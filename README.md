# Web Automation Test Suite

<br/>This automated web test suite covers all the test cases mentioned in code challenge file.

# Libraries Used

* Selenium:
    * To incorporate web tests.
* TestNG:
    * To perform parallel execution of test.
    * To perform cross browser/platform test.
* Log4J:
    * To perform logging across test application for all test cases on output stream and in a file.
    
# Features:

* Generation human readable allure report
    - HTML Reports are available in the "/target/allure-report" directory having details of each test case execution.
* Logging
    - Implemented using Log4J to log at 2 destinations:
        1. Log messages on output stream
        2. Log in a file "automation.out" (residing at project level)
* Taking screenshot on failed tests
    - Available in the "/src/test/resources/failure_screencaptures" directory.
* WebDriver factory
    - WebDriverFactory class is added as part of base package to enable WebDriver initialization for cross browser and platform test.
* Encapsulation layers like test data, logic of tests, actions on web pages and so on
    - PageFactory design pattern is used to have a clean separation of layers consisting of test data, logic
    and actions on web pages.
* Configurator(via testng.xml file):
  * run tests in parallel mode;
    - Test cases executed in parallel with maximum thread count of 3.
  * ability to run tests for different browsers/OS by configuring;
    - Test can run for chrome/firefox browsers using parameters in testng.xml file.
* Allure report: 
  *Integrate to defect tracking system by using @link
  *Test order by severity by using @Severity annotation.
  *Tests groups with @Epic, @Feature, and @Stories annotations.

# Steps to execute the project:

* Method 1: Command Line:
    * Execute via command line by entering below command.
    
    Please go to the project folder and execute below command on command line.
    
    ```
    mvn clean test allure:report -DsuiteXmlFile=testng.xml -D baseurl=https://www.fromatob.com/
    ``` 
    Run the command to generate a allure report and open it in a browser: 
    ```bash
    mvn allure:serve
    ```
