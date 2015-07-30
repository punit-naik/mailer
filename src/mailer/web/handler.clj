(ns mailer.web.handler
  (:require [ring.middleware.defaults :refer :all]
            [mailer.web.route :as r]
            [taoensso.carmine.ring :refer [carmine-store]]
            [ring.middleware.json :refer 
             [wrap-json-response wrap-json-body]]
            [taoensso.timbre :as timbre]
	    [noir.session :as session]
            [taoensso.carmine.ring :refer [carmine-store]]
	    [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.params :refer [wrap-params]]))

(timbre/refer-timbre)

(def server-conn {:pool {} :spec {:host "127.0.0.1" :port 6379}})

(def app
  (wrap-json-body (session/wrap-noir-session
   (wrap-json-response (wrap-multipart-params (wrap-params r/app-routes)))) {:keywords? true}))
