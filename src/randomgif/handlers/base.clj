(ns randomgif.handlers.base
  (:require [hiccup.page :refer [html5]]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(defn header
  "Function to display HTML Header"
  []
  [:head (hiccup.page/include-css "styles.css") (hiccup.page/include-js "app.js")
   [:meta {:charset "UTF-8"}]
   [:title "Find GIFs"]])

(defn inputForm
  "Function to display HTML Text Input Form"
  []
  [:div {:class "search-form"}
   [:h1 "Find GIFs!"]
   [:form {:id "myform" :action "/search" :method "POST"}
      [:input {:id "search"
               :name "input"
               :type "search"
               :placeholder "Enter Search Term"}]]])

(defn get-GIF-src
  [gif]
  (-> gif
    (get "images")
    (get "fixed_height_downsampled")
    (get "url")))

(defn displayGIF
  [gif]
  [:img {:src (get-GIF-src gif)}])

(defn gif-api
  "Function to fetch gifs based on input"
  ([search limit]
   (str "http://api.giphy.com/v1/gifs/search?q=" search "&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&rating=g&lang=en&limit=" limit))
  ([search]
   (str "http://api.giphy.com/v1/gifs/search?q=" search "&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&rating=g&lang=en"))
  ([]
   (str "http://api.giphy.com/v1/gifs/random?&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&rating=g&lang=en")))

(defn parseResponse
  "Function to parse JSON response"
  [response]
  (-> response
      :body
      json/read-str
      (get "data")))

(defn fetch-gifs
  "Function to fetch GIFs and return parsed response"
  ([input]
   (-> input
       gif-api
       client/get
       parseResponse))
  ([]
   (->
       (gif-api)
       client/get
       parseResponse)))

(defn capitalize-words
  "Function to capitilize first letter of each word"
  [string]
  (-> string
      (string/split #" ")
      (->> (map string/capitalize)
           (string/join " "))))

(defn searchResults
  "Function to display Search Results"
  [input]
  (let [results (fetch-gifs input)]
    [:div
      [:h1 (capitalize-words input)]
      (if (empty? results)
        [:div "No matches found"]
        [:ul
          (map (fn [each] [:li (displayGIF each)]) results)])]))

(defn home [request]
  (html5 {:lang "en"}
   (header)
   (inputForm)
   (displayGIF (fetch-gifs))))

(defn search [request]
  (html5 {:lang "en"}
   (header)
   (searchResults (get (:params request) "input"))))
