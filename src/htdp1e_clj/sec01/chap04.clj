(ns htdp1e-clj.sec01.chap04
  (:require [clojure.test :refer :all]
            [same :refer [ish? zeroish?]]))

;;;; I - Processing Simple Forms of Data
;;;; 4 - Conditional Expressions and Functions
;; pg. 60


;;; 4.1 Booleans and Relations

(< 4 5) ;; => true
(= 4 5) ;; => false
;; `and` `or` `not`

(and (= 5 5) (< 5 5)) ;; => false

(and true true)
(or true true)
(not true)


;;; Exercises 4.1

;; Exercise 4.1.1 What are the results of the following Clojure conditions?
;;   1. (and (> 4 3) <= 10 100))
;;   2. (or ( > 43) (= 10 100))
;;   3. (not (= 2 3))

(and (> 4 3) (<= 10 100)) ;; => true
(or (> 4 3) (= 10 100)) ;; => true
(not (= 2 3)) ;; => true

;; Exercise 4.1.2 What are the results of
;;   1. (> x 3)
;;   2. (and (> 4 x) (> x 3))
;;   3. (= (* x x) x)
;; for (a) x = 4, (b) x = 2, and (c) x = 7/2 ?

(def x1 4)
(> x1 3) ;; => true
(and (> 4 x1) (> x1 3)) ;; => false
(= (* x1 x1) x1) ;; => false

(def x2 2)
(> x2 3) ;; => false
(and (> 4 x2) (> x2 3));; => false
(= (* x2 x2) x2) ;; => false

(def x3 7/2)
(> x3 3) ;; => true
(and (> 4 x3) (> x3 3))  ;; => true
(= (* x3 x3) x3) ;; => false


;; Example

;; Company XYZ & Co. pays all its employees $12 per hour. A
;; typical employee works between 20 and 65 hours per week. De-
;; velop a program that determines the wage of an employee from
;; the number of hours of work, `if the number is within the proper
;; range`.


(defn wage [hours]
  (* hours 12.0))

(wage 0)  ;; => 0.0
(wage 10) ;; => 120.0
(wage 40) ;; => 480.0



;;; 4.2 Functions that Test Conditions

;; is-5? : number -> boolean
;; Determine whether `n` is equal to 5
(defn is-5? [n]
  (= n 5))
