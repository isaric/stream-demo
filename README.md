#stream-demo
In order to setup the project you will need to edit application.properties and put in the url of
your camera. You will also need to add the path to the opencv jar directory as a gradle property
(either in your gradle home or in the project dir) as "opencv.jar".
Finally, in order to start the application successfully you need to pass in the following argument:
   
	 -Djava.library.path=/path/to/opencv/lib

Thank you for reading!
