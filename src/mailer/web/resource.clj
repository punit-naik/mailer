(ns mailer.web.resource
  (:require [liberator.core :refer [defresource]]
            [taoensso.timbre :as timbre]
            [cheshire.core :refer :all]
	    [mailer.service.sendmail :as mail]
	    [mailer.service.getall :as getall]))

(timbre/refer-timbre)
(timbre/merge-config! {:level :debug})

(defresource send-mail
:available-media-types ["application/json"]
:allowed-methods [:post]
:handle-created! (fn [ctx]
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
