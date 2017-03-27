# ExerciseDrawer
ExerciseDrawer is small application for drawing floorball exercises.
It is published under the Apache License, Version 2.0, what means it's free and open source.

## Launch it!
You can launch the application from [FloorballCoaches.com](http://www.floorballcoaches.com/) site.

## Screenshots
![ExerciseDrawer Screenshot 1](http://www.floorballcoaches.com/exercisedrawer/ExerciseDrawer-1.png)
![ExerciseDrawer Screenshot 2](http://www.floorballcoaches.com/exercisedrawer/ExerciseDrawer-2.png)

## Frequently Asked Questions (FAQ)
**1.) How can I draw curved and not only straight paths/moves/passes?**

Curved lines are drawn as serie of straight lines. If you push right button of your mouse the line starts just in place where previous line ends.

**2.) Application doesn't start with _"Unable to launch application"_ message**

You need to clean your Java cache. Make sure you do following:

1. Make sure list of Java applications is empty
    * Java Control Panel -> Temporary Internet Files -> View -> Applications
    * Remove all what's there (or at least ExerciseDrawer).
2. Make sure cache is empty
    * Java Control Panel -> Temporary Internet Files -> Settings -> Delete Files -> Check all 3 checkboxes -> OK
3. Double check cache is empty by going to your Cache directory (eg. C:\Users\MyProfile\AppData\LocalLow\Sun\Java\Deployment\cache), there should be no files and no folders (directories)
4. Then try to start application from this website again.

**3.) I have Java 7 Update 21 (or newer) and I am unable to start application anymore. It fails with _"Your security settings have blocked an untrusted application from running"_ error message or _"Application Blocked"_ message.**

This application is using so called self-signed certificate. Since Java 7 Update 21 these kind of [certificates are blocked](https://www.java.com/en/download/help/java_blocked.xml) by default, unless you allow their run in your Java settings. To be able to run the application you need to add 'http://www.floorbalcoaches.com' to the [Exception Site list](https://www.java.com/en/download/faq/exception_sitelist.xml).
