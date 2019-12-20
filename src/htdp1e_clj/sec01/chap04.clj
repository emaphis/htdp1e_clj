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

;; When we apply equational to some number, we get true or false:

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



;;; 4.3 Conditionals and Conditional Functions - pg 68.

(def n 25)

(cond
  (< n 10) 5.0
  (< n 20) 5
  (< n 30) true)

(cond
  (<= n 1000) 0.040
  (<= n 5000) 0.045
  (<= n 10000) 0.055
  :else 0.060)


;;; Exercises 4.3

;; Exercise 4.3.1 Decide which of the following two cond-expressions is legal:

(cond
  (< n 10) 20
  (> n 20) 0
  :else 1)

;; (cond
;;   (< n 10) 20
;;   (and (> n 20) (<= n 30))  ; no answer clause
;;   :else 1)

;; (cond (< n 10) 20
;;       (* 10 n)      ; no answer clause
;;       :else 555)


;; Exercise 4.3.2 What is the value of

(def n1 15000)

(cond
  (<= n1 1000) 0.040
  (<= n1 5000) 0.045
  (<= n1 10000) 0.055
  (> n1 10000) 0.060)

;; (a)   500 => 0.040
;; (b)  2800 => 0.045
;; (c) 15000 => 0.060


;; Exercise 4.3.3 What is the value of

(def n2 15000)

(cond
  (<= n2 1000) (* 0.040 1000)
  (<= n2 5000) (+ (* 1000 0.040)
                  (* (- n2 1000) 0.045))
  :else (+ (* 1000 0.040)
           (* 4000 0.045)
           (* (- n2 10000) 0.055)))

;; (a)   500 =>  40.0
;; (b)  2800 => 121.0
;; (c) 15000 => 495.0


;; Example
;; With the help of cond-expressions, we can now define the interest rate
;; function that we mentioned at the beginning of this section. Suppose the
;; bank pays 4% for deposits of up to $1,000 (inclusive), 4.5% for deposits of
;; up to $5,000 (inclusive), and 5% for deposits of more than $5,000. Clearly,
;; the function consumes one number and produces one:

;; interest-rate : number -> number
;; Determine the interest rate for a given `amount`
(defn interest-rate-1 [amount]
  (cond (<= amount 1000) 0.040
        (<= amount 5000) 0.045
        :else 0.050))

(interest-rate-1 1000)

(deftest test-interest-rate-1
  (is (= 0.040 (interest-rate-1 1000)))
  (is (= 0.045 (interest-rate-1 5000)))
  (is (= 0.050 (interest-rate-1 8000))))



;;; 4.4 Designing Conditional Functions

;; interest-rate : number -+ number
;; to determine the interest rate for the given amount >= 0
(defn  iterest [amount]
  (cond
    (<= amount 1000) 0.055
    (<= amount 5000) 0.050
    (> amount 5000) 0.045))

