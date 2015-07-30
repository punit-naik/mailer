(ns mailer.service.auth
	(:require [mailer.service.db :as db]
	    	  [noir.session :as session]
		  [cheshire.core :refer :all])
	(:use korma.db korma.core))

	

(defn authenticate [request]
	(if (not (empty? (select db/userdata (where {:username (get-in request ["username"]) :pass (get-in request ["pass"])}))))
		(do 
			(session/put! :username (get-in request ["username"]))
			;;render the upload-csv html page
			(generate-string {:status "You are authenticated"}))
		(generate-string {:status "Username or Password not correct"})
		)
	)
