(ns randomgif.handlers.base
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]))

(defn handler [request]
  "Function to handle basic HTTP request"
  {:status 200
   :body (html [:h1#header "Welcome to RandomGIF App!"])
   :headers {}})
