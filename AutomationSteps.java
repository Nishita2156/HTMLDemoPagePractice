package com.demo.bdd;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.failsafe.internal.util.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AutomationSteps {
	WebDriver driver;
    WebDriverWait wait;
    static int initialCount;

    @Given("the user launches the automation app")
    public void launchApp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://127.0.0.1:5500/Practice.html"); // Change path
        driver.manage().window().maximize();
    }

    @When("the user registers with username \"{string}\" and password \"{string}\"")
    public void registerUser(String username, String password) {
        driver.findElement(By.linkText("Register here")).click();
        driver.findElement(By.id("regUsername")).sendKeys(username);
        driver.findElement(By.id("regPassword")).sendKeys(password);
        driver.findElement(By.tagName("form")).submit();
    }

    @When("the user logs in with username \"{string}\" and password \"{string}\"")
    public void loginUser(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginUsername")));
        driver.findElement(By.id("loginUsername")).sendKeys(username);
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.tagName("form")).submit();
    }

    @Then("the user should see the dashboard")
    public void verifyDashboard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("appSection")));
        assertTrue(driver.findElement(By.id("appSection")).isDisplayed());
    }

    @Given("the user is logged in")
    public void ensureLoggedIn() {
        if (driver == null) launchApp();
        loginUser("testuser", "testpass");
    }

    @When("the user opens the modal")
    public void openModal() {
        driver.findElement(By.xpath("//button[text()='Open Modal 1']")).click();
    }

    @Then("the modal should display correct content")
    public void validateModalContent() {
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicModal")));
        assertTrue(modal.getText().contains("Modal from Button 1"));
        driver.findElement(By.xpath("//button[text()='Close']")).click();
    }

    @When("the user hovers over the menu")
    public void hoverMenu() {
        Actions actions = new Actions(driver);
        WebElement box = driver.findElement(By.className("hover-box"));
        actions.moveToElement(box).perform();
    }

    @Then("the hover menu should appear")
    public void checkHoverMenu() {
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("hover-menu")));
       assertTrue(menu.isDisplayed());
    }

    @When("the user adds a new list item")
    public void addItem() {
        initialCount = driver.findElements(By.cssSelector("#dynamicList li")).size();
        driver.findElement(By.xpath("//button[text()='Add New Item']")).click();
    }

    @Then("the item count should increase")
    public void verifyItemAdded() {
        List<WebElement> items = driver.findElements(By.cssSelector("#dynamicList li"));
        assertEquals(items.size(), initialCount + 1);
    }
}
