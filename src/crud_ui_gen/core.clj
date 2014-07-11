(ns crud-ui-gen.core
  (:require [clojure.data.xml :as xml]
            [clojure.walk :as walk]
            [clojure.zip :as zip]))

(defn transform [e tree]
  (println
    (cond
      (= (:tag e) :html) (dissoc e :content)
      (:tag e) (dissoc e :content)
      :else e)))

(defn walk [tree]
  (loop [tree tree]
    (transform (zip/node tree) tree)
    (if (zip/end? tree)
      (zip/root tree)
      (recur (zip/next tree)))))

(defn print-content [tree]
  (loop [tree tree]
    (println (:content (first (zip/next tree))))
    (if (zip/end? tree)
      'end
      (recur (zip/next tree)))))

(let [html (xml/parse (java.io.FileReader. "a.html"))]
  (walk (zip/xml-zip html)))


