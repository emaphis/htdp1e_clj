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

(deftest test-is-5?
  (is (= true (is-5? 5)))
  (is (= false (is-5? 0))))


;; is-between-5-6? : number -> boolean
;; Determine whether `n` is between 5 and 6 (exclusive)
(defn is-between-5-6? [n]
  (and (< 5 n) (< n 6)))


;; is-between-5-6-or-over-10? : number -> boolean
;; to determine whether `n` is between 5 and 6 (exclusive)
;; or larger than or equal to 1 0
(defn is-between-5-6-or-over-10? [n]
  (or (is-between-5-6? n) (>= n 10)))


;;; Exercises 4.2

;; Exercise 4.2.1 Translate the following five intervals on the real line into
;; Scheme functions that accept a number and return `true` if the number is
;; in the interval and `false` if it is outside:

;; 1. the interval (3, 7]:
(defn is-between-3-7? [n]
  (and (> n 3 ) (<= n 7)))

(deftest test-is-between-3-7?
  (is (= false (is-between-3-7? 1)))
  (is (= false (is-between-3-7? 3)))
  (is (= true (is-between-3-7? 4)))
  (is (= true (is-between-3-7? 7)))
  (is (= false (is-between-3-7? 8))))


;; 2. the interval [3, 7]:
(defn is-between-3-7-ex? [n]
  (and (>= n 3) (<= n 7)))

(deftest test-is-between-3-7-ex?
  (is (= false (is-between-3-7-ex? 1)))
  (is (= true (is-between-3-7-ex? 3)))
  (is (= true (is-between-3-7-ex? 4)))
  (is (= true (is-between-3-7-ex? 7)))
  (is (= false (is-between-3-7-ex? 8))))


;; 3. the interval [3, 9):
(defn is-between-3-9? [n]
  (and (>= n 3) (< n 9)))

(deftest test-is-between-3-9?
  (is (= false (is-between-3-9? 2)))
  (is (= true (is-between-3-9? 3)))
  (is (= true (is-between-3-9? 4)))
  (is (= false (is-between-3-9? 9)))
  (is (= false (is-between-3-9? 10))))


;; union of (1, 3) and (9, 11):
(defn is-between-1-3-and-9-11? [n]
  (or (and (> n 1) (< n 3))
      (and  (> n 9) (< n 11))))

(deftest test-is-between-1-3-and-9-11?
  (is (= false (is-between-1-3-and-9-11? 1)))
  (is (= true  (is-between-1-3-and-9-11? 2)))
  (is (= false (is-between-1-3-and-9-11? 3)))
  (is (= false (is-between-1-3-and-9-11? 5)))
  (is (= false (is-between-1-3-and-9-11? 9)))
  (is (= true  (is-between-1-3-and-9-11? 10)))
  (is (= false (is-between-1-3-and-9-11? 11)))
  (is (= false (is-between-1-3-and-9-11? 12))))

;; 5. and the range o f numbers outside of [1, 3].
(defn is-outside-1-3? [n]
  (or (< n 1) (> n 3)))

(deftest test-is-outside-1-3?
  (is (= true (is-outside-1-3? 0)))
  (is (= false (is-outside-1-3? 1)))
  (is (= false (is-outside-1-3? 2)))
  (is (= false (is-outside-1-3? 3)))
  (is (= true (is-outside-1-3? 4))))


;; Exercise 4.2.2 Translate the following three Scheme functions into intervals
;; on the line of reals:
;; 1. (define (in-interval-l ? x)
;;       (and « -3 x) « x 0)))
;; 2. (define (in-interval-2 ? x)
;;       (or « x 1) (> x 2) ))
;; 3. (define (in-interval-3 ? x)
;;       (not (and « = 1 x) « = x 5))))
;; Also formulate contracts and purpose statements for the three functions.


;; Show the important steps. Use the pictures to check your results.

;; in-interval-1 : number -> boolean
;; returns true if given number is between -3 and 0
;;    ------(----------)----
;;     -4  -3  -2  -1  0  1
(defn in-interval-1 [x]
  (and (< -3 x) (< x 0)))

(deftest test-in-interval-1
  (is (= false (in-interval-1 -4)))
  (is (= false (in-interval-1 -3)))
  (is (= true  (in-interval-1 -2)))
  (is (= false (in-interval-1 -0)))
  (is (= false (in-interval-1  1))))


