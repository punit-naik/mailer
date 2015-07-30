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

(defn upload-csv [request]
	(let [lines (clojure.string/split (slurp request) #"\n")]
		(dorun
			(for [l lines]
				(do
					(def parts (clojure.string/split l #","))
					(insert db/maildata (values {:name (parts 0) :email (parts 1) :org (parts 2)}))
					)
				)
			)
		)
	(generate-string {:status "New Entries have been added"})
	)
