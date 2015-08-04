(ns mailer.web.resource
  (:require [liberator.core :refer [defresource]]
            [taoensso.timbre :as timbre]
            [cheshire.core :refer :all]
            [mailer.service.sendmail :as mail]
            [mailer.service.getall :as getall]
	    [selmer.parser :refer [render-file]]
            [mailer.service.auth :as auth]))

(timbre/refer-timbre)
(timbre/merge-config! {:level :debug})

(defresource send-mail
:available-media-types ["application/json"]
:allowed-methods [:post]
:handle-created (fn [ctx]
			(mail/send-mail (get-in ctx [:request :body])))
)

(defresource get-all
:available-media-types ["application/json"]
:allowed-methods [:get]
:handle-ok (fn [ctx]
		(getall/get-mail (get-in  ctx [:request :body])))
)

(defresource filter-mail
:available-media-types ["application/json"]
:allowed-methods [:get]
;;curl -v -X GET http://localhost:3000/filter-mail -d '{"query":"Fomento Travel"}' -H "Content-Type: application/json"
;;curl -v -X GET http://localhost:3000/filter-mail -d '{"query":"Punit Naik"}' -H "Content-Type: application/json"
:handle-ok (fn [ctx]
		(getall/filter-mail (get-in ctx [:request :body])))
)

(defresource unsub
:available-media-types ["application/json"]
:allowed-methods [:put]
;;curl -v -X PUT http://localhost:3000/unsubscribe -d '{"email":"naik.punit44@gmail.com"}' -H "Content-Type: application/json"
:handle-created (fn [ctx]
	(getall/unsub-mail (get-in ctx [:request :body])))
)

(defresource upload-file
:available-media-types ["application/json" "multipart/form-data"]
:allowed-methods [:post]
;;curl -v -X POST http://localhost:3000/upload-csv --upload-file Desktop/data.csv
:handle-created (fn [ctx]
			(if (:username (:noir (:session request)))
				(getall/upload-csv (get-in ctx [:request :body]))
				(generate-string {:status "You are not logged in"})
				)
			)
)

(defresource login
	:available-media-types ["application/json" "text/html"]
	:allowed-methods [:post :get]
	:handle-created (fn [ctx]
		(info (get-in ctx [:request]))
		(auth/authenticate (get-in ctx [:request :params])))
	:handle-ok (fn [ctx]
			(if (get-in ctx [:request :session :noir :username])
				;;render the upload-csv html page
				(generate-string {:status "You are authenticated"})
				(render-file "public/login.html" {})))

)
