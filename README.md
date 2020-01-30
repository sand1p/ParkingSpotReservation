# ParkingSpotReservation
Parking Spot Reservation System
[![Build Status](https://travis-ci.org/sand1p/1timeshare.svg?branch=master)](https://travis-ci.org/sand1p/1timeshare)
## Description: 
Assume that we are building a street parking spot reservation service. Each parking spot is identified by
its location (lat, lng). Users should be able to view street parking spots, reserve and pay for the parking
spots or cancel their reservations. Build REST API's for the following and share the github repository
with us. You can populate your database with any dummy data you want.
## Features: 
      1.See available parking spots on a map
      2. Search for an address and find nearby parking spot. (input: lat, lng, radius in meters. Output - list of
        parking spots within the radius).
      3. Reserve a parking spot
      4. View existing reservations
      5. Cancel an existing reservation
      6. Show the user the cost of the reservation
## How to run application
### Prerequisites (platform setup): 
    1. java 8 or latest
    2. sbt 
### Steps to run application in dev mode: 
    1. Install sbt in your local environment. 
    2. Clone the repository.
    3. Enter the project directory.
    4. Execute following commands in CLI.
    sbt : to enter into sbt console. Execute following commands inside sbt console.
      - clean : to clean previous executables. 
      - compile : to compile sbt application.
      - run -Dhttp.port=PORT_NUMBER -Dconfig.file=application.conf    : to deploy application locally PORT_NUMBER : e.g. 9111
    5. Now open any REST client. And execute following APIs
### API List
    Yet to be Updated.
## How to build
      Steps: 
      1. Clone project in any directory 
      2. Enter directory 
      3. Execute Command: sbt dist 
          zip file will be created and the location will be printed in logs.
      4. Unzip file, you will get and jar to deploy
      5. deploy using command on any linux machine:  
        nohup bin/vcs -J-Xms512M -J-Xmx1G -Dpidfile.path=RUNNING_PID -Dlogger.file=prod/logback.xml -Dconfig.file=prod/prod.conf 
        -Dhttp.port=9000 &> vcs.out & tailf vcs.out
        -J-Xms512M :
        -J-Xmx1G  :
        -Dpidfile.path = RUNNING_PID : to store the java process ID.
        -Dlogger.file = logback.xml : Logging configurations 
        -Dconfig.file = prod/prod.conf : Application configuration file.
        -Dhttp.port = 9000   : port on which process will be running
        Logs will be written in vcs.out file.
           
##  Tech stack
### Scala 
### Play Framework
### PlaySpecs
### XML 
### Cassandra 
 
