
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

class ChromeLatestWin10 implements Runnable {
	public void run() {
		FlipkartTesting r1 = new FlipkartTesting();
		try {
			r1.executeTest("Chrome", "latest", "Windows", "10", "Thread 1", "Flipkart Website testing");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class FirefoxLatestWin10 implements Runnable {
	public void run() {
		FlipkartTesting r1 = new FlipkartTesting();
		try {
			r1.executeTest("Firefox", "latest", "Windows", "10", "Thread 2", "Flipkart Website testing");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class IELatestWin10 implements Runnable {
	public void run() {
		FlipkartTesting r1 = new FlipkartTesting();
		try {
			r1.executeTest("IE", "latest", "Windows", "10", "Thread 3", "Flipkart Website testing");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class SafariMacCatalina implements Runnable {
	public void run() {
		FlipkartTesting r1 = new FlipkartTesting();
		try {
			r1.executeTest("Safari", "latest", "OS X", "Catalina", "Thread 4", "Flipkart Website testing");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class FlipkartTesting {
	public static final String USERNAME = "laukikkakade3";
	public static final String AUTOMATE_KEY = "VpZej7Y67sYSF3i6yAUh";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	public static void main(String[] args) throws Exception {
		Thread object1 = new Thread(new ChromeLatestWin10());
		object1.start();
		Thread object2 = new Thread(new FirefoxLatestWin10());
		object2.start();
		Thread object3 = new Thread(new IELatestWin10());
		object3.start();
		Thread object4 = new Thread(new SafariMacCatalina());
		object4.start();
//		Thread object5 = new Thread(new ChromeLatestWin10());
//		object5.start();
	}

	public void executeTest(String browser, String version, String os, String os_version, String test_name,
			String build_name) throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browser", browser);
		caps.setCapability("browser_version", version);
		caps.setCapability("os", os);
		caps.setCapability("os_version", os_version);
		caps.setCapability("build", build_name);
		caps.setCapability("name", test_name);
		WebDriver driver = new RemoteWebDriver(new URL(URL), caps);

		driver.get("https://www.flipkart.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		driver.findElement(By.xpath("//input[@title='Search for products, brands and more']"))
				.sendKeys("Samsung Galaxy S10");
		driver.findElement(By.xpath("//button[@class=\"L0Z3Pu\"]")).sendKeys(Keys.RETURN);
		driver.findElement(By.xpath("//a[@title=\"Mobiles\"]")).sendKeys(Keys.RETURN);
		driver.findElement(By.cssSelector(".\\_10UF8M:nth-child(5)")).click();
		driver.findElement(By.cssSelector(".\\_3tCU7L")).click();

		List<WebElement> mobileElements = driver.findElements(By.className("_13oc-S"));
		for (WebElement webElement : mobileElements) {
			String name = webElement.findElement(By.className("_4rR01T")).getText();
			String price = webElement.findElement(By.className("_1_WHN1")).getText();
			String link = webElement.findElement(By.className("_1fQZEK")).getAttribute("href");

			System.out.println(name + "\t" + price + "\t " + link);

		}

		if (driver.getTitle().equals("Mobile Price List | Compare Mobiles and Buy Online @ Flipkart")) {
			markTestStatus("success", "Result Found", driver);
		} else {
			markTestStatus("fail", "Result Not Found", driver);
		}
		driver.quit();
	}

	public static void markTestStatus(String status, String reason, WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
				+ status + "\", \"reason\": \"" + reason + "\"}}");
	}
}