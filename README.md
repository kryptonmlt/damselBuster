# damselBuster
Scrapes the IGT Game Search to download games list and info from Url: https://www.igt.com/en/products-and-services/gaming/game-search

## Execution description:
The executable jar file is located in the DIST folder, along with the "run.bat" batch file for easy execution.
All the properties found in application.properties can be configured (even server port, email settings, logging, etc) through the command line,
example;
* java -jar -DstartType=update -DchromeDriver="path\to\damselBuster\chromedriver.exe" damselbuster-0.0.1-SNAPSHOT.jar

Apart from the correct path to chromedriver, the application only has 1 essential parameter to be set:
* java -jar -DstartType=init damselbuster-0.0.1-SNAPSHOT.jar
* java -jar -DstartType=delete damselbuster-0.0.1-SNAPSHOT.jar
* java -jar -DstartType=update damselbuster-0.0.1-SNAPSHOT.jar

###### delete:
Deletes all data in database and then calls init
###### init:
Scrapes all the games and afterwards it continues to download only the newest games daily.
###### update:
The app downloads only the newest data at 24hours interval after a 1minute initial delay.

#### Requirements to run:
* java 8
* chrome
* selenium chromedriver
* mysql database
* run damselTableStructureOnly.sql script on database for empty tables
* run damselTableStructureAndDataBackup.sqlscript on database for populated tables with games until 28/7/2017

#### Requirements to build:
* same as above + maven

#### Build command:
* mvn clean install
* (jar file is generated inside the target folder)

#### Code Structure:
* org.kryptonmlt.damselbuster: Initialisation class
	* configuration: Configures rest controllers and public paths
	* controllers: Expose the database objects via rest
	* enums: Enums found here
	* mappers: Maps to database model objects
	* pojos: Database model objects
	* repositories: Interact with the database
	* scraper: Classes which scrape the website
	* utils: Utility classes
	
#### Default Web page to view data:
* localhost:8901/

This is a simple web page to view the data inside the database along with the images
![alt text](https://raw.githubusercontent.com/kryptonmlt/damselBuster/master/readmeImages/webPageExample.png)

#### Rest interface:
* localhost:8901/games
* localhost:8901/games/game{id}
* localhost:8901/games/game{id}/getImage
* localhost:8901/platforms/{gameid}
* localhost:8901/platforms/{platformId}

## Design decisions:
The application is programmed in java/spring and tried to include a variety of features such as the;
* rest controller to expose the data
* JPA repositories to allow for easy interaction with the database
* entities which are then mapped to the connected database and viceversa
* Quartz Scheduling - for job scheduling
* Logging both to file and console
* Email
* Unit Tests.
 
Selenium chromedriver was used to scrape the IGT GameSearch website. The website was not straight forward to scrape as it did not offer any web api to extract the game information.
It also had an inner content page which was being updated with the games information at each click/interaction.
The website loaded the content page through an encrypted form data POST, which would have taken a long time to try to mimic. Hence selenium allowed me to interact directly with the Web Elements.
The main problem I encountered was that at certain times the website does not return the games (screenshot below [1]). To solve this I stored the current state and then restarted the selenium driver
and continued from where it left off.
 
The application also includes some utility classes for some post/pre processing of the data such as Image saving/downloading/reading
and String Utilities to extract the reel/way/line/credit information. This was done due to the fact that the website had no clear structure of encoding this.
Another way would have been to crawl this information through the filters but it would have been much more troublesome as there are multiple games using the same description and name. 

###### [1] - Issue: Content Page does not load
![alt text](https://raw.githubusercontent.com/kryptonmlt/damselBuster/master/readmeImages/pageNotReturningData.png)

## Closing Remarks:
Given more time there are more things I could have done example;
* text analysing the individual Game pages to identify the real/way/line/credit in cases where the data is unstructured.
* Implement Mocking in testing to mock certain selenium operations. 
* A more advanced web page which allows you to search and order the items.
* Easier configuration - Instead of passing the arguments in console as shown above we could use an external config file
* Health monitor system such as spring actuator