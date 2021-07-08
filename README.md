# Overview
Welcome 👋!
We at [Appstyx](https://appstyx.com) are looking forward to hire new Android developers.

If you came across this page randomly we'd like to inform you that this repository contains an exercise that is meant to evaluate your skills: if you are interested in joining our team please have a look at our [job offer](https://androidjobs.io/jobs/android-developer-b6282a3a52) and drop us an email at `info AT appstyx DOT com` first.

Please complete the following tasks to help us better address your Android experience, we can't wait to have you onboard! We'd love to see the kinds of decisions you make given the existing code, nature of the app and time constraints (please don't spend more than 4 hours on it, time is precious).

## General instructions
- fork this repository, or clone it and push it to a private repository of yours, and start from the `develop` branch to apply your changes;
- complete as many tasks as you'd like but follow the requested order;
- use all the third-party dependencies you may need but elaborate on your choices;
- share the link to the forked repository, if public, or invite `@matteinn`, if private.

## Tasks
1. Add a new dropdown field below "Last Name" to let users pick their gender. The list of genders has to be fetched from the API.
2. Add validation on the signup screen: when clicking on the "Signup" button and any of the fields is empty please show an inline error like [this](docs/validation.jpg).
3. Perform the signup API call when clicking on the "Signup" button with all fields correctly set: in case of errors please show them inline, in case of success move to the Home screen and store the token for subsequent authenticated API calls.
4. Change the routing logic to redirect already authenticated users to Home, while unauthenticated ones should land on the Signup screen.
5. On the Home screen perform the API call to fetch user data and load that info on screen (avatar, first name, last name) like [this](docs/user.jpg).
6. Implement the "Logout" logic on the home page, redirecting the user to the signup screen afterwards and clearing the auth credentials.

## Notes
- all details about the APIs can be found [here](docs/API.md)

## Your thoughts
_Please add here any comments about decisions you made while implementing the requested changes, reasoning about any dependencies you may have added to the project, any issues you may have faced, problematic code you would like to change..._

- Disclaimers :- 
 1. Sorry I took more than 4 hours about 5 hours , I was enjoying the task .
 2. Copied some generic code from my templete project
 3. Didn't clean up code because of time 
 
 Libraries used:-
 
- Used Navigation component as its new recommended way and I like working with it as maintaining backstack is easier
- Used lottie for animating loading bar 
- Used TSnackBar for showing error/success messages as it looks better than normal snackbar at bottom
- Used com.pddstudio:encrypted-preferences for encrypting shared preferences , there are better ways to do it but used it to get work done faster
- Used Retrofit for networking and glide for image loading as they are almost standard for android development
- Would like to used Moshi instead of Gson if I had some time 
- Hilt for dependency injection , I am not an expert in dependency injection and hilt make things little easier