;; in-interval-2 : number -> boolean
;; returns true if given number is less than 1 exclusive or greater than 2 exclusive
;;    ------)---(------
;;      0   1   2   3 
(defn in-interval-2 [x]
  (or (< x 1) (> x 2)))

(deftest test-in-interval-2
  (is (= true  (in-interval-2 0)))
  (is (= false (in-interval-2 1)))
  (is (= false (in-interval-2 2)))
  (is (= true  (in-interval-2 3))))


;; in-interval-3 : number -> boolean
;; returns true if given number is less than 1 and greater than 5
;;    ------)---------------(------
;;      0   1   2   3   4   5   6
(defn in-interval-3 [x]
  (not (and (<= 1 x) (<= x 5))))

(deftest test-in-interval-3
  (is (= true  (in-interval-3 0)))
  (is (= false (in-interval-3 1)))
  (is (= false (in-interval-3 2)))
  (is (= false (in-interval-3 5)))
  (is (= true  (in-interval-3 6))))


;; Evaluate the following expressions by hand:

;; 1. (in-interval-l ? - 2)
;; 2. (in-interval-2 ? - 2)
;; 3. (in-interval-3 ? - 2)

(in-interval-1 -2) ;; => true

(in-interval-2 -2) ;; => true

(in-interval-3 -2) ;; => true


;; Exercise 4.2.3 Mathematical equations in one variable are claims about an
;; unknown number. For example, the quadratic equation
;;    x2 + 2 · x + 1 = 0
;; is a claim concerning some unknown number x . For x = - 1, the claim
;; holds:
;;    For x = 1, it doesn't, because
;;    x2 + 2 · x + 1 = ( 1 ) 2 + 2 · ( 1 ) + 1 = 1 + 2 + 1 = 4 ,
;; and 4 is not equal to O. A number for which the claim holds is called a
;; solution to the equation.
;; We can use Scheme to formulate equational conditions as a function.
;; If someone then claims to have a solution, we can use the function to test
;; whether the proposed solution is, in fact, a solution. Our running example
;; corresponds to the function

;; equationl : number --+ boolean
;; to determine whether x is a solution for x2 + 2 . x + 1 = 0
(defn equationl [x]
  (= (+ (* x x) (+ (* 2 x) 1)) 0))

;; When we apply equationl to some number, we get t r u e or false:

(equationl -1) ;; => true

;; and

(equationl 1) ;; => false

;; Translate the following equations into Scheme functions:


;;; 1. 4 * n + 2 = 62

;; equation-1 : number -> boolean
;; f(10) = 42
;; f(12) = 50
;; f(14) = 58
;; f(16) = 62
(defn equation-1 [n]
  (= 62 (+ (* 4 n) 2)))

(deftest test-equation-1
  (is (= false (equation-1 10)))
  (is (= false (equation-1 12)))
  (is (= false (equation-1 14)))
  (is (= true  (equation-1 15))))


;;; 2. 2 * n^2 = 102

;; equation-2 : number -> boolean
;; f(10) = 200
;; f(12) = 288
;; f(14) = 392
(defn equation-2 [n]
  (= 102 (* 2 n n)))

(deftest test-equation-2
  (is (= false (equation-2 10)))
  (is (= false (equation-2 12)))
  (is (= false (equation-2 14))))


;;; 3. 4*n^2 + 6 + 2 = 464
;; equation-3 : number -> boolean
;; f(10) = 462
;; f(12) = 650
;; f(14) = 870
(defn equation-3 [n]
  (= 462 (+ (* 4 n n) (* 6 n) 2)))

(deftest test-equation-3
  (is (= true  (equation-3 10)))
  (is (= false (equation-3 12)))
  (is (= false (equation-3 14))))


;;; Exercise 4.2.4 - Reformulate test cases 2.2.1, 2.2.2, 2.2.3, 2.2.4

;; 2.2.1
(defn fahrenheit->celsius [far]
  (* (- far 32.0) (/ 5.0 9.0)))

(= (fahrenheit->celsius 32)  0.0)
(= (fahrenheit->celsius 0) -17.77777777777778)
(= (fahrenheit->celsius 68) 20.0)
(= (fahrenheit->celsius 100) 37.77777777777778)
(= (fahrenheit->celsius 212) 100.0)
(= (fahrenheit->celsius -40) -40.0)


;;; 2.2.2
(defn dollar->euro [dol]
  (* dol 0.90))

(= (dollar->euro 0.00) 0.0)
(= (dollar->euro 1.00) 0.9)
(= (dollar->euro 100.0) 90.0)


;;; 2.2.3
(defn triangle [side height]
  (* 0.5 side height))

(= (triangle 0 0) 0.0)
(= (triangle 10 2) 10.0)
(= (triangle 4 2) 4.0)


;;; 2.2.4
(defn convert3 [num1 num2 num3]
  (+ (* num3 100) (* num2 10) num1))

(= (convert3 0 0 0) 0)
(= (convert3 1 1 1) 111)
(= (convert3 1 2 3) 321)
