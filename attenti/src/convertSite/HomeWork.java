package convertSite;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

public class HomeWork {
	
	WebDriver driver;
	
	//private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	public void invokeBrowser() 
	{
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
			driver = new ChromeDriver(); // Starting chrome
			driver.manage().deleteAllCookies(); // start from scratch
			driver.manage().window().maximize(); // maximize window since by default it opens in minimum size 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // waiting enough time for the procedure to occur
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.get("https://www.metric-conversions.org");
			
			//convCelToFar("100");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void closeBrowser()
	{
		driver.quit();
	}
	
	 public String expectedConv (String str , double val)
	 {
		 if (str == "cel")
			 val = 1.8 * val + 32;
		 else if (str == "met")
			 val = val * 3.2808;
		 else if (str == "ounce")
			 val = val / 0.035274;
		 
		//System.out.println(String.format ("%.2f", val));
		 return (String.format ("%.2f", val));
	 }
	 
	 public String siteConv (String convType , String val , int len , int iter)
	 {
		 String ans = "";
		 int i, indStart, indEnd;
		
		 if (convType.equals("cel")) // celsius to Farenheit
		 {
			 if (iter == 0)
				 driver.findElement(By.className("temperature")).click();
			 driver.findElement(By.xpath("//*[@href='/temperature/celsius-to-fahrenheit.htm']")).click();

			
			 driver.findElement(By.id("argumentConv")).sendKeys(Keys.CONTROL,"a");
			 driver.findElement(By.id("argumentConv")).sendKeys(Keys.DELETE);
			 driver.findElement(By.id("argumentConv")).sendKeys(val);
			 
			 String answer = driver.findElement(By.id("answer")).getText();
			 indStart = answer.indexOf(' ');
			 indEnd = answer.indexOf('.', indStart);
			 ans = answer.substring(indStart + 1 ,  indEnd + 3);
		 }
		 else if (convType.equals("met")) // meter to feet
		 {
			 if (iter == 0)
			 {
				 driver.findElement(By.className("length")).click();
				 driver.findElement(By.xpath("//*[@href='/length/meters-to-feet.htm']")).click();
			 }		
			
			 driver.findElement(By.id("argumentConv")).sendKeys(Keys.CONTROL,"a");
			 driver.findElement(By.id("argumentConv")).sendKeys(Keys.DELETE);
			 driver.findElement(By.id("argumentConv")).sendKeys(val);
			 
			 if (iter == 0)
			 {
				 Select format = new Select(driver.findElement(By.id("format")));
				 format.selectByVisibleText("Decimal");
			 }
				 

			 String answer = driver.findElement(By.id("answer")).getText();
			 indStart = answer.indexOf(' ');
			 indEnd = answer.indexOf('.', indStart);
			 
			 if (indEnd == -1) // in case the is no '.'
			 {
				 indEnd = answer.indexOf('f');
				 ans = answer.substring(indStart + 1 ,  indEnd);
			 }
			 else
				 ans = answer.substring(indStart + 1 ,  indEnd + 3);
		 }
		 else if (convType.equals("ounce")) // ounces to gram
		 {
			 if (iter == 0)
			 {
				 driver.findElement(By.className("weight")).click();
				 driver.findElement(By.xpath("//*[@href='/weight/ounces-to-grams.htm']")).click();
			 }		
			
			 driver.findElement(By.id("argumentConv")).sendKeys(Keys.CONTROL,"a");
			 driver.findElement(By.id("argumentConv")).sendKeys(Keys.DELETE);
			 driver.findElement(By.id("argumentConv")).sendKeys(val);				 

			 String answer = driver.findElement(By.id("answer")).getText();
			 indStart = answer.indexOf(' ');
			 indEnd = answer.indexOf('.', indStart);
			 
			 if (indEnd == -1) // in case the is no '.'
			 {
				 indEnd = answer.indexOf('g');
				 ans = answer.substring(indStart + 1 ,  indEnd);
			 }
			 else
				 ans = answer.substring(indStart + 1 ,  indEnd + 3);
		 }
		 
		 if (iter == len - 1) // return to first page of site (couldn't find home button)
		 {
			 for (i=0 ; i<2 ; i++)
				 driver.navigate().back();
		 }
		
	//	 System.out.println(answer);
		 return ans;
	 }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String actualAns, expectedAns;
		
		HomeWork hw = new HomeWork();
		boolean tempCov = true , meterFeet = true, ouncGram = true;
		int i;
		
		hw.invokeBrowser();
		
		System.out.println("Testing conversion Celsius to Farenheit");
		
		double[] arr = new double[] {0,100,200,135,654.23,-200,-264,-1000,-65.65,-0.01};
		for (i=0 ; i<arr.length ; i++)
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			actualAns = hw.siteConv("cel" , Double.toString(arr[i]) , arr.length , i);
			expectedAns = hw.expectedConv("cel", arr[i]);
			if (!expectedAns.equals(actualAns))
			{
				System.out.println("Temperature was converted from " + arr[i] + " to " + actualAns + " instead of " + expectedAns);
				tempCov = false;
			}
			else
			{
				System.out.println("Temperature was converted properly for value " + arr[i]);
			}
		}
		
		if (tempCov == true)
			System.out.println("Temperature conversion from celsius to Farenheit PASSED");
		else
			System.out.println("Temperature conversion from celsius to Farenheit FAILED");
		
		
		
		System.out.println("Testing conversion meter to feet");
		
		arr = new double[] {0,101,1005,123.56,50001.9,1000001,958.56432,9999999,1.5,54};
		for (i=0 ; i<arr.length ; i++)
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actualAns = hw.siteConv("met" , Double.toString(arr[i]) , arr.length , i);
			expectedAns = hw.expectedConv("met", arr[i]);
			if (!expectedAns.equals(actualAns))
			{
				System.out.println("Length was converted from " + arr[i] + " to " + actualAns + " instead of " + expectedAns);
				meterFeet = false;
			}
			else
			{
				System.out.println("Length was converted properly for value " + arr[i]);
			}
		}
		
		if (ouncGram == true)
			System.out.println("Length conversion from celsius to Farenheit PASSED");
		else
			System.out.println("Length conversion from celsius to Farenheit FAILED");
		
		
		
System.out.println("Testing conversion ounces to gram");
		
		arr = new double[] {0,101,1005,123.56,50001.9,1000001,958.56432,9999999,1.5,54};
		for (i=0 ; i<arr.length ; i++)
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actualAns = hw.siteConv("ounce" , Double.toString(arr[i]) , arr.length , i);
			expectedAns = hw.expectedConv("ounce", arr[i]);
			if (!expectedAns.equals(actualAns))
			{
				System.out.println("Weight was converted from " + arr[i] + " to " + actualAns + " instead of " + expectedAns);
				meterFeet = false;
			}
			else
			{
				System.out.println("Weight was converted properly for value " + arr[i]);
			}
		}
		
		if (meterFeet == true)
			System.out.println("Length conversion from celsius to Farenheit PASSED");
		else
			System.out.println("Length conversion from celsius to Farenheit FAILED");
		
		hw.closeBrowser();
	}	
}
