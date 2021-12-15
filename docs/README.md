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
- [ ] Windows (to create bash files)
- [ ] Use html actual files for the views (currently you will have to parse the file on your own)
- [ ] Create a simple template engine. (variables on htmlText and so on)

## Usage

use ~: sh compile.sh to compile the project.
use ~: sh run.sh to run it

src/Main.java is the main file that gets executed when running run.sh , start your project there or just use that file

.framework directory contains the code associated with the small server (just some sockets)


To create a UI on html, you can: 

1. create a Router object: dev.edwinsf.quickjavaconcept.Router
2. register your route: router.registerRoute("/", HtmlViewText.class); the class file provides the html text.
3. create a server with your router: Server server = new Server(router);
4. start your server:  server.start();
5. try and automatically call a browser to display:  server.triggerLocalBrowser(); or visit: http://127.0.0.1:4038/ 


