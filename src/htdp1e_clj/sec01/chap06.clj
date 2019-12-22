(ns htdp1e-clj.sec01.chap06
  (:require [clojure.test :refer :all]
            [same :refer [ish? zeroish?]]))

;;;; I - Processing Simple Forms of Data
;;;; 6 - Compound Data, Part 1: Maps as Structures. pg. 83


;;; 6.1 Maps as Structures

;; make-posn : number number -> Posn
;; make a `posn` map given `x` and `y` values.
(defn make-posn [x y]
  {:x x :y y})

(def posn-1 (make-posn 2 3))

(:x posn-1) ;; => 2
(:y posn-1) ;; => 3


;; distance-to-0 : pasn -> number
;; compute the distance to a-posn to the origin
(defn distance-to-0 [a-posn]
  (Math/sqrt (+ (* (:x a-posn) (:x a-posn))
                (* (:y a-posn) (:y a-posn)))))

(distance-to-0 (make-posn 0 5)) ;; => 5.0

(distance-to-0 (make-posn 7 0)) ;; => 7.0

(distance-to-0 (make-posn 8 6)) ;; => 10.0

(distance-to-0 (make-posn 5 12)) ;; => 13.0

;; Selection of components

(:x (make-posn 7 0)) ;; => 7
(:y (make-posn 7 0)) ;; => 0

(def a-posn (make-posn 7 0))

(:x a-posn) ;; => 7
(:y a-posn) ;; => 0

(* (:x a-posn) 7) ;; => 49

(defn posn-template [a-posn]
  ,,, (:x a-posn) ,,,
  ,,, (:y a-posn) ,,,)

(defn distance-to-0 [a-posn]
  (Math/sqrt
   (+ (* (:x a-posn) (:x a-posn))
      (* (:y a-posn) (:y a-posn)))))


;;; Exercises 6.1

;; Exercise 6.1.1 Evaluate the following expressions:
;;  1. (distance-to-O (make-posn 3 4))
;;  2. (distance-to-O (make-posn (* 2 3) (* 2 4)))
;;  3. (distance-to-O (make-posn 12 (- 6 1)))
;; by hand. Show all steps. Assume that sqr performs its computation in a
;; single step . Check the results with DrScheme's stepper

(distance-to-0 (make-posn 3 4)) ;; => 5.0
(distance-to-0 (make-posn (* 2 3) (* 2 4))) ;; => 10.0
(distance-to-0 (make-posn 12 (- 6 1))) ;; => 13.0



;;; Extended Exercise: Drawing Simple Picture. pg 55

;; FIXME: Skip!



;;; 6.3 Structure Definitions - Using Clojure maps

;; a map to represent positions on a plane
;; (make-posn number number)
(defn make-posn [x y]
  {:x x :y y})

;; create a posn
;; (make-posn 1 2)

;; selectors - get values
;; (:x a-posn)  ;=> 1
;; (:y a-posn)  ;=> 2

;; And example

;; make-entry : symbol sybol string -> entry
;; A map to represent entries in a phone-book.
(defn make-entry [name zip phone]
  {:name name :zip zip :phone phone})

;;(def an-entry (make-entry symbol number string))

;; Selectors for entry map.
;; (:name an-entry)   ; symbol - name of phone-book entry
;; (:zip an-entry)    ; number - zip code of phone-book entry
;; (:phone an-entry)  ; string - phone number of phone-book entry.

