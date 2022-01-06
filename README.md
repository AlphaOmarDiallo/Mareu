# Ma réu

Ce dépôt contient une mini-application pour le P4 du parcours **Grande École du Numérique**. 
This application is the property of the startup Lamzone. It helps collaborators book meetingrooms quickly. 

## Project setup

This is an Android application, it is coded in Java and runs on SDK version 31. To run he project, clone this repository and open it on Android Studio. 

## Project architecture

The application displays one activity within which you find a recyclerview with all the meetings booked. You can delete a meeting, open the details of a meeting (new activity), create a meeting (new activity) and filter the meetings by meetingrooms or date.

<img width="489" alt="Use_cases_Mareu" src="https://user-images.githubusercontent.com/73855044/148439478-c70ebc41-453e-4f0a-86cc-339d7074375a.png">

<img width="449" alt="Class_diagram_MaReu" src="https://user-images.githubusercontent.com/73855044/148439509-dc8bdd91-056a-4b3c-bb09-a254780dec36.png">

### Model package

It stores : 
  * Meeting class : parcelable, with all the informations.
  * MeetingRooms enum : it stores the meeting rooms.
  * Collaborators enum : it stores the collaborators informations.

### Events

This application uses Eventbus to simplifiy communication between the different classes. It stores the following events : 
  * DeleteMeetingEvent
  * OpenMeetingEvent
  * SendPositionEvent
They are triggered in the recycler view. They trigger these events.  
An improvment, would be to consider refactoring the app to use more performant tool as RxJava or refactor it in Kotlin and use coroutines. 

### Service

It contains the dummy API to run this application. 

### controller

It contains the classes linked to the activities and fragments. 

## External Dependencies

This is a mock app, it does live on its own. Remember, it is a project aimed to a learner. 

## Version Control

We loosely use the "Git flow" approach: master is the release branch - it should always be releasable, and only merged into when we have tested and verified that everything works and is good to go. 

Daily development is done in the development branch. Features, bugfixes and other tasks are done as branches off of develop, then merged back into develop directly or via pull requests.

Keep commit clear and self-explanatory. Clean messy branches before merge. 

## Testing

This application has 5 unit tests and 6 instrumented tests as well as one espresso recorded test. All the test are on status passed. 

#### Unit tests
1. Get meeeting with success : checking that all meetings in the API are in the meeting list.
2. Delete meeting with success : checking that it is possible to remove meeting from a list. 
3. filter Meeting by meeting room with success : checking that the meeeting room filter works. 
4. filter meeting by date with success : check that the date filter works. 
5. Create new meeting : check that a new meeting can be created.  

### Instrumented tests
1. RecyclerView should not be empty : recylcerview displays all meetings.
2. RecyclerView should delete meeting with success. 
3. RecyclerView should open meeting detail with correct data. 
4. Opens creation activity and meeting is not created.
5. Opens creation activity and meeting is created.
6. Filter by date or by meeting room.

## How to improve this project

* Display meeting participants as chips in meeting creation activity. 
* Create new filter options. 
You can either clone the repository and freely reuse it or you can make a pull request. It will only be accepted once I validate my retraining. 
