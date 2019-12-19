(ns htdp1e-clj.sec01.chap03
  (:require [clojure.test :refer :all]
            [same :refer [ish? zeroish?]]))

;;;; I - Processing Simple Forms of Data
;;;; 3 - Programs are Function Plus Variable Definitions
;;   pg. 52


;;; 3.1 Composing functions

;; Example

;; Imagine the owner of a movie theater who has complete freedom
;; in setting ticket prices. The more he charges, the fewer
;; the people who can afford tickets. In a recent experiment the
;; owner determined a precise relationship between the price of a
;; ticket and average attendance. At a price of $5.00 per ticket, 120
;; people attend a performance. Decreasing the price by a dime
;; ($. 10) increases attendance by 15. Unfortunately, the increased
;; attendance also comes at an increased cost. Every performance
;; costs the owner $180. Each attendee costs another four cents
;; ($0.04) . The owner would like to know the exact relationship between
;; profit and ticket price so that he can determine the price
;; at which he can make the highest profit.


;; attendees : number -> number
;; Compute the number of `attendees` given `ticket-price`.
;; 5.00 -> 120
;; 4.00 -> 270
;; 3.00 -> 420
;; 2.00 -> 570
(defn attendees [ticket-price]
  (+ 120
     (* (/ 15 0.10) (- 5.00 ticket-price))))


(deftest test-attendees
  (is (= 120.0 (attendees 5.00)))
  (is (= 270.0 (attendees 4.00)))
  (is (= 420.0 (attendees 3.00)))
  (is (= 570.0 (attendees 2.00))))

;; cost : number -> number
;; Compute the `cost` given `ticket-price`
;; formula -> 180 + (attendees * 0.04)
;; (2.00 -> 570) -> 180 + 570 * 0.04 => 202.8
;; (3.00 -> 420) -> 180 + 420 * 0.04 => 196.8
;; (4.00 -> 270) -> 180 + 270 * 0.04 => 190.8
;; (5.00 -> 120) -> 180 + 120 * 0.04 => 184.8
(defn cost [ticket-price]
  (+ 180 (* (attendees ticket-price) 0.04)))

(deftest test-cost
  (is (= 202.8 (cost 2.00)))
  (is (= 196.8 (cost 3.00)))
  (is (= 190.8 (cost 4.00)))
  (is (= 184.8 (cost 5.00))))

;; revenue : number -> number
;; Compute the `revenue`, given the `ticket-price`
;; formula -> ticket-price * attendees
;; 2.00 * 570 => 1140.00
;; 3.00 * 420 => 1260.00
;; 4.00 * 270 => 1080.00
;; 5.00 * 120 =>  600.00

(defn revenue [ticket-price]
  (* ticket-price (attendees ticket-price)))

(deftest test-revenue
  (is (ish? 1140.0 (revenue 2.00)))
  (is (ish? 1260.0 (revenue 3.00)))
  (is (ish? 1080.0 (revenue 4.00)))
  (is (ish?  600.0 (revenue 5.00))))

;; profit : number -> number
;; Compute the `profit` as the difference between `revenue` and `costs`
;; at some ticket price
;; formula -> revenue - cost
;; 2.00 ->  1140.00 - 202.80 =>  937.20
;; 3.00 ->  1260.00 - 196.80 => 1063.2
;; 4.00 ->  1080.00 - 190.80 =>  889.2
;; 5.00 ->   600.00 - 184.80 =>  415.2

(defn profit [ticket-price]
  (- (revenue ticket-price)
     (cost ticket-price)))

(deftest test-profit
  (is (= 937.2 (profit 2.00)))
  (is (= 1063.2 (profit 3.00)))
  (is (= 889.2 (profit 4.00)))
  (is (= 415.2 (profit 5.00))))


;;; Exercises 3.1

;; Exercise 3.1.1 The next step is to make up examples for each of the functions.
;; Determine how many attendees can afford a show at a ticket price
;; of $3.00, $4.00, and $5.00. Use the examples to formulate a general rule
;; that shows how to compute the number of attendees from the ticket price.
;; Make up more examples if needed.

;; formula - decrease price by 0.10 increases attendance by 15.
;; (+ 120 (* 150 (- 5.00 x)))

;; 5.00 -> 120
;; 4.80 -> 135
;; 4.60 -> 150
;; decrease by 1.00 increase 150
;; 4.00 -> 270
;; 3.00 -> 420
;; 2.00 -> 570


;; Exercise 3.1.2 Use the results of exercise 3.1.1 to determine how much it
;; costs to run a show at $3.00, $4.00, and $5.00. Also determine how much
;; revenue each show produces at those prices. Finally, figure out how much
;; profit the monopolistic movie owner can make with each show. Which is
;; the best price (of these three) for maximizing the profit

;; cost
;; formula -> 180 + (attendees * 0.04)
;; (2.00 -> 570) -> 180 + (570 * 0.04) => 202.8
;; (3.00 -> 420) -> 180 + (420 * 0.04) => 196.8
;; (4.00 -> 270) -> 180 + (270 * 0.04) => 190.8
;; (5.00 -> 120) -> 180 + (120 * 0.04) => 184.8

;; revenue
;; formula -> ticket-price * attendees
;; 2.00 * 570 => 1140.00
;; 3.00 * 420 => 1260.00
;; 4.00 * 270 => 1080.00
;; 5.00 * 120 =>  600.00

;; profit
;; formula -> revenue - cost
;; 2.00 ->  1140.00 - 202.80 =>  937.20
;; 3.00 ->  1260.00 - 196.80 => 1063.20  ***
;; 4.00 ->  1080.00 - 190.80 =>  889.20
;; 5.00 ->   600.00 - 184.80 =>  415.20


;; too complex - break down into auxiliary definitions

(defn profit-2 [ticket-price]
  (- (* (+ 120
           (* (/ 15 0.10)
              (- 5.00 ticket-price)))
        ticket-price)
     (+ 180
        (* 0.04
           (+ 120
              (* (/ 15 0.10)
                 (- 5.00 ticket-price)))))))

(deftest test-profit-2
  (is (= 937.2 (profit-2 2.00)))
  (is (= 1063.2 (profit-2 3.00)))
  (is (= 889.2 (profit-2 4.00)))
  (is (= 415.2 (profit-2 5.00))))


;;; Exercises 3.1 cont.

;; Exercise 3.1.3 Determine the profit that the movie owner makes at $3.00,
;; $4.00, and $5.00 using the program definitions in both columns. Make sure
;; that the results are the same as those predicted in exercise 3.1 .2. I

(profit 5.00) ;; => 415.2
(profit-2 5.00) ;; => 415.2

(profit 4.00) ;; => 889.2
(profit-2 4.00) ;; => 889.2

(profit 3.00) ;; => 1063.2
(profit-2 3.00) ;; => 1063.2

(profit 2.00) ;; => 937.2
(profit-2 2.00) ;; => 937.2


;; Exercise 3.1.4 After studying the cost structure of a show, the owner dis-
;; covered several ways of lowering the cost. As a result of his improvements,
;; he no longer has a fixed cost. He now simply pays $1.50 per attendee.
;;    Modify both programs to reflect this change. When the programs are
;; modified, test them again with ticket prices of $3.00, $4.00, and $5.00 and
;; compare the results.

;; cost-3 : number -> number
;; Compute the `cost` given `ticket-price`
;; formula -> attendees * 1.50
;; (2.00 -> 570) -> 570 * 1.50 => 855.0
;; (3.00 -> 420) -> 420 * 1.50 => 630.0
;; (4.00 -> 270) -> 270 * 1.50 => 405.0
;; (5.00 -> 120) -> 120 * 1.50 => 180.0

(defn cost-3 [ticket-price]
  (* (attendees ticket-price) 1.50))

(deftest test-cost-3
  (is (= 855.0 (cost-3 2.00)))
  (is (= 630.0 (cost-3 3.00)))
  (is (= 405.0 (cost-3 4.00)))
  (is (= 180.0 (cost-3 5.00))))


;; profit : number -> number
;; Compute the `profit` as the difference between `revenue` and `costs`
;; at some ticket price
;; formula -> revenue - cost
;; 2.00 ->  1140.00 - 855.0 => 285.00
;; 3.00 ->  1260.00 - 630.0 => 630.00
;; 4.00 ->  1080.00 - 405.0 => 675.00 **
;; 5.00 ->   600.00 - 180.0 => 420.00

