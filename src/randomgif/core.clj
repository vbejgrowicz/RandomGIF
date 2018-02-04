(ns randomgif.core
  (:require [ring.adapter.jetty :as jetty]))

(defn handler [request]
  "Function to handle basic HTTP request"
  {:status 200
   :body "Welcome to the RandomGIF App!"
   :headers {}})

(defn -main
  "The entry point for the RandomGIF App"
  []
  (jetty/run-jetty handler
    {:port 8000}))
