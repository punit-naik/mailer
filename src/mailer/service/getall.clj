(ns mailer.service.getall
	(:require [mailer.service.db :as db])
	(:use korma.db korma.core)
	)

(defn get-mail [request]
	(select db/maildata
		(fields :id :name :org :email)))

(defn filter-mail [request]
	(select db/maildata
		(fields :id :name :org :email)
		(where (and {:active 1} (or {:name [like (str "%" (:query request) "%")]} {:org [like (str "%" (:query request) "%")]})))))
