(ns htdp1e-clj.sec01.chap02)

;;;; I Processing Simple Forms of Data
;;;; 2 Number, Expressions, Simple Programs

;;; 2.1 - Numbers and Arithmetic

5  -5  2/3  17/3  1.14211356

(+ 5 5) ;; => 10
(+ -5 5) ;; => 0
(- 5 5) ;; => 0
(+ 3 4)  ;; => 7
(/ 8 12) ;; => 2/3

;; Nest expressions
(* (+ 2 2) (/ (* (+ 3 5) (/ 30 10)) 2))
;; => 48

;; (operation A ... B)
;; (sqrt A), (expt A B), (rem A B), (log A), (sin A)

(Math/sqrt 2) ;; => 1.4142135623730951
(- 1.0 0.9)  ;; => 0.09999999999999998
(- 1000.0 999.9) ;; => 0.10000000000002274


;; Exercise 2.1.1 - Find out whether DrScheme has operations for squaring a
;; number; for computing the sine of an angle; and for determining the
;; maximum of two numbers

(Math/sin 0) ;; => 0.0
(Math/sin (/ Math/PI 2.0)) ;; => 1.0
(Math/signum (Math/toRadians 90)) ;; => 1.0
(Math/max 5 6) ;; => 6


;; Exercise 2.1.2 Evaluate (sqrt 4), (sqrt 2), and (sqrt -1) in DrScheme. Then,
;; find out whether DrScheme knows an operation for determining the tangent of
;; an angle.
(Math/sqrt 4) ;; => 2.0
(Math/sqrt 2) ;; => 1.4142135623730951
(Math/sqrt -1) ;; => ##NaN - No complex numbers
(Math/tan 0) ;; => 0.0

;;; 2.2 Variables and Programs

;; Area of a circle = pi*r^2
(defn area-of-disk [rad]
  (* 3.14 (* rad rad)))

(area-of-disk 5) ;; => 78.5

;; Consume more than one value

(defn area-of-ring [outer inner]
  (- (area-of-disk outer)
     (area-of-disk inner)))

(area-of-disk 5) ;; => 78.5
(area-of-disk 3) ;; => 28.26
(area-of-ring 5 3) ;; => 50.239999999999995


;;; Exercises 2.2

;;; Exercise 2.2.1 Define the program Fahrenheit->Celsius which consumes a
;;  temperature measured in Fahrenheit and produces the Celsius equivalent.
;;  Use a chemistry or physics book to look up the conversion formula.

;; Coverts a given Fahrenheit temperature value to a Celcius value
(defn fahrenheit->celsius [far]
  (* (- far 32.0) (/ 5.0 9.0)))

(fahrenheit->celsius 32) ;; => 0.0
(fahrenheit->celsius 0) ;; => -17.77777777777778
(fahrenheit->celsius 68) ;; => 20.0
(fahrenheit->celsius 100) ;; => 37.77777777777778
(fahrenheit->celsius -40) ;; => -40.0


;; Exercise 2.2.2 Define the program dollar->euro, which consumes a number
;; of dollars and produces the euro equivalent. Use the currency table in the
;; newspaper to look up the current exchange rate.

(defn dollar->euro [dol]
  (* dol 0.90))

(dollar->euro 0.00) ;; => 0.0
(dollar->euro 1.00) ;; => 0.9
(dollar->euro 100.0) ;; => 90.0


;; Exercise 2.2.3 Define the program triangle. It consumes the length of a tri;; angle's side and its height. The program produces the area of the triangle.
;; Use a geometry book to look up the formula for computing the area of a
;; triangle.

;; Calculate the area of a triangle given a side and height
(defn triangle [side height]
  (* 0.5 side height))

(triangle 0 0) ;; => 0.0
(triangle 10 2);; => 10.0


;; Exercise 2.2.4 Define the program convert3. It consumes three digits, starting
;; with the least significant digit, followed by the next most significant
;; one, and so on. The program produces the corresponding number. For
;; example, the expected value of
;;    (convert3 1 2 3)
;; is 321. Use an algebra book to find out how such a conversion works.

(defn convert3 [num1 num2 num3]
  (+ (* num3 100) (* num2 10) num1))

(convert3 1 2 3) ;; => 321


;; Exercise 2.2.5

(defn f [n]
  (+ (/ n 3) 2))

(f 2) ;; => 8/3
(f 5) ;; => 11/3
(f 9) ;; => 5

(defn g [n]
  (+ (* n n) 10))

(g 2) ;; => 14
(g 5) ;; => 35
(g 9) ;; => 91

(defn h [n]
  (+ (* (/ 1 2) n n) 20))

(h 2) ;; => 22N
(h 5) ;; => 65/2
(h 9) ;; => 121/2

(defn i [n]
  (- 2 (/ 1 n)))

(i 2) ;; => 3/2
(i 5) ;; => 9/5
(i 9) ;; => 17/9


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 2.3 Worded Problems

