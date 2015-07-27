(ns mailer.web.resource
  (:require [liberator.core :refer [defresource]]
            [taoensso.timbre :as timbre]
            [cheshire.core :refer :all]
	    [mailer.service.sendmail :as mail]
	    [mailer.service.get-all :as get-all]))

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
		(get-all/get-mail (get-in  ctx [:request :body])))
)

(defresource filter-mail
:available-media-types ["application/json"]
:allowed-methods [:get]
:handle-ok (fn [ctx]
		(get-all/filter-mail (get-in ctx [:request :body])))
)