;; <--[-----](---------------](------------------------->
;;    0     1000            5000                   10000


;;; Exercises 4.4 - pg 74

;; Exercise 4.4.1 Develop the function interest. Like interest-rate, it consumes
;; a deposit amount. Instead of the rate, it produces the actual amount of
;; interest that the money earns in a year. The bank pays a flat 4% for deposits
;; of up to $1,000, a flat 4.5% per year for deposits of up to $5,000, and a flat
;; 5% for deposits of more than $5,000 .

;;  <---](---------](------>
;;     1000       5000

(defn interest [amount]
  (cond
    (<= amount 1000) (* amount 0.040)
    (<= amount 5000) (* amount 0.045)
    :else (* amount 0.050)))

(deftest test-interest
  (is (= 4.0 (interest 100.0)))
  (is (= 40.0 (interest 1000.0)))
  (is (= 225.0 (interest 5000)))
  (is (= 300.0  (interest 6000))))


;; Exercise 4.4.2 Develop the function tax, which consumes the gross pay and
;; produces the amount of tax owed.
;;   For a gross pay of $240 or less, the tax is 0%
;;   For over $240 and $480 or less, the tax rate is 15%;
;;   For any pay over $480, the tax rate is 28%.

;; Data definition.
;; Tax is 3 - cases:
;; gp <= $240 is 0%.
;; gp <= $480 is 15%
;; gp > $480  is 28%

;; gross-pay : number -> number
;; Produce tax owed given gross-pay.
(defn tax [gross-pay]
  (cond
    (<= gross-pay 240.0) (* 0.00 gross-pay)
    (<= gross-pay 480.0) (* 0.15 gross-pay)
    :else (* 0.28 gross-pay)))

(deftest test-taxe
  (is (ish?   0.0 (tax 120.00)))  ; 10 hours
  (is (ish?   0.0 (tax 240.00)))  ; 20 hours
  (is (ish?  54.0 (tax 360.00)))  ; 30 hours
  (is (ish?  72.0 (tax 480.00)))  ; 40 hours
  (is (ish? 201.6 (tax 720.00)))) ; 60 hours


;; Also develop netpay. The function determines the net pay of an employee
;; from the number of hours worked. The net pay is the gross pay
;; minus the tax. Assume the hourly pay rate is $12.
;; Hint: Remember to develop auxiliary functions when a definition becomes
;; too large or too complex to manage.

(def PAY-RATE 12.00)

;; gross-pay : number -> number
;; Calculates the gross pay given hours worked and assuming pay-rate.
(defn gross-pay [hours]
  (* hours PAY-RATE))

(deftest test-gross-oay
  (is (= 240.00 (gross-pay 20)))
  (is (= 480.00 (gross-pay 40))))

;; net-pay : number -> number
;; Calculate the net pay of an employee given hours worked.
(defn net-pay [hours]
  (- (gross-pay hours)
     (tax (gross-pay hours))))

(deftest test-nat-pay
  (is (ish?  12.0 (net-pay 1)))
  (is (ish? 240.00 (net-pay 20)))
  (is (ish? 408.00 (net-pay 40)))
  (is (ish? 518.40 (net-pay 60))))


;; Exercise 4.4.3 Some credit card companies pay back a small portion of the
;; charges a customer makes over a year. One company returns

;; 1. .25% for the first $500 of charges,
;; 2. .50% for the next $1000 (that is, the portion between $500 and $1500),
;; 3. .75% for the next $1000 (that is, the portion between $1500 and $2500),
;; 4. and 1.0% for everything above $2500.

;; Thus, a customer who charges
;; $400 a year receives  $1.00, which is 0.25 = 1/100 * 400,
;; And one who charges $1,400 a year receives $5.75, which is
;; 1.25 = 0.25 * 1/100 * 500 for the first $500 and 0.50 * 1/100 * 900 = 4.50
;; for the next $900.

;; 400
(= (* 400 0.0025) 1.0)

;; 1400
(= (+ (* 500 0.0025)  ; 1.25
      (* (- 1400 500) 0.0050)) ; 4.50
   (+ 1.25 4.50))

;; Determine by hand the pay-backs for a customer who charged $2000
;; and one who charged $2600.

;; 2000
(= 
 (+ (* 500 0.0025)   ; 1.25
    (* 1000 0.0050)  ; 5.00
    (* (- 2000 1500) 0.0075)) ; 3.75 
 (+ 1.25 5.0 3.75)) ; 10.00

;; 2600
(= 
 (+ (*  500 0.0025)  ; 1.25
    (* 1000 0.0050)  ; 5.00
    (* 1000 0.0075)  ; 7.50
    (*  (- 2600 2500) 0.0100)) ; 1.00
 (+ 1.25 5.0 7.50 1.00)) ; 14.75


;; Define the function pay-back, which consumes a charge amount and
;; computes the corresponding pay-back amount

;; aux functions

(defn pay-500 [amount]
  (* amount 0.0025))

(defn pay-1500 [amount]
  (+ (pay-500 500)
     (* (- amount 500) 0.0050)))

(defn pay-2500 [amount]
  (+ (pay-1500 1500)
     (* (- amount 1500) 0.0075)))

(defn pay-over-2500 [amount]
  (+ (pay-2500 2500)
     (* (- amount 2500) 0.0100)))

(deftest test-pay-amount
  (is (=  1.00 (pay-500 400)))
  (is (=  5.75 (pay-1500 1400)))
  (is (= 10.00 (pay-2500 2000)))
  (is (= 14.75 (pay-over-2500 2600))))


(defn pay-back [amount]
  (cond
    (<= amount 500) (pay-500 amount)
    (<= amount 1500) (pay-1500 amount)
    (<= amount 2500) (pay-2500 amount)
    :else (pay-over-2500 amount)))

(deftest test-pay-back
  (is (=  1.00 (pay-back 400)))
  (is (=  5.75 (pay-back 1400)))
  (is (= 10.00 (pay-back 2000)))
  (is (= 14.75 (pay-back 2600))))


;; Exercise 4.4.4 An equation is a claim about numbers; a quadratic equation
;; is a special kind of equation. All quadratic equations (in one variable) have
;; the following general shape:

;;   a*x^2 + b*x + c = 0 .

;; In a specific equation, a, b and c are replaced by numbers, as in

;;   2*x^2 + 4*x + 2 = 0

;; or

;;  1*x^2 + 0*x + (-1) = 0 .

;; The variable `x` represents the unknown.
;; Depending on the value of x, the two sides of the equation evaluate to
;; the same value (see exercise 4.2.3). If the two sides are equal, the claim is
;; true; otherwise it is false. A number that makes the claim true is a solution.
;; The first equation has one solution, - 1, as we can easily check:

;;   2*(- 1)^2 + 4*(-1) + 2 = 2 - 4 + 2 = 0 .

;; The second equation has two solutions: +1 and -1 .
;; The number of solutions for a quadratic equation depends on the values
;; of a, b, and c. If the coefficient a is 0, we say the equation is degenerate
;; and do not consider how many solutions it has. Assuming a is not 0, the
;; equation has

;;  1. two solutions if b^2 > 4*a*C,
;;  2. one solution if b^2 = 4*a*c, and
;;  3. no solution if b^2 < 4*a*c.

;; To distinguish this case from the degenerate one, we sometimes use the
;; phrase proper quadratic equation.
;;    Develop the function how-many, which consumes the coefficients a, b,
;; and c of a proper quadratic equation and determines how many solutions
;; the equation has:

;; (how-many 1 0 -1 ) = 2
;; (how-many 2 4 2)   = 1

;; Make up additional examples. First determine the number of solutions by
;; hand, then with DrScheme
;; FIXME: Finish the problem
