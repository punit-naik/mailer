(ns mailer.service.auth
	(:require [mailer.service.db :as db]
	    	  [noir.session :as session]
		  [crypto.password.scrypt :as password]
		  [cheshire.core :refer :all])
	(:use korma.db korma.core))

	

(defn authenticate [request]
	(do
		(def pass ((vec (select db/userdata (where {:username (get-in request ["username"])}) (fields :pass))) 0))
		(if (not (empty? pass))
			(if (password/check (get-in request ["pass"]) (:pass pass))
				(do 
					(session/put! :username (get-in request ["username"]))
					;;render the upload-csv html page
					(generate-string {:status "You are authenticated"})
					)
				(generate-string {:status "Username or Password not correct"})
				)
			)
		)
	)
