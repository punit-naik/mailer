(ns mailer.service.get-all
	(:require [mailer.service.db :as db]
	(:use korma.db korma.core))
	)

(defn get-mail [request]
	(select db/mail-data))

(defn filter-mail [request]
	(select db/mail-data
		(where {:name (:name request)})))
