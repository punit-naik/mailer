(ns mailer.service.getall
	(:require [mailer.service.db :as db])
	(:use korma.db korma.core)
	)

(defn get-mail [request]
	(select db/maildata))

(defn filter-mail [request]
	(select db/maildata
		(where (or {:name [like (:query request)]} {:org [like (:query request)]}))))
