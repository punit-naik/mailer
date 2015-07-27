(ns mailer.service.getall
	(:require [mailer.service.db :as db]
		  [cheshire.core :refer :all])
	(:use korma.db korma.core)
	)

(defn get-mail [request]
	(select db/maildata
		(fields :id :name :org :email)))

(defn filter-mail [request]
	(select db/maildata
		(fields :id :name :org :email)
		(where (and {:active 1} (or {:name [like (str "%" (:query request) "%")]} {:org [like (str "%" (:query request) "%")]})))))

(defn unsub-mail [request]
	(update db/maildata
		(set-fields {:active 0})
		(where {:email (:email request)}))
	(generate-string {:status "You have been unsubscribed"})
)
