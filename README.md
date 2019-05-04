# Capital Bus Connect

Capital Bus Connect (CBC) is website for buying bus tickets using PayPal or bills with ability of generating tickets with QR code and viewing travel history. CBC is written in Java mainly using Spring and Hibernate framework.  

## Getting started

### Prerequisites

CBC is Maven project which means all dependencies are handled by Maven (list of dependencies is available later in documentation). For running the project you need to have the following items:

- Java SDK 11 or later - recommended is OpenJDK or [Oracle](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) for developing only
- Servlet server - recommended is [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi) or later
- Java IDE - recommended is [Intellij IDEA Ultimate](https://www.jetbrains.com/idea/) (or Eclipse / NetBeans)
- [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) 8 or later - it's also recommended to install MySQL Workbench
- Connector/J - available with MySQL Community Server installer
- [Node.js](https://nodejs.org/en/) 10 or later and NPM 6 or later

### Installing

Install Java SDK, Servlet server and Java IDE with default options. Set Java IDE following instruction from IDE web page, ex: [Defining Application Servers in IntelliJ IDEA](https://www.jetbrains.com/help/idea/configuring-and-managing-application-server-integration.html). Download and install Node.js - with installer you should also get NPM. Run MySQL Community Server Installer and install MySQL Server, MySQL Workbench, Connector/J and MySQL Router. While installing leave default options - port should be 3306. If your production require other port, localhost url you need to change settings in CBC project (described few lines down). After installing open MySQL Workbench (or terminal) and create new connection to database:

*Workbench menu bar > Database > Connect to database*

Leave everything as default just set the username and the password as following:

database username: **cbcadmin**

database password: **cbcadmin**
 
**Important: Setting timezone in hibernate.properties is crucial!**

If you are not in **Europe/Zagreb** timezone than you need to change timezone in CBC project. In project go to:
 
*CapitalBusConnect > CapitalBusConnectRepository > src > main > resources > hibernate.properties*

Change both *hibernate.connection.url* and *hibernate.jdbc.time_zone* with new timezone value (%2F is "URL code" for slash). Here you can change other options as well. You could also set Java IDE to connect to database but that's optional. Connect to database and run **database.sql** script which is provided with project. This script will create required database tables and data for testing.

After installing everything open project as Maven project and run Maven. Maven will install following libraries used in this project:

- Servlet API
- JSP API
- JSTL
- Expression Language
- Google HTML Compressor
- Spring MVC
- Spring ORM
- Spring Test
- Spring Security
- Jackson
- Hibernate Validator
- AspectJ
- Apache Commons
- Hibernate and C3PO
- junit (Jupiter)
- SLF4J
- p6spy
  
Run Node.js/NPM on project (package.json). NPM will install:

- Autoprefixer
- Gulp
- PostCSS
- SASS
- Uglify

Before running the project you could also run **gulpfile.js** (script and style task). This will "compile" SCSS to CSS, uglify JS and combine everything to one file.

### Running

After everything is set if you run project you'll get home page. 

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/home.jpg)

Home page is consisted of several parts: header with menu (cart link, user page link and selector for changing web page language), banner with search box, special offers list, traffic information box and footer with contact information (which is common to all pages).

There are several languages already included in this project: English, German, Italian and Croatian.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/language.jpg)

testing_method :: wrong_parameter :: reason (null | empty)
testing_method :: reason (no result)

DAO layer:

Wrong limit or offset => IllegalArgumentException

Wrong data used in query:
1. if result is List => empty list
2. if result is one object => null
3. if it is update or add query => IllegalArgumentException (if data is null, other checks should be done in business layer)
4. exception for above rule is TripPricesDao which return NoResultException exception for no result - buying items with price of unknown is forbidden


Bussines Layer

Wrong data:
1. if result is List => empty list
2. if result is one object => null
3. if update/add => one of Exceptions defined in exception package

List of exceptions:

1. CartService.add/update/remove => InvalidCartItemException
2. CartService.saveToDatabase => InvalidSuppliedArgumentException | UnknownUserException
3. LoginHistoryService.add => InvalidSuppliedArgumentException | UnknownUserException
4. UserService.add/remove => InvalidUserException | InvalidUserFormItemException