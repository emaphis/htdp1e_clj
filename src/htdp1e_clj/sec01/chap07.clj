(ns htdp1e-clj.sec01.chap07
  (:require [clojure.test :refer :all]
            [same :refer [ish? zeroish?]]))

;;;; I - Processing Simple Forms of Data
;;;; 7 - The Varieties of Data - pg 112.


;;; 7.1 Mixing and Distinguishing Data.

;; predicate - recognize a particular class of data

;; numbers: representations of numeric information
;; `number?`, which consumes an arbitrary value and produces true if the
;; value is a number and false otherwise;

(number? 3.0) ;; => true
(number? 'bool) ;; => false

;; booleans: truth and falsity.
;; `boolean?`, which consumes an arbitrary value and produces true if the
;; value is a boolean value and false otherwise;

(boolean? false) ;; => true
(boolean? 100) ;; => false

;; symbols:  representations of symbolic information 
;; `symbol?`, which consumes an arbitrary value and produces true if the
;; value is a symbol and false otherwise;

(symbol? 'symbol) ;; => true
(symbol? "symbol") ;; => false

;; strings:  lists of characters representing information

(string? "string") ;; => true
(string? 'string) ;; => false

;; maps:  representations of compounds of information

(map? {:a 'map}) ;; => true
(map? 'map) ;; => false


;; custom map predicates:
;; (defn make-map [a b] {:a a :b b})
;; (defn map? [mp] (and (number? (:a mp)) (string? (:b mp))))
;; (make-map 3 "str")
;; => (and (number? 3) (string? "str")

;; examples:

(defn make-posn [x y]
  {:x x :y y})

;; posn? : any -> boolean
;; check if obj contains an :x number and a :y number
(defn posn? [obj]
  (and (number? (:x obj))
       (number? (:y obj))))

(deftest test-posn?
  (is (= true (posn? (make-posn 1 2))))
  (is (= true (posn? {:x 1 :y 2})))
  (is (= false (posn? {:x 1 :z 2})))
  (is (= false (posn? (make-posn 1 "two"))))
  (is (= false (posn? "posn")))
  (is (= false (posn? 100))))


;; star is a map
(defn make-star [last first dob ssn]
  {:last last :first first :dob dob :ssn ssn})

(defn star? [obj]
  (and (symbol? (:last obj))
       (symbol? (:first obj))
       (string? (:dob obj))
       (string? (:ssn obj))))


;; airplane is a map
(defn make-airplane [kind max-speed max-load price]
  {:kind kind :max-speed max-speed :max-load max-load :price price})

(defn airplane? [obj]
  (and (symbol (:kind obj))
       (number? (:max-speed obj))
       (number? (:max-load obj))
       (number? (:price obj))))

(= true (airplane? (make-airplane 'jet 600 4000 100000)))


;;; Exercise 7.1

;; Exercise 7.1.1 Evaluate the following expressions by hand:

;; 1. (number? (make-posn 2 3))
(number? (make-posn 2 3)) ;; => false

(number? {:x 2 :y 3}) ;; => false

;; 2. (number? (+ 12 10))
(number? (+ 12 10)) ;; => true

(number? 22) ;; => true


;; 3. (posn? 23)
(posn? 23) ;; => false

(and (number? (:x 23))
     (number? (:y 23)));; => false

(and (number? nil)
     (number? (:y 23))) ;; => false

(and false
     (number? (:y 23))) ;; => false


;; 4. (posn? (make-posn 23 3))
(posn? (make-posn 23 3)) ;; => true

(posn? {:x 23 :y 3}) ;; => true

(and (number? (:x {:x 23 :y 3}))
     (number? (:y {:x 23 :y 3}))) ;; => true

(and (number? 23)
     (number? (:y {:x 23 :y 3}))) ;; => true

(and true
     (number? (:y {:x 23 :y 3}))) ;; => true

(and true (number? 3)) ;; => true

(and true true) ;; => true


;; 5. (star? (make-posn 23 3))
(star? (make-posn 23 3)) ;; => false

(star? {:x 23 :y 3}) ;; => false

(and (symbol? (:last {:x 23 :y 3}))
     (symbol? (:first {:x 23 :y 3}))
     (string? (:dob {:x 23 :y 3}))
     (string? (:ssn {:x 23 :y 3})));; => false

(and (symbol? nil)
     (symbol? (:first {:x 23 :y 3}))
     (string? (:dob {:x 23 :y 3}))
     (string? (:ssn {:x 23 :y 3}))) ;; => false

(and false
     (symbol? (:first {:x 23 :y 3}))
     (string? (:dob {:x 23 :y 3}))
     (string? (:ssn {:x 23 :y 3}))) ;; => false



;; Example:

;; distance-to-0 : pixel-2 -> number
;; compute the distance of a-pixel to the origin.

(defn distance-to-0 [a-pixel]
  (cond
    (number? a-pixel) a-pixel
    (posn? a-pixel) (Math/sqrt
                     (+ (* (:x a-pixel) (:x a-pixel))
                        (* (:y a-pixel) (:y a-pixel))))))


;; Shapes examples:

;; a circle is a map
;; make-circle : number number -> circle.
;; make a circle given a center position and a radius
(defn make-circle [center radius]
  {:center center :radius radius})

(defn circle? [obj]
  (and (posn? (:center obj))
       (number? (:radius obj))))

;; a square is a msp
;; make-square : posn number -> square\
;; makes a square map given a posn and a number
(defn make-square [nw length]
  {:nw nw :length length})

(defn square? [obj]
  (and (posn? (:nw obj))
       (number? (:length obj))))

;; Examples:
(make-square (make-posn 20 20) 3)
;; => {:nw {:x 20, :y 20}, :length 3}
(square? (make-square (make-posn 20 20) 3)) ;; => true

(make-square (make-posn 2 20) 3)
;; => {:nw {:x 2, :y 20}, :length 3}
(square? (make-square (make-posn 2 20) 3)) ;; => true
(circle? (make-square (make-posn 2 20) 3)) ;; => false

(make-circle (make-posn 10 99) 1)
;; => {:center {:x 10, :y 99}, :radius 1}
(circle? (make-circle (make-posn 10 99) 1)) ;; => true

;; perimeter : shape -> number
;; compute the perimeter of a-shape
(defn perimeter [a-shape]
  (cond
    (square? a-shape)
    (* (:length a-shape) 4)
    (circle? a-shape)
    (* (* 2 (:radius a-shape)) Math/PI)))


;; Exercises 7.1

;; Exercise 7.1.2 Test perimeter with the examples.

(deftest test-perimeter
  (is (= 4 (perimeter (make-square (make-posn 2 3) 1))))
  (is (= 12 (perimeter (make-square (make-posn 20 20) 3))))
  (is (= 12 (perimeter (make-square (make-posn 2 20) 3))))
  (is (= (* 2 Math/PI) (perimeter (make-circle (make-posn 1 1) 1)))) 
  (is (= (* 6 Math/PI) (perimeter (make-circle (make-posn 10 99) 3)))))


;; Exercise 7.1.3 Develop the function area, which consumes either a
;; circle or a square and computes the area. Is it possible to reuse
;; the template for perimeter by changing the name to area?

;; area : shape -> number
;; compute the ara of a-shape
(defn area [a-shape]
  (cond
    (square? a-shape)
    (* (:length a-shape) (:length a-shape))
    (circle? a-shape)
    (* (:radius a-shape) (:radius a-shape) Math/PI)))

(deftest test-area
  (is (= 1 (area (make-square (make-posn 2 3) 1))))
  (is (= 9 (area (make-square (make-posn 20 20) 3))))
  (is (= 9 (area (make-square (make-posn 2 20) 3))))
  (is (= (* 1 1 Math/PI) (area (make-circle (make-posn 1 1) 1)))) 
  (is (= (* 3 3 Math/PI) (area (make-circle (make-posn 10 99) 3)))))



;;; 7.2 Designing Functions for Mixed Data. pg. 120

;; Data Definition:
;; A shape is either
;; a circle map
(defn make-circle [center radius]
  {:center center :radius radius})
;; where is a posn and s is a number:
(defn circle? [shape]
  (and (posn? (:center shape)) (number? (:radius shape))))
;; Or a square map
(defn make-square [nw-corner length]
  {:nw-corner nw-corner :length length})
;; where nw-corner is a posn and is length is a number
(defn square? [shape]
  (and (posn? (:nw-corner shape)) (number? (:length shape))))


;; Examples:

(make-square (make-posn 20 20) 3)
;; => {:nw {:x 20, :y 20}, :length 3}
(square? (make-square (make-posn 20 20) 3)) ;; => true
(make-square (make-posn 2 20) 3)
;; => {:nw {:x 2, :y 20}, :length 3}
(square? (make-square (make-posn 2 20) 3)) ;; => true
(circle? (make-square (make-posn 2 20) 3)) ;; => false
(make-circle (make-posn 10 99) 1)
;; => {:center {:x 10, :y 99}, :radius 1}
(circle? (make-circle (make-posn 10 99) 1)) ;; => true

;; Template:

;; shape-template : shape -> ???
(defn shape-template [shape]
  (cond
    (square? shape)
    (,,, (:nw-corner shape) ,,, (:length shape))
    (circle? shape)
    (,,, (:center shape) ,,, (:radius shape) ,,, )))

;; Body:

;; perimeter : shape -> number
;; compute the perimeter of a-shape
(defn perimeter [a-shape]
  (cond
    (square? a-shape) (* (:length a-shape) 4)
    (circle? a-shape) (* (* 2 (:radius a-shape)) Math/PI)))

;; Tests

(deftest test-perimeter
  (is (= 4 (perimeter (make-square (make-posn 2 3) 1))))
  (is (= 12 (perimeter (make-square (make-posn 20 20) 3))))
  (is (= 12 (perimeter (make-square (make-posn 2 20) 3))))
  (is (= (* 2 Math/PI) (perimeter (make-circle (make-posn 1 1) 1)))) 
  (is (= (* 6 Math/PI) (perimeter (make-circle (make-posn 10 99) 3)))))


;;; Exercises 7.2.

;; Exercise 7.2.1 Develop structure and data definitions for a collection of zoo
;; animals. The collection includes

;; `spiders`, whose relevant attributes are the number of remaining legs
;;  (we assume that spiders can lose legs in accidents) and the space they
;;   need in case of transport;

;; `elephants`, whose only attributes are the space they need in case of
;;    transport;

;; monkeys, whose attributes are intelligence and space needed for
;;     transportation.

;; Then develop a template for functions that consume zoo animals.
;; Develop the function fits? The function consumes a zoo animal and the
;; volume of a cage. It determines whether the cage is large enough for the
;; animal.

;; zoo-animal is one of three classes: spiders, elephants, monkeys.

;; spider is a map:
;; make-spider : number number -> spider
(defn make-spider [legs space]
  {:legs legs :space space})
;; where legs and space are numbers
(defn spider? [any]
  (and (number? (:legs any)) (number? (:space any))))

;; elephant is a map
;; make-elephant : number -> elephant
(defn make-elephant [space]
  {:space space})
;; where space is a number
(defn elephant? [any]
  (number? (:space any)))

;; monkey is a map:
;; make-monkey : number number -> monkey
(defn make-monkey [intelligence space]
  {:intelligence intelligence :space space})
;; where intelligence and space a numbers.
(defn monkey? [any]
  (and (number? (:intelligence any)) (number? (:space any))))

;; zoo-animal-template : zoo-animal -> ???
;; consume a zoo-animal
(defn zoo-animal-template [an-animal]
  (cond
    (spider? an-animal)
    (,,, (:legs an-animal) ,,, (:space an-animal) ,,,)
    (elephant? an-animal)
    (,,, (:space an-animal) ,,,)
    (monkey? an-animal)
    (,,, (:intelligence an-animal) ,,, (:space an-animal) ,,,)))


