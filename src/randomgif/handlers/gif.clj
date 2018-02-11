(ns randomgif.handlers.gif
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]))

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

(defn get-GIF-src
  [gif]
  (-> gif
    (get "images")
    (get "fixed_height_downsampled")
    (get "url")))

(defn displayGIF
  [gif]
  [:img {:src (get-GIF-src gif)}])
