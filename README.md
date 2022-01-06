# Ma réu

Ce dépôt contient une mini-application pour le P4 du parcours **Grande École du Numérique**. 
This application is the property of the startup Lamzone. It helps collaborators book meetingrooms quickly. 

## Project setup

This is an Android application, it is coded in Java and runs on SDK version 31. To run he project, clone this repository and open it on Android Studio. 

## Project architecture

The application displays one activity within which you find a recyclerview with all the meetings booked. You can delete a meeting, open the details of a meeting (new activity), create a meeting (new activity) and filter the meetings by meetingrooms or date.

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
They are triggered in the recycler view nested in the viewpager. They trigger these events.  
An improvment, would be to consider refactoring the app to use more performant tool as RxJava or refactor it in Kotlin and use coroutines. 

### Service

It contains the dummy API to run this application. 

### ui.neighhbour_list

It contains the classes linked to the activities and fragments. 

## External Dependencies

This is a mock app, it does live on its own. Remember, it is a project aimed to a learner. 

## Version Control

We loosely use the "Git flow" approach: master is the release branch - it should always be releasable, and only merged into when we have tested and verified that everything works and is good to go. 

Daily development is done in the development branch. Features, bugfixes and other tasks are done as branches off of develop, then merged back into develop directly or via pull requests.

Keep commit clear and self-explanatory. Clean messy branches before merge. 

## Testing

This application has 5 unit tests and 5 instrumented tests as well as one espresso recorded test. All the test are on status passed. 

#### Unit tests
1. Delete neighbour with success.
2. Get neighbour with sucess.
3. Get favorite neighbour with success.
4. Display favorite neighbour only.
5. Add to favorite. 

### Instrumented tests
1. MyNeighbourList_OpenUserFullProfileAction_ShouldOpenUserProfileWithItsData
2. NeighboursList_shouldNotBeEmpty
3. UserInformationActivity_nameIsDisplayed_ShouldMatchUserNameThatWasClickedOn
4. List_deleteAction_shouldRemoveItem
5. FavouriteNeighbourFragment_ShouldOnlyDisplayFavoriteNeighbours

### Espresso recorded test
1. List neighbour activity test 2

## How to improve this project

You can either clone the repository and freely reuse it or you can make a pull request. 