;; Example
(make-entry 'PeterLee 15270 "606-7771")

(:name (make-entry 'PeterLee 15270 "606-7771"))
;; => PeterLee

(def phonebook (make-entry 'PeterLee 15270 "606-7771"))

(:name phonebook) ;; => PeterLee
(:zip phonebook) ;; => 15270
(:phone phonebook) ;; => "606-7771"

;; make-star : symbol symbol number -> star
;; make a map representing rock stars
(defn make-star [last first intrument sales]
  {:last last :first first :intrument intrument  :sales sales})

;; (def a-star (make-star symbol symbol number))

;; selectors for star mao
;; (:last a-star)        ; symbol - last name
;; (:first a-star)       ; symbol - first name
;; (:instrument a-star)  ; symbol - instrument star plays
;; (:sales a-star)       ; number - sales of star


(make-star 'Fredman 'Dan 'ukelele 190004)

(make-star 'Talcott 'Caroly 'banjo 80000)

(make-star 'Harper 'Robert 'bagpipe 27860)


;;; Exercises 6.3  pg 93

;; Exercise 6.3.1 Consider the following structure definitions:
;; 1. (define-struct movie (title producer))
;; 2. (define-struct boyfriend (name hair eyes phone))
;; 3. (define-struct cheerleader (name number))
;; 4. (define-struct CD (artist title price))
;; 5. (define-struct sweater (material size producer))

;; What are the names of the constructors and the selectors that each of them
;; adds to Scheme? Draw box representations for each of these structures.


;; Exercise 6.3.2 Consider the following structure definition

;;   (define-struct movie (title producer))

;; and evaluate the following expressions:
;;   1. (movie-title (make-movie 'ThePhantomMenace 'Lucas))
;;   2. (movie-producer (make-movie 'TheEmpireStrikesBack 'Lucas))
;; Now evaluate the following expressions, assuming x and y stand for
;; arbitrary symbols:

;;  1. (movie-title (make-movie x y))
;;  2. (movie-producer (make-movie x y))

;; Formulate equations that state general laws concerning the relationships
;; of movie-title and movie-producer and make-movie

;; map movie xdefined above

(:title (make-movie 'ThePantomMenace 'Lucas)) ;; => ThePantomMenace

(:producer (make-movie 'ThePantomMenace 'Lucas)) ;; => Lucas

;; equations:

(deftest test-movie-map-rules
  (is (= 'x (:title (make-movie 'x 'y))) "for all values x and y")
  (is (= 'y (:producer (make-movie 'x 'y))) "for all values x and y"))


;; function definitions

;; increment-sales : star -> star
;; produce an new star with sales increased by 20000.
(defn increment-sales [a-star]
  (make-star (:first a-star)
             (:last a-star)
             (:instrument a-star)
             (+ (:sales a-star) 20000)))

(increment-sales (make-star 'Abba 'John 'vocals 12200))
;; => {:last John, :first Abba, :intrument nil, :sales 32200}

;; Template for a function that consumes a star
(defn start-template [a-star]
  ,,, (:last a-star) ,,,
  ,,, (:first a-star) ,,,
  ,,, (:instrument a-star) ,,,
  ,,, (:sales a-star) ,,,)


;;; Exercises  6.3

;; Exercise 6.3.3 Provide a structure definition that represents an airforce's
;; jet fighters. Assume that a fighter has four essential properties: designation
;; ('f22, 'tornado, or 'mig22), acceleration, top-speed, and range. Then
;; develop the function within-range. The function consumes a fighter record
;; and the distance of a target from the (fighter 's) base. It determines whether
;; the fighter can reach the intended target.

;; Also develop the function reducerange.
;; The function consumes a fighter record and produces one in which
;; the range field is reduced to 80% of the original value.

;; make-fighter : symbol number number number -> fighter
;; Produces an fighter map.
(defn make-fighter [designation acceleration top-speed range]
  {:designation designation
   :acceleration acceleration
   :top-speed top-speed
   :range range})

;; (def a-figter (make-fighter symbol number number number))

;; selectors
;; (:designation a-fighter)     ; symbol
;; (:acceleration a-fighter)    ; number
;; (:top-speed a-fighter)       ; number
;; (:range a-fighter)           ; number

(defn fighter-temp [a-figter]
  ,,, (:designation a-figter) ,,,
  ,,, (:acceleration a-figter) ,,,
  ,,, (:top-speed a-figter) ,,,
  ,,, (:range a-figter) ,,,)


;; in-range : fighter number -> boolean
;; determines if fighter is in range of a targ .80et.
(defn in-range [a-fighter targ-distance]
  (>= (:range a-fighter) targ-distance))

(deftest test-in-range
  (is (= true (in-range (make-fighter 'F15 30 1500 0) 0)))
  (is (= true (in-range (make-fighter 'F15 30 1500 1200) 0)))
  (is (= true (in-range (make-fighter 'F15 30 1500 1200) 600)))
  (is (= false (in-range (make-fighter 'F15 30 1500 1200) 1500))))


;; reduce-range : fighter -> fighter
;; Produce a new fighter from a given fighter with range reduced to 80%
(defn reduce-range [a-figter]
  (make-fighter (:designation a-figter)
                (:acceleration a-figter)
                (:top-speed a-figter)
                (* 0.80 (:range a-figter))))


(deftest test-reduce-range
  (is (= (make-fighter 'F15 30 1500 0.0)
         (reduce-range (make-fighter 'F15 30 1500 0.0))))
  (is (= (make-fighter 'F15 30 1500 960.0)
         (reduce-range (make-fighter 'F15 30 1500 1200.0)))))



;;; 6.4 Data Definitions

(make-posn 'Albert 'Meyer) ;; => {:x Albert, :y Meyer}

;; (distance-to-0 (make-posn 'Albert 'Meyer)) ;=> Error! - type mismatch.


;; A posn is a structure:
;; (make-posn x y)
;; where x and y are numbers

;; A star is a structure:
;;  (make-star last first instrument sales)
;; Where last, first, and instrument are symbols and sales is a number.


;;; Exercises 6.4

;; Exercise 6.4.1 Provide data definitions for the following structure definitions:
;; 1. (define-struct movie (title producer))
;; 2. (define-struet boyfriend (name hair eyes phone))
;; 3. (define-struct cheerleader (name number))
;; 4. (define-struct CD (artist title price))
;; 5. (define-struct sweater (material size producer))
;; Make appropriate assumptions about what data goes with which field.

;; Revamped from ex. 6.3.1

;; 1. (define-struct movie (title producer))

;; A movie is a map
;;  (make-movie title producer)
;; where title and producer are symbols

;; make-movie : symbol symbol -> movie
;; make a map representing movies
(defn make-movie [title producer]
  {:title title :producer producer})

;; 2. (define-struet boyfriend (name hair eyes phone))

;; A boyfriend is a map
;; (make-boyfriend name hair eyes phone)
;; where name hair eyes are symbols and phone is a string.

;; make-boyfriend : symbol symbol symbol string -> boyfriend
;; make map representing boyfriends
(defn make-boyfriend [name hair eyes phone]
  {:name name :hair hair :eyes eyes :phone phone})


;; 3. (define-struct cheerleader (name number))

;; A cheerleader is a map
;;  (make-cheerleader name number)
;; where name is a symbol and number is a number

;; make-cheerleader : symbol symbol -> cheerleader
;; Make a cheerleader map
(defn make-cheerleader [name number]
  {:name name :number number})


;; 4. (define-struct CD (artist title price))

;; A CD is a map
;; (make-CD artist title price)
;; where artist and title are symbols and price is a number

;; make-CD :  symbol string number -> CD
;; make a CD map
(defn make-CD [artist title price]
  {:artist artist  :title title  :price price})


;; 5. (define-struct sweater (material size producer))

;; A sweater is a map
;;  (make-sweater material size producer)
;; where material and producer are symbols and size is a number

;; make-sweater : symbol number symbol -> sweater
;; make a sweater map
(defn make-sweater [material size producer]
  {:material material  :size size  :producer producer})


;; Exercise 6.4.2 Provide a structure definition and a data definition for
;; representing  points in time since midnight. A point in time consists of three
;; numbers: hours, minutes, and seconds

;; A `time` is a map representing points of time since midnight.
(defn make-time [hours minutes seconds]
  {:hours hours :minutes minutes :seconds seconds})
;; where hours minutes seconds are numbers.

;; Examples
(make-time 0 0 0)  ;; midnight
(make-time 12 0 0) ;; noon.
(make-time 21 30 30)


;; Exercise 6.4.3 Provide a structure definition and a data definition for rep-
;; resenting three-letter words. A word consists of letters, which we represent
;; with the symbols 'a through 'z.

;; sort-word is a map representing a three letter word.
(defn make-short-word [let1 let2 let3]
  {:let1 let1 :let2 let2 :let3 let2})
;; where let1 let2 let3 are all one char symbols. 'a 'b ... 'z

;; Examples
(make-short-word 'c 'a 't)
(make-short-word 'd 'o 'g)
(make-short-word 'h 'a 't)


;;; 6.5 Designing functions for compound data  - pg 98

;; Example 1 - Student

;; Let us consider functions that process student records at a school. If
;; a student's interesting properties for a school are

;; 1. the first name,
;; 2. the last name, and
;; 3. the name of the home-room teacher

;; Data Definition

;; A `student` is a map
;; make-student : 'symbol symbol symbol -> student
(defn make-student [last first teacher]
  {:last last :first first :teacher teacher})

;; Contract:
;; subst-teacher : student symbol -> student

;; Purpose:
;; Create a student map with a new teacher name if the teacher's
;; name matches 'Fri.

;; Examples:
;; (= (make-student 'Find 'Matthew 'Amanda)
;;    (subst-teacher (make-student 'Find 'Matthew 'Amanda) 'Elise))

;; (= (make-student 'Matthew 'Elsie)
;;    (subst-teacher (make-student 'Find 'Matthew 'Fritz) 'Elsie))

;; Template
(defn process-student [a-student]
  ,,, (:last a-student),,,
  ,,, (:first a-student),,,
  ,,, (:teacher a-student) ,,,)

;; Definition:
(defn subst-teacher [a-student a-teacher]
  (cond
    (= (:teacher a-student) 'Fritz)
    (make-student (:last a-student)
                  (:first a-student)
                  a-teacher)
    :else a-student))

;; Tests
(deftest test-subst-teacher
  (is (= (make-student 'Find 'Matthew 'Amanda)
         (subst-teacher (make-student 'Find 'Matthew 'Amanda) 'Elise)))
  (is (= (make-student 'Find 'Matthew 'Elsie)
         (subst-teacher (make-student 'Find 'Matthew 'Fritz) 'Elsie))))

