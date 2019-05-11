# Capital Bus Connect

Capital Bus Connect (CBC) is a website for buying bus tickets using PayPal or cash with ability of viewing travel history. CBC is written in Java using Spring and Hibernate framework. CBC is separated into 3 Maven modules that separate repository, business and presentation layer. 

## Getting started

Directory *packed* contains a compiled version of the project (WAR file). The database is still required for running a project.

### Prerequisites

CBC is Maven project, which means all dependencies are handled by Maven (list of dependencies is available later in the documentation). For running the project you need to have the following items:

- Java SDK 11 or later - recommended is OpenJDK or [Oracle](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) for developing only
- Servlet server - recommended is [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi) or later
- Java IDE - recommended is [Intellij IDEA Ultimate](https://www.jetbrains.com/idea/) (or Eclipse / NetBeans)
- [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) 8 or later - it's also recommended to install MySQL Workbench
- Connector/J - available with MySQL Community Server installer
- [Node.js](https://nodejs.org/en/) 10 or later and NPM 6 or later

### Installing

Install Java SDK, Servlet server and Java IDE with the default options. Set the Java IDE the following instruction from the IDE web page, ex: [Defining Application Servers in IntelliJ IDEA](https://www.jetbrains.com/help/idea/configuring-and-managing-application-server-integration.html). Download and install Node.js - with the installer, you should also get NPM. Run MySQL Community Server Installer and install MySQL Server, MySQL Workbench, Connector/J and MySQL Router. While installing keep the default options - port should be 3306. If your production requires the other port or localhost url you need to change settings in CBC project as described later. After installing, open MySQL Workbench (or terminal) and create a new connection to the database:

```
Workbench menu bar > Database > Connect to database
```

Keep everything as default only set the username and the password as follow:

- database username: **cbcadmin**
- database password: **cbcadmin**
 
**Important: Setting time zone in hibernate.properties is crucial!** If you are not in **Europe/Zagreb** time zone, then you need to change the time zone in CBC project. In the project, go to:
 
 ```
CapitalBusConnect > CapitalBusConnectRepository > src > main > resources > hibernate.properties
```

Change both *hibernate.connection.url* and *hibernate.jdbc.time_zone* with the new time zone value (%2F is "URL code" for the slash). Here you can change other options as well. You could also set the Java IDE to connect to the database but that's optional. Connect to the database and run **database.sql** script which is provided by the project. This script will create required database tables and data for the testing.

After installing everything, open project as the Maven project and run Maven. Maven will install all required libraries used in this project.Run Node.js/NPM on the project (package.json). Before running the project you could also run **gulpfile.js** (script and style task). This will "compile" SCSS to CSS, uglify JS and combine everything in one file.

## Running

There are two accounts already registered with the downloaded project, which you can use for testing. Credentials of those users are:

*Administrator*:<br> 
username: **admin@cbc**<br> 
password: **password**

*User*:<br>
username: **user@example.com**<br> 
password: **password**

#### Home page

After everything is set if you run the project you'll get to the home page. On the image below you can see how the page looks like on the big screen (ex. desktop PC) and small screen (ex. mobile phone). The web page is fully supported on mobile devices (aka responsive).

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/home.jpg)

The home page consists of several parts: header with a menu (cart link, user page link and selector for changing web page language), banner with a search box, special offers list, traffic information box and a footer with a contact information (which is common to all pages).

There are several languages already included in this project: English, German, Italian and Croatian.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/language.jpg)

Search bar offers the user ability to search the lines between two cities by a given date. The city name is an autocomplete text box. You can select every capital city in Europe<sup>1</sup>, except remote ones like capital city of Cyprus, Iceland and Malta. This project uses Pikaday date picker.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/search.jpg)

#### Special offer page

Except search bar the user can find his or her trip by clicking some of the available special offers. If user clicks on "more" button in special offer list, he or she will be presented with a small description of the destination and buy ticket button.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/offer.jpg)

#### Schedule page

