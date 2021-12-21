# Quick Java Concept

### by edwinmrqz

This project is intended to provide a quick way to just code in java.
Not as production, but if you want to give a quick test of a concept , algorithm, or showcase something
just clone it , and have the scripts compile and run it, linking libraries and jar files. 

For UI, you can make use Html.(more support to be added)

## file structure

* ./assets : this folder is intended for files , this gets added to the classPath directly
* ./compiled : this folder will contain the compiled files after compile.sh is executed
* ./lib : you can include jar files here.
* ./src : you can add your java files here.
* ./views : to add html Files (eventually) this folder gets added to the classpath as well.

## Support
- [x] linux
- [x] Mac
- [x] Windows (pending jar script)

## Usage

use ~: sh compile.sh to compile the project.
use ~: sh run.sh to run it

on Windows: 
use compile.bat to compile
use run.bat to run

src/Main.java is the main file that gets executed when running run.sh (run.bat), start your project there or just use that file

.framework directory contains the code associated with the small server (just some sockets)


## html Support
To create a UI using Html you can: 

1. create a Router object: dev.edwinsf.quickjavaconcept.Router
2. register your route: router.registerRoute("/", HtmlViewText.class); the class file provides the html text.
3. create a server with your router: Server server = new Server(router);
4. start your server:  server.start();
5. try and automatically call a browser to display:  server.triggerLocalBrowser(); or visit: http://127.0.0.1:4038/ 

To use an html file: 

1. Create a html file on ./views directory. 
2. Create a java class associated with the files that implements HtmlView. and provide the file name, (include the path if you added extra folders), on getViewFileName() method. 
3. register the routes on a Router , run the server.

### retrieve GET Parameters: 
to retrieve get parameter from your class extending HtmlView, do call getGETParams(), to retrieve a HashMap, with the GET parameters.

### Image and Asset support on HTML UI
to add images and assets on your htmlFile or text you provide, to place them on the ./assets folder, and on html reference them as /assets/yourfilename.extension
i.e: <img src=\"/assets/your_file.extension\"/>

### simple tag engine. 
you can also include methods that return a String , in your HtmlView class , and add them on the HTML file by using <% method() %> 

There is no support for POST request , although you can create navigation by using links. to other routes regsitered.

### stand alone jar. 
if your program relies using HTML UI and uses only content on ./views, and ./assets, you can create a stand alone jar by running, 
~: sh jar.sh
This will create a jar file named Myjar.jar that will be selfcontained.
