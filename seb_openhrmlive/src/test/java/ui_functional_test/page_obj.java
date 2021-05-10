
package ui_functional_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class page_obj {
	private static WebElement element = null;
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Login Page Objects~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/	
	public static WebElement txtbox_user_name(WebDriver driver) {
		element = driver.findElement(By.xpath(".//*[@id = 'txtUsername']"));
		//System.out.println("Hi");
		return element;
	}
	public static WebElement txtbox_password(WebDriver driver) {
		element = driver.findElement(By.xpath(".//*[@id = 'txtPassword']"));
		return element;
	}
	
	public static WebElement btn_login(WebDriver driver) {
		element = driver.findElement(By.xpath(".//*[@id = 'btnLogin']"));
		return element;
	}
}