When a user search for the desired destination by a given date, he or she is presented with the bus schedule as shown in the images below. The left image is a desktop version and the right is a mobile site version (note: some schedule items are **removed** from image **for cosmetic purposes**). From this page user can add items to the cart.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/schedule.jpg)

#### Cart page

All selected items are stored in the cart. The user can change number of adults and/or children and the cart items will update accordingly. The user can also remove items from the cart. If there are no items in the cart, the user will be presented with a message.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/cart.jpg)

There are two types of payment: **money** (paying in bus) and **PayPal**. If the user is not authenticated he or she will be presented with a login page, before he or she can continue with payment. If the user is authenticated than he or she will be presented with the list of bought items and tickets (see *my travels page*). 

#### Login and registration page

Before paying the bus ticket, the user must be authenticated. The user can either login or register a new account. *Name, date of birth, email* and *password* are required fields. *Name* and *surname* can only be alphabetic characters. *Date of birth* must be valid date (the user should be older than 18 years old) and *email* must be a valid email address. The password must be at least 8 characters long and must have at least one number. If some of this rules (or all) are not satisfied the user is presented with an error message as seen in the image below. Except email login you can login with Facebook account (**note:** Facebook login is not available as source code, so it's disabled in this version of CBC).

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/login.jpg)


#### User page

The user page is visible only for the authenticated users. There are several pages on user disposal: Settings page, my travels page and discount page.

#### Settings page

The settings page offers the user ability to change their personal information (except the email address). Validation of user input is same as on the registration page. If the password is left empty, the password will not be updated while saving the form.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/settings.jpg)

#### My travels page

The my travels page is the page where the user can see his or her travel history. By clicking on the ticket button, the server will generate a ticket and QR code for the user. The user is obligated to show his or her ticket in the bus before departure. The user can only view his or her tickets (security feature).

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/ticket.jpg)

#### Discounts page

The user can receive bonuses/discounts for his loyalty. He or she can use them while buying the tickets. **Note:** This is not available as source code, so it's disabled in this version of CBC.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/discounts.jpg)

#### Admin page

The administrator can also login to CBC system. He can view travel statistics, view user login and travel history and they can also edit the bus lines.

#### Statistics page

The administrator can view the total number of passengers and the total number of trips. He can also view which users travel the most and what are the most used bus lines. This information provides better management of the bus lines and gives ability to award the users who travels a lot.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/statistics.jpg)

#### Users history page

The user history page show users ordered by login time. The administrator can search for specific users (using the search box) or he can choose from the provided list by clicking on the information button.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/users.jpg)

By choosing an specific user, the administrator can see his or her login and travel history. By choosing a specific date in the search box, the administrator can see history for that specific day.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/user.jpg)

#### Routes page

The routes page gives the administrator an ability to change or add bus routes. **Note:** This is not available as source code, so it's disabled in this version of CBC.

 ![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/routes.jpg)

#### Error pages

There are several error pages:

- Not found error page - if requested source is not found
- Forbidden error page - if the user is not authenticated or doesn't have required permissions
- Bad request error page - if the user request is malformed<sup>2</sup>

Example of an error page:

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/error.jpg)

## Dependencies

Maven dependencies:

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

NPM dependencies:

- Autoprefixer
- Gulp
- PostCSS
- SASS
- Uglify

HTML/JS dependencies:

- Pikaday

## Notice

<sup>1</sup> Europe is here limited by Russia and Turkey, so Armenia, Azerbaijan and Kazakhstan are not included. There are also countries with limited to no international recognition, which are not included: Abkhazia, Republic of Artsakh, Northern Cyprus, South Ossetia and Transnistria. An exception to this rule is Kosovo because it's located in limited area of Europe (limited by Russia and Turkey) and it's recognized by a greater number of countries than for example Transnistria. I don't want to get into politics with these project, so someone can say that this project doesn't connect all capitals in Europe and that some cities on the list are not a capital city. This information is from 5.5.2019, in the future some data could change which may not reflect information used in this project.

<sup>2</sup> The bad request error page can be thrown if the developer has error in his or her code.

## Licence

See the LICENSE file. For every question write to kurelic@sanjin.eu