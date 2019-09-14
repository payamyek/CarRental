# SupremeCarRentals 
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Build Status](https://travis-ci.com/payamyek/SupremeCarRentals.svg?branch=master)](https://travis-ci.com/payamyek/SupremeCarRentals) [![GitHub issues](https://img.shields.io/github/issues/Naereen/StrapDown.js.svg)](https://github.com/payamyek/SupremeCarRentals/issues)

SupremeCarRentals is a Java Program that can be used by a car rental company to create rental reservations, store customer information, and create a PDF invoice for reservations which can be printed.

# License
[MIT](https://github.com/payamyek/SupremeCarRentals/blob/master/LICENSE)


# Usage

## Login Menu

- To login into the application use the following credentials: Username = "Admin" and Password = "password123"

## Renting a Car

- In the main menu choose "Rent Car" option which will take you to this screen: 

![ScreenShot](Screenshots/RentCar.PNG)

- Toronto Supreme Car Rentals is only open from 9:00 AM to 9:00 PM so those are the only available times 
- All reservations must be made a day in advance and it must be at least one day long in duration

- After valid reservation details are provided you will be taken to this screen:

![ScreenShot](Screenshots/ChooseCar.PNG)

- The JComboBox only allows the users to select cars that are available based on their chosen dates for their chosen vehicle type, in this case it is Economy.

- After a valid car is choosen you will asked to enter the customer's information: 

![ScreenShot](Screenshots/CustomerInfo.PNG)

- The above is an example of valid customer information
- The first and last names can contain lower and uppercase letters, periods, commas, apostrophes, and dashes
- The accepted credit cards are Visa and Mastercard and must fit those standards
- The insurance number should only be provided if the customer wants to use their own insurance and must contain 5 uppercase letters followed by two numbers
- The driver license number follows the Ontario driver license format which is LNNNN-NNNNN-NNNNN where L is a capital letter and N is a number


## Viewing Database Tables

- To view tables choose "View" in the main menu and choose any of the 3 available tables ("Car", "Reservation", "Customer")

### Car Table

![ScreenShot](Screenshots/CarTable.PNG)

### Reservation Table

![ScreenShot](Screenshots/ReservationTable.PNG)

### Customer Table

![ScreenShot](Screenshots/CustomerTable.PNG)