(defn profit-3 [ticket-price]
  (- (revenue ticket-price)
     (cost-3 ticket-price)))

(deftest test-profit
  (is (= 285.00 (profit-3 2.00)))
  (is (= 630.00 (profit-3 3.00)))
  (is (= 675.00 (profit-3 4.00)))
  (is (= 420.00 (profit-3 5.00))))


;;; 3.2 Variable Definitions

(def PI 3.14159)


;;; 3.2 Exercises

;; Exercise 3.2.1 Provide variable definitions for all constants that appear in
;; the profit program of figure 5 and replace the constants with their names.

(def BASE-TICKET-PRICE 5.00)
(def BASE-ATTENDENCE 120)
(def ATTENDANCE-PRICE-INCREMENT 0.10)
(def ATTENDANCE-INCREMENT 15)
(def BASE-COST 180.00)
(def ATTENDENCE-COST 0.04)

;; attendees-4 : number -> number
;; Compute the number of `attendees` given `ticket-price`.
(defn attendees-4 [ticket-price]
  (+ BASE-ATTENDENCE
     (* (/ ATTENDANCE-INCREMENT ATTENDANCE-PRICE-INCREMENT)
        (- BASE-TICKET-PRICE ticket-price))))

(deftest test-attendees-4
  (is (= 120.0 (attendees-4 5.00)))
  (is (= 270.0 (attendees-4 4.00)))
  (is (= 420.0 (attendees-4 3.00))))

;; cost-4 : number -> number
;; Compute the `cost` given `ticket-price`
(defn cost-4 [ticket-price]
  (+ BASE-COST
     (* (attendees-4 ticket-price)
        ATTENDENCE-COST)))

(deftest test-cost-4
  (is (= 196.8 (cost-4 3.00)))
  (is (= 190.8 (cost-4 4.00)))
  (is (= 184.8 (cost-4 5.00))))

;; revenue-4 : number -> number
;; Compute the `revenue`, given the `ticket-price`
(defn revenue-4 [ticket-price]
  (* ticket-price (attendees-4 ticket-price)))

(deftest test-revenue-4
  (is (= 1260.0 (revenue-4 3.00)))
  (is (= 1080.0 (revenue-4 4.00)))
  (is (=  600.0 (revenue-4 5.00))))

;; profit-4 : number -> number
;; Compute the `profit` as the difference between `revenue` and `costs`
;; at some ticket price

(defn profit-4 [ticket-price]
  (- (revenue-4 ticket-price)
     (cost-4 ticket-price)))

(deftest test-profit-4
  (is (= 1063.2 (profit-4 3.00)))
  (is (= 889.2 (profit-4 4.00)))
  (is (= 415.2 (profit-4 5.00))))



;;; 3.3 Finger Exercises on Composing Functions

;; Exercise 3.3.1 The United States uses the English system of (length) mea-
;; surements. The rest of the world uses the metric system. So, people who
;; travel abroad and companies that trade with foreign partners often need to
;; convert English measurements to metric ones and vice versa.
;; Here is a table that shows the six major units of length measurements
;; of the English system:

;;   1 inch     =  2.54 cm
;;   1 foot     = 12 in.
;;   1 yard     =  3 ft.
;;   1 rod      =  5 1/2 yd.
;;   1 furlong  = 40 rd.
;;   1 mile     =  8 fl.

;; Develop the functions inches->em, feet-> inches, yards->feet, rods->yards,
;; furlongs->rods, and miles->furlongs.
;;    Then develop the functions feet->em, yards->em, rods->inches, and
;; miles->feet.

;; Hint: Reuse functions as much as possible. Use variable definitions to specify
;; constants

;; Constants
(def CM-IN 2.54)
(def IN-FT 12.0)
(def FT-YD 3.0)
(def YD-RD 5.5)
(def RD-FL 40.0)
(def FL-ML 8.0)

;; inches->cm : number->number
;; Convert inches to centimeters.
(defn inches->cm [inches]
  (* inches CM-IN))

