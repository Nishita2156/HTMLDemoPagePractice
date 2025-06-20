package com.demo.practice;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DemoUI {
	
	public static void main(String[] args) throws InterruptedException {
		//driver set up
		WebDriver dr= new ChromeDriver();
		dr.manage().window().maximize();
		dr.get("http://127.0.0.1:5500/Practice.html");
		dr.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//test modal pop-up
	    dr.findElement(By.xpath("//button[text()='Open Modal 1']")).click();
	    WebDriverWait wait=new WebDriverWait(dr,Duration.ofSeconds(10));
	    WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicModal")));
	    String content = modal.getText();
	    System.out.println("Modal content: " + content);
	    dr.findElement(By.xpath("//button[text()='Close']")).click();
	   //test pop-up notification---
	    WebElement button = dr.findElement(By.xpath("//button[text()='Show Notification']"));
        button.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("popupMsg")));
        System.out.println("Popup displayed: " + popup.isDisplayed());
        //test hover menu
        Actions actions = new Actions(dr);
        WebElement hoverBox = dr.findElement(By.className("hover-box"));
        actions.moveToElement(hoverBox).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("hover-menu")));
		//test dynamic list update
        JavascriptExecutor js=(JavascriptExecutor)dr;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(3000);
        List<WebElement> itemsBefore = dr.findElements(By.xpath("//h2[text()=\"4. Dynamic List Update\"]"));
        int initialCount = itemsBefore.size();
        dr.findElement(By.xpath("//button[text()='Add New Item']")).click();
        List<WebElement> itemsAfter = dr.findElements(By.xpath("//h2[text()=\"4. Dynamic List Update\"]"));
        assert itemsAfter.size() == initialCount + 1;
        //test registration information
        WebElement nameField = dr.findElement(By.id("regName"));
        WebElement emailField = dr.findElement(By.id("regEmail"));
        WebElement passwordField = dr.findElement(By.id("regPassword"));

        nameField.sendKeys("User1");
        emailField.sendKeys("user1@example.com");
        passwordField.sendKeys("Password123");
        js.executeScript("window.scrollBy(0,800)");
        dr.findElement(By.cssSelector("#registrationForm button[type='submit']")).click();
        Thread.sleep(1000);

        WebElement result = dr.findElement(By.id("registrationResult"));
        System.out.println("Registration Result: " + result.getText());

        // Optionally, assert the result
        if (result.getText().contains("Thank you for registering, Test User!")) {
            System.out.println("Registration test passed.");
        } else {
            System.out.println("Registration test failed.");
        }
     // To view the result before closing
        Thread.sleep(2000); 

	    dr.quit();
		
		
}
}	


