# attenti
attenti home assignment
This is question 5 answer regarding test of https://www.metric-conversions.org/

file contains:
public void invokeBrowser() --> this opens google Chrome browser and enters https://www.metric-conversions.org/
public void closeBrowser() --> closes browser
public String expectedConv (String str , double val) --> calculates expected answer 
       based on infomation in conversion site
public String siteConv (String convType , String val , int len , int iter) --> get the actual coversion made by the site
public static void main(String[] args) --> main functions in which I call functions above:
invokeBrowser
Then for each parameter (Length,temp,weight) I compare between 
10 different values (actual and expected) which are written in an array
Finally closeBrowser
