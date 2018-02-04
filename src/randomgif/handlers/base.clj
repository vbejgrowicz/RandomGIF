(ns randomgif.handlers.base
  (:require [hiccup.page :refer [html5]]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn header
  "Function to display HTML Header"
  []
  [:head (hiccup.page/include-css "styles.css")
   [:meta {:charset "UTF-8"}]
   [:title "Find GIFs"]])

(defn inputForm
  "Function to display HTML Text Input Form"
  []
  [:div {:class "search-form"}
   [:div {:class "form-header"} "Find a GIF!"]
   [:form {:action "/search" :method "GET"}
    [:input {:id "search"
             :name "input"
             :type "search"
             :placeholder "Enter Search Term"}]]])

(defn searchURL
  ([search limit]
   (str "http://api.giphy.com/v1/gifs/search?q=" search "&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&limit=" limit))
  ([search]
   (str "http://api.giphy.com/v1/gifs/search?q=" search "&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&limit=" "1")))

(defn parseResponse
  [response]
  (get (json/read-str (:body response)) "data"))

(defn handleSearch
  "Function to handle search request"
  [{input "input"}]
  (let [response (client/get (searchURL input 10))]
    (parseResponse response)))
(defn home [request]
  (html5 {:lang "en"}
   (header)
   (inputForm)))

(defn search [request]
  (html5 {:lang "en"}
   (header)
   (searchResults (:params request))))