;; feet->inches : number->number
;; Convert feet to inches
(defn feet->inches [feet]
  (* feet IN-FT))

;; yards->feet : number->number
;; Convert yards to feet
(defn yards->feet [yards]
  (* yards FT-YD))

;; rods->yards : number->number
;; Convert rods to yards
(defn rods->yards [rods]
  (* rods YD-RD))

;; furlongs->rods : number->number
;; Convert furlongs to rods
(defn furlongs->rods [furlongs]
  (* furlongs RD-FL))

;; miles->furlongs : number->number
;; Convert miles to furlongs
(defn miles->furlongs [miles]
  (* miles FL-ML))

;; feet->cm : number->number
;; Convert feet to centimeters
(defn feet->cm [inches]
  (inches->cm
   (feet->inches inches)))

;; yards->cm : number->number
;; Convert yards to centimeters
(defn yards->cm [yards]
  (feet->cm
   (yards->feet yards)))

;; rods->inches : number->number
;; Convert rods to inches
(defn rods->inches [rods]
  (feet->inches
   (yards->feet
    (rods->yards rods))))

;; miles->feet : number->number
;; Convert miles to feet.
(defn miles->feet [miles]
  (yards->feet
   (rods->yards
    (furlongs->rods
     (miles->furlongs miles)))))

(deftest test-conversions
  (is (= 2.54 (inches->cm 1.0)))
  (is (= 7.62 (inches->cm 3.0)))
  (is (= 12.0 (feet->inches 1.0)))
  (is (= 36.0 (feet->inches 3.0)))
  (is (= 3.0 (yards->feet 1.0)))
  (is (= 36.0 (yards->feet 12)))
  (is (= 198.0 (rods->inches 1.0)))
  (is (= 594.0 (rods->inches 3.0)))
  (is (= 40.0 (furlongs->rods 1.0)))
  (is (= 120.0 (furlongs->rods 3.0)))
  (is (= 8.0 (miles->furlongs 1.0)))
  (is (= 32.0 (miles->furlongs 4.0)))
  (is (= 30.48 (feet->cm 1.0)))
  (is (= 121.92 (feet->cm 4.0)))
  (is (= 91.44 (yards->cm 1.0)))
  (is (= 274.32 (yards->cm 3.0)))
  (is (= 198.0 (rods->inches 1.0)))
  (is (= 396.0 (rods->inches 2.0)))
  (is (= 5280.0 (miles->feet 1.0)))
  (is (= 26400.0 (miles->feet 5.0))))


;; Exercise 3.3.2 Develop the program volume-cylinder. It consumes the radius
;; of a cylinder's base disk and its height; it computes the volume of the
;; cylinder.

;; area-circle : number -> number
;; calculate the area of a circle given the radius  
(defn area-circle [radius]
  (* PI radius radius))

;; volume-cylinder : number number -> number
;; calculate volume of cylinder given radius and height
(defn volume-cylinder [radius height]
  (* (area-circle radius) height))

(deftest test-volume-cylinder
  (is (= 0.0 (area-circle 0.0)))
  (is (= 3.14159 (area-circle 1.0)))
  (is (= 12.56636 (area-circle 2.0)))
  (is (= 0.0 (volume-cylinder 0.0 0.0)))
  (is (ish? 37.69908 (volume-cylinder 2.0 3.0))))


;; Exercise 3.3.3  Develop area-cylinder. The program consumes the radius of
;; the cylinder's base disk and its height. Its result is the surface area of the
;; cylinder.

;; circum-circle : number -> number
;; Calculate circumference of a circle given radius
(defn circum-circle [radius]
  (* 2.0 PI radius))

;; area-cylinder : number number -> number
;; Calculate the area of a cylinder
(defn area-cylinder [radius height]
  (+ (* (circum-circle radius) height)
     (* 2 (area-circle radius))))

(deftest test-area-cylinder
  (is (= (* 4.0 PI) (circum-circle 2.0)))
  (is (ish? (* 20 PI) (area-cylinder 2.0 3.0)))
  (is (ish? (* 42 PI) (area-cylinder 3.0 4.0))))


