(ns mailer.service.sendmail
(:require [postal.core :as p]
	  [cheshire.core :refer :all]))


(defn send-mail [request]
	(dorun
		(for [mail (:mail request)]
			(p/send-message {:host "smtp.gmail.com"
         		:user "abc@gmail.com";; Sender's gmail email address.
         		:pass "password";; Sender's password
         		:ssl :yes!!!11}
         		{:from "abc@gmail.com" ;; Sender's gmail email address.
         		:to mail ;; receipients email address
         		:subject (:sub request)
         		:body (:body request)})))
	(generate-string {:status "All mails have been sent"})
)
