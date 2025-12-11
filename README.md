# Improved-5C-Menu
## Overview
At the 5C's we are incredibly fortunate to be cursed with choice. From a selection of seven very stomachable dining halls, other factors often come into play other than the simple "I'm hungry". No matter what reason, all too often are students faced with a packed dining hall—completely taken by surprise. We wanted to develop a solution to this problem!

The Improved 5C Menu is a small scale (but scalable!) look into what a 5C menu app with greater functionality could look like. In our implementation we focus on four (incredibly useful) features. 

*Wait Time Estimate*: Estimates the wait time in line for food (assuming only one line for our scale)

*Occupancy*: Estimates the occupancy of the dining hall 

*Friend Location*: Lists (estimated) friends who currently occupy the dining hall

*Favorite Food*: Lists a user's favorite food(s) that are currently on the menu for that meal period

For our scale (and because we were unable to access live dining hall scan data) we decided to make our program a simulation of these features. Thus, per run of the main, we simulate a week's worth of scan data and give sample outputs for any user that was generated!

#### Sample Output:
![[./readmeimages/sampleoutput1.png]]
As seen in the output, the data generation is confirmed, then the **welcome message** is outputed. 

If *n* was inputted in the "Do you want to change..." then another user ID could be inputed at that same time and date.

if *n* was inputted in the "Quit" then the date/time could be changed, and the program would continue to run on that generated data set

## Running the code
The progam is run through Main.java through command line arguments. Upon running the code, the data will confirm generation before entering the main user loop.

For the *day*, input 1-7 with **Monday** being 1 and **Sunday** being 7 

For the *hour*, input 0-23 (24 Hr time).

For the *minute*, input 0-59.

This will then output the people in line, the dining hall occupants, wait time, and total occupancy. 

10 users will then be shown as potential inputs to 'sign-in' as. These do not need to be followed. Any number could be inputted, but it is not guarenteed the ID exists.

The User information will be inputed as if they were signing in at that particular time into the app/program. 

Two exit prompts will follow. Answering "y" to both will quit the program entirely. Answer "n" to the first will allow for more user sign-ins at that time. Answer "n" to the second and the date and time can be changed (within that week of generated data). 



## Making alterations to the Data Generation
The engine under the app and the calcuations is within the file SimulationEngine.java. Partnered with SystemManager.java, the simulation parameters can be changed at will. 

**ID** creation is based off of the *student population* with IDs being selected from 1 -> max population

#### Alterations in SystemManager.java
==These times **MUST** be inputted as minutes with 0 being the opening time== 
==If the times are altered, the **OPENTIMEOFFSET** must also be corrected== 
![[./readmeimages/staticvarssystemmanager.png]]

#### Alterations in SimulationEngine.java
==The busyness offsets can be used to correct unusual flow in the data set==
![[./readmeimages/staticvarssimengine.png]]

## Data Format

##### Scan Data
Scan data (with our generation) is formatted by day, hour, minute, user ID. Each line is a different scan

##### Menu
Menu data (manual creation) is formatted day, meal period, item. Each line is a different menu item

The menu was generated from ChatGPT with the formatting given as parameterization. 

## Public Method Documention 

#### DiningHall.java
```java
public DiningHall()
```
* Zero parameter constructure, initalizes events and dayToEvent instance variables with an empty ArrayList and Map respectively. 

```java
public void loadData(String filename)
```
* Takes file path of scan data to populate DiningHall object

```java
public ArrayList<ScanEvent> getDayEvents(int day)
```
* Returns ArrayList of all ScanEvents on a particular day

```java
public int timeSpent(int mealPeriod)
```
* Returns a random occupy time (in the dining hall) in minutes
* Uses data obtained from survey of average stay times per meal period: 
    * Breakfast Average: 28.84, Standard Deviation: 26.86
    * Lunch Average: 38.92, Standard Deviation: 12.39
    * Dinner Average: 48.09, Standard Deviation: 15.71

```java
public int[] getOccupancy(int day, int hour, int minute)
```
* Returns a length 3 integer array
    * with the format [Line size, Number of people eating, Wait time]

```java
public PriorityQueue<ScanEvent> getPQ()
```
* Returns the Priority Queue used for occupancy

```java 
public Queue<ScanEvent> getQ()
```
* Returns the Queue used for in line people

#### MenuParser.java
```java
public MenuParser(String menusamplefilename)
```

* Creates MenuParser Object with an input of the menu file path (CSV) that parses the CSV into an ArrayList of each row, and also one of all food items

```java
public ArrayList<String> getAllFoodItems()
```

* Returns all the food item names contained in the file

```java
public ArrayList<String> getItemsForDay(int whatdayisit,int whatnomealisit)
```

* Takes input of day and meal period number to return the list of item names in that meal period

#### ScanEvent.java

#### SimulationEngine.java

#### SystemManager.java

#### User.java

