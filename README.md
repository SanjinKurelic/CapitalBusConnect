# Capital Bus Connect

Capital Bus Connect (CBC) is website for buying bus tickets using PayPal or bills with ability of generating tickets with QR code and viewing travel history. CBC is written in Java mainly using Spring and Hibernate framework. CBC is separated in 3 maven modules which separate repository, business and presentation layer. 

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

```
Workbench menu bar > Database > Connect to database
```

Leave everything as default just set the username and the password as following:

- database username: **cbcadmin**
- database password: **cbcadmin**
 
**Important: Setting timezone in hibernate.properties is crucial!** If you are not in **Europe/Zagreb** timezone than you need to change timezone in CBC project. In project go to:
 
 ```
CapitalBusConnect > CapitalBusConnectRepository > src > main > resources > hibernate.properties
```

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

##### Home page

After everything is set if you run project you'll get home page. On below image you can see how page looks like on big screen (ex. desktop PC) and small screen (ex. mobile phone). Web page is fully supported on mobile devices (aka responsive).

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/home.jpg)

Home page is consisted of several parts: header with menu (cart link, user page link and selector for changing web page language), banner with search box, special offers list, traffic information box and footer with contact information (which is common to all pages).

There are several languages already included in this project: English, German, Italian and Croatian.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/language.jpg)

Search bar offers user to search lines between two cities for given date. City name is autocomplete text box. This project use Pikaday date picker.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/search.jpg)

##### Special offer page

Except search bar user can find his/her trip by clicking some of the available special offers. If user clicks on "more" button in special offers list he/she will be presented with small description of destination and buy ticket button.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/offer.jpg)

##### Schedule page

After user search for desire destination for given date, he/she is presented with bus schedule as shown on images. Left image is desktop version and right is mobile site version (note: some schedule items are **removed** from image **for cosmetic purposes**). From this page user can add items to cart.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/schedule.jpg)

##### Cart page

All selected items are stored in cart. User can change number of adults and/or children and cart items will update accordingly. User can also remove items from cart. If there are no items in cart, user will be presented with message.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/cart.jpg)

There are two types of payment: **money** (paying in bus) and **PayPal**. If user is not authenticated he/she will be presented with login page, before he can continue with paying. If he/she is authenticated he/she will be presented with bought items and tickets (see *my travels page*). 

##### Login and registration page

Before paying the bus ticket, user must be authenticated. User can either login or register new account. *Name, date of birth, email* and *password* are required fields. *Name* and *surname* can only be alphabetic characters. *Date of birth* must be valid date (user should be older than 18 years old) and *email* must be valid email address. Password must be 8 characters long and must have at least one number. If some of this rules (or none) are not satisfied user is presented with error message as seen on image below. Except email login you can login with Facebook account (**note:** Facebook login is not available as source code so it's disabled in this version of CBC).

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/login.jpg)


#### User page

User page is visible only for authenticated users. There are several pages on user disposal: Settings page, my travels page and discount page.

##### Settings page

Settings page offer user ability to change personal information (except email address). Validation of user input is same as on registration page. If password is left empty, password will not be updated on saving the form.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/settings.jpg)

##### My travels page

Page where user can see his/her travel history. By clicking on ticket button server will generate ticket and QR code for user. User is obligated to show this ticket in the bus before departure. User can only view his/her tickets (security feature). 

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/ticket.jpg)

##### Discounts page

User can receive bonuses/discounts for his loyalty. He/she can use them while buying tickets. **Note:** This is not available as source code so it's disabled in this version of CBC.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/discounts.jpg)

#### Admin page

Administrators can also login to CBC system. They can view travel statistics, view user login and travel history and they can also edit bus lines.

##### Statistics page

Administrators can see the total number of passengers and the total number of trips. They can also see which users travel the most and what are the most used bus lines. This information provide better management of bus lines and gives ability to award users who travels a lot.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/statistics.jpg)

##### Users history page

User history page show users ordered by login time. Administrator can search for specific user (using search box) or he can choose from provided list by clicking on information button.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/users.jpg)

By choosing an specific user, administrator can see his/her login and travel history. By choosing an specific date in search box, administrator can see history for that specific day.

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/user.jpg)

##### Routes page

Routes page gives administrator an ability to change or add bus routes. **Note:** This is not available as source code so it's disabled in this version of CBC.

 ![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/routes.jpg)

#### Error pages

There are several error pages:

- Not found error page - if requested source is not found
- Forbidden error page - if user is not authenticated or doesnt have required permissions
- Bad request error page - if user request is malformed<sup>1</sup>

Example of error page:

![](https://github.com/SanjinKurelic/CapitalBusConnect/blob/master/images/error.jpg)

## Notice

## Licence

## Footnotes

<sup>1</sup> Bad request error page can be thrown if developer has error in code.