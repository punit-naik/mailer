(ns mailer.web.route
  (:use [ring.util.response])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [taoensso.timbre :as timbre]
            [cheshire.core :refer :all]
            [mailer.web.resource :as res]))

(timbre/refer-timbre)

(defroutes app-routes
  (ANY "/" [_] "Welcome")
  (ANY "/send" request (res/send-mail request))
  (ANY "/get-all-records" request (res/get-all request))
  (ANY "/filter-mail" request (res/filter-mail request))
  (ANY "/unsubscribe" request (res/unsub request))
  (ANY "/upload-csv" request (res/upload-file request))
  (ANY "/upload" request (res/login request))
  )

