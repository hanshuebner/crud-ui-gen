(ns crud-ui-gen.core
  (:require [clojure.data.xml :as xml]
            [clojure.walk :as walk]
            [clojure.zip :as zip]))

(defn transform [e tree acc]
  (cons
    (cond
      (= (:tag e) :html) (dissoc e :content)
      (:tag e) (dissoc e :content)
      :else e)
    acc))

(defn walk [tree]
  (loop [tree tree
         acc nil]
    (if (zip/end? tree)
      acc
      (recur (zip/next tree)
        (transform (zip/node tree) tree acc)))))

(let [html (xml/parse (java.io.FileReader. "a.html"))]
  (walk (zip/xml-zip html)))

