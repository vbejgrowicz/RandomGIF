(ns randomgif.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defn handler [request]
  "Function to handle basic HTTP request"
  {:status 200
   :body "Welcome to the RandomGIF App!"
   :headers {}})

(defroutes app
  (GET "/" [] handler)
  (route/not-found "<h1>Page Not Found</h1>"))

(defn -main
  "The entry point for the RandomGIF App"
  []
  (jetty/run-jetty app
    {:port 8000}))

(defn -dev-main
  "The dev entry point for the RandomGIF App with app reload functionality"
  []
  (jetty/run-jetty (wrap-reload #'app)
    {:port 8000}))