;; Exercise 3.3.4 Develop the function area-pipe. It computes the surface area
;; of a pipe, which is an open cylinder. The program consumes three values:
;; the pipe's inner radius, its length, and the thickness of its wall.
;; Develop two versions: a program that consists of a single definition and
;; a program that consists of several function definitions. Which one evokes
;; more confidence?
;; FIXME:
(defn area-pipe-1 [radius length thickness]
  (+ (* 2 (- (* PI (+ radius thickness) (+ radius thickness))
             (* PI radius radius)))
     (* length (* 2 PI (+ radius thickness)))
     (* length (* 2 PI radius))))


;; circumference : number -> number
;; Calculate the circumference of a circle with a given radius
(defn circumference [radius]
  (* 2 PI radius radius))

;; area-circle : number -> number
;; Calculates the area of a circle with given radius
(defn area-circle [radius]
  (* PI radius radius))

;; area-ring : number number -> number
;; calculates the area of a ring of outer radius and inner radius
;; area of outer circle - area of inner circle
(defn area-ring [outer-radius inner-radius]
  (- (area-circle outer-radius)
     (area-circle inner-radius)))

;; area-pipe : number number number -> number
;; Calculate the area of a pipe given inner radius length and thickness
(defn area-pipe [in-radius length thickness]
  (+ (* 2 (area-ring  (+ in-radius thickness) in-radius))
     (* length (circumference (+ in-radius thickness)))
     (* length (circumference in-radius))))

(deftest test-area-pipe
  (is (ish? (* 112 PI) (area-pipe-1 2 3 4)))
  (is (ish? (* 18 PI) (circumference 3)))
  (is (ish? (* 4 PI) (area-circle 2)))
  (is (ish? (* (- 9 4) PI ) (area-ring 3 2)))
  (is (ish? (* 112 PI) (area-pipe 2 3 4))))

(area-pipe-1 1 1 0)
(area-pipe 1 1 0)


;; Exercise 3.3.5 Develop the program height, which computes the height that
;; a rocket reaches in a given amount of time. If the rocket accelerates at a
;; constant rate g, it reaches a speed of g * t in t time units and a height of
;; 1/2 * v * t where v is the speed at t.

;; speed -> g * t
;; height -> 1/2 * v * t

;; speed-time : number number -> number
;; Calculate the speed of a rocket at time t
(defn speed-time [g t]
  (* g t))

;; height-time : number number -> number
(defn height-time [t v]
  (* 1/2 v t))

;; height-rocket : number number -> number
;; Calculate the height of a rocket at time t.
(defn height-rocket [g t]
  (height-time t (speed-time g t)))

;; tests as examples
(deftest test-heigth-rocket
  (is (= (* 20 2) (speed-time 20 2)))
  (is (= 40N (height-time 2 40)))
  (is (= 40N (height-rocket 20 2))))


;; Exercise 3.3.6 Recall the program Fahrenheit->Celsius from exercise 2.2.1.
;; The program consumes a temperature measured in Fahrenheit and prod-
;; uces the Celsius equivalent.
;;    Develop the program Celsius->Fahrenheit, which consumes a tempera-
;; ture measured in Celsius and produces the Fahrenheit equivalent.
;;   Now consider the function
;;   ;; I : number -+ number
;;   ;; to convert a Fahrenheit temperature to Celsius and back
;;   (defn (I f)
;;     (Celsius->Fahrenheit (Fahrenheit->Celsius f)))

;; Evaluate (1 32) by hand and using DrScheme's stepper. What does this
;; suggest about the composition of the two functions?

;; fahrenheit->celsius : number -> number
;; Calculate the Celcius temperature given a Fahrenheit temperature
(defn fahrenheit->celsius [fahr]
  (* 5/9 (- fahr 32)))

;; celcius->fahrenheit : number -> number
;; Calculates the Fahrenheit temperature given the Celcius temperature
(defn celsius->fahrenheit [celc]
  (+ (* 9/5 celc) 32))

;; I : number -> number
;; Convert a Fahrenheit temperature to Celsius and back to Fahrenheit
(defn I [fahr]
  (celsius->fahrenheit (fahrenheit->celsius fahr)))

(deftest test-I
  (is (= 0N (I 0)))
  (is (= 32N (I 32N))))
