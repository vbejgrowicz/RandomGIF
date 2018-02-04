(ns randomgif.handlers.base
  (:require [hiccup.page :refer [html5]]))

(defn header
  "Function to display HTML Header"
  []
  [:head
   [:meta {:charset "UTF-8"}]
   [:title "Find GIFs"]])

(defn inputForm
  "Function to display HTML Text Input Form"
  []
  [:div {:class "form"}
   [:div "Find a GIF!"]
   [:form {:action "/search" :method "GET"}
    [:input {:id "query"
             :type "text"
             :placeholder "Enter Search Term"}]]])

(defn home [request]
  (html5 {:lang "en"}
   (header)
   (inputForm)))
