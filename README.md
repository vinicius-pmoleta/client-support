# Client Support App

An Android app to simulate a client support system where the user can see support tickets from a client.

## Architecture

### Overview

This app was developed with concepts of MVP and Clean Architecture in mind. It also makes use of Android's Architecture Components (ViewModel, LiveData and Room) along side a reactive approach. These choices were made to allow a clear separation of concerns and the focus on unit testing.

The idea was to develop an offline-first app, so the database is queried first and its results displayed if available while remote API fetches are triggered and stored in the database, which is being observed by the view to get the latest updates.

### View

It uses the concept of a passive view, which doesn't have any logic and only knows how to do very simple and small things when the presenter asks to.

### Presenter

It's responsible for presentation logic and part of that is to decide which business logic to call and also how to handle their results to interact back with the view. The presenter also uses the Android Architecture components LiveData and ViewModel (this last one as a data holder) in order to handle orientation changes and to interact with the view in the correct moment of its life cycle.

In this architecture the presenter is also responsible to decide if an operation should be executed asynchronously or not, which gives to the other layers the ability to run synchronously.

The presenter can make use of **screen converters** to convert a model used in the business layer from/to a screen model that will have everything already processed to be rendered by the view, what also helps ensure the passive characteristic of the view.

### Business

This layer is where all the business logic is done and alsointeracting with the appropriate data repository, local or remote.

### Data

This layer is formed by **repositories** that can get data from remote or local data stores. They can make use of **repository converters** to convert a data model (a remote response or a local database entity) from/to a business model that will be used in the other layers.

## Running the app

The information you'll need to build and use the app are:
* Client subdomain inside Zendesk system
* Username
* Password
* View ID for filtering and fetching the tickets

You can do that by exporting environment variables in your local or CI environment:
```
export REMOTE_SUBDOMAIN=subdomain
export REMOTE_USERNAME=username
export REMOTE_PASSWORD=password
export REMOTE_VIEW_ID=123456
```

Or you can create a file _private.properties_ inside _$PROJECT_PATH/environment_ with the content:
```
REMOTE_SUBDOMAIN=subdomain
REMOTE_USERNAME=username
REMOTE_PASSWORD=password
REMOTE_VIEW_ID=123456
```