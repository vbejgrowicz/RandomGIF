(ns randomgif.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]))

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

(defn -dev-main
  "The dev entry point for the RandomGIF App with app reload functionality"
  []
  (jetty/run-jetty (wrap-reload #'handler)
    {:port 8000}))