;; Company XYZ & Co. pays all its employees $12 per hour. A
;; typical employee works between 20 and 65 hours per week. Develop
;; a program that determines the wage of an employee from
;; the number of hours of work

(defn wage [hours]
  (* hours 12.0))

(wage 0)  ;; => 0.0
(wage 10) ;; => 120.0
(wage 40) ;; => 480.0


;;; Exercises 2.3

;; Exercise 2.3.1 Utopia's tax accountants always use programs that compute
;; income taxes even though the tax rate is a solid, never-changing 15%. Define
;; the program tax, which determines the tax on the gross pay.
;;    Also define netpay. The program determines the net pay of an employee
;; from the number of hours worked. Assume an hourly rate of $12.

(defn grosspay [hours]
  (* hours 12.0))

(defn tax [pay]
  (* pay 0.15))

(defn netpay [hours]  ; should use `local`
  (- (grosspay hours) (tax (grosspay hours))))

(grosspay 0) ;; => 0.0
(grosspay 10) ;; => 120.0
(grosspay 40) ;; => 480.0

(tax 0) ;; => 0.0
(tax 120.0) ;; => 18.0
(tax 480.0) ;; => 72.0

(netpay 0) ;; => 0.0
(netpay 10)  ;; => 102.0
(netpay 40) ;; => 408.0


;; Exercise 2.3.2 The local supermarket needs a program that can compute
;; the value of a bag of coins. Define the program sum-coins. It consumes four
;; numbers: the number of pennies, nickels, dimes, and quarters in the bag; it
;; produces the amount of money in the bag. I

(defn sum-coins [pennies nickles dimes quarters]
  (+ (* pennies 0.01)
     (* nickles 0.05)
     (* dimes 0.10)
     (* quarters 0.25)))

(sum-coins 0 0 0 0) ;; => 0.0
(sum-coins 1 1 1 1) ;; => 0.41000000000000003
(sum-coins 1 2 3 4) ;; => 1.4100000000000001


;; Exercise 2.3.3 An old-style movie theater has a simple profit function. Each
;; customer pays $5 per ticket. Every performance costs the theater $20, plus
;; $.50 per attendee. Develop the function total-profit. It consumes the number
;; of attendees (of a show) and produces how much income the attendees
;; produce

(defn total-profit [num-attendees]
  (- (* num-attendees 5.00)
     20.00
     (* num-attendees 0.50)))

(total-profit 1)   ;; => -15.5
(total-profit 10)  ;; => 25.0
(total-profit 100) ;; => 430.0
(total-profit 500) ;; => 2230.0

(defn profit-per-attendee [num-attendess]
  (/ (total-profit num-attendess) num-attendess))

(profit-per-attendee 10) ;; => 2.5
(profit-per-attendee 100) ;; => 4.3
(profit-per-attendee 500) ;; => 4.46



;;; 2.4 Errors.

(def df ,,,)

;; badly formed
(defn P [x]
  (+ (x) 10))

(defn Q [x]
  x 10)


;;; Exercises 2.4

;; Exercise 2.4.1 Evaluate the following sentences in DrScheme, one at a time:
;;   (+ (10) 20)
;;   (10 + 20)
;;   (+ +)
;; Read and understand the error messages.

;; (+ (10) 20)
;; java.lang.Long cannot be cast to clojure.lang.IFn

;;(10 + 20)
;; java.lang.Long cannot be cast to clojure.lang.IFn

;;(+ +)
;; Cannot cast clojure.core$_PLUS_ to java.lang.Number


;; Exercise 2.4.2 Enter the following sentences, one by one, into DrScheme's
;; Definitions window and click Execute:
;; (define if 1)
;;   (+x 10))
;;
;; (define (g x)
;;   + x 10)
;;
;; (define h(x)
;;   (+x 10))
;;
;; Read the error messages, fix the offending definition in an appropriate
;; manner, and repeat until all definitions are legal

(defn f2 [x]
  (+ x 10))

(defn g2 [x]
  (+ x 10))

(defn h2 [x]
  (+ x 10))


;;; Run-time Errors:

;; Exercise 2.4.3 Evaluate the following grammatically legal Scheme expressions
;; in DrScheme's Interactions window:

;; (+ 5 (/ 1 0))

;; (sin 10 20)

;; (somef 10)

;; Read the error messages

;; (+ 5 (/ 1 0))
;; Divide by zero

;; (Math/sin 10 20)
;; No matching method sin found taking 2 args for class java.lang.Math

;; (somef 10)
;; Unable to resolve symbol: somef in this context


;; Exercise 2.4.4 Enter the following grammatically legal Scheme program
;; into the Definitions window and click the Execute button:

;; (define (somef x)
;;   (sin x x))

;; Then, in the Interactions window, evaluate the expressions:

;; (somef 1 0 2 0)

;; (some! 1 0)

;; and read the error messages. Also observe what DrScheme highlights
#_
(defn somef [x]
  (Math/sin x x))


