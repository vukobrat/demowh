package framework.wallethubtests.teams.team1.project1.feature.facebook;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestFacebookPost {
  private WebDriver driver;
  private String baseUrl;
  private String username;
  private String password;
  String post;
  private StringBuffer verificationErrors = new StringBuffer();
  WebDriverWait wait;

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.facebook.com";
    username = "#";
    password = "#";
    post = "I like pizza_" + randomString();
    wait = new WebDriverWait(driver, 20);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testFacebookPost() throws Exception {
    /* Summary: Test posting message to user profile 
     * 
     * Test Scenario: 
     * 1. Visit facebook.com page 
     * 2. Login 
     * 3. Post message to profile 
     * 4. Assert posted message
     * 
     * Expected result: Post should be visible after user post message.
     * 
     * Issue: e.g. JIRA-1234
     */
    

    //1. Visit facebook.com page 
    visitPage(baseUrl);
    //2. Login
    login(username, password);
    //3. Post message to profile
    postToProfile(post);
    //4. Assert posted message
    assertNewPost(post);
  }

  
  private void login(String username, String password) throws Exception {
    if(username != null && !username.isEmpty() && password != null && !password.isEmpty() ) {
      insertUsername(username);
      insertPassword(password);
      clickLoginButton();
      assertTrue(driver.getTitle().equals("Facebook"));
    }else {
      throw new Exception("Unvalid credentials.");
    }
    
    

  }

  
  private void assertNewPost(String message) {
    //TODO: Update logic here. Try to find user latest post's locator, and assert grabbed value
    boolean foundMessageInPageSource = driver.getPageSource().contains(message);
    assertTrue(foundMessageInPageSource);
  }

  
  // Actions:
  public void postToProfile(String message) {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
        .xpath("//textarea[@name='xhpc_message']")));
    element.sendKeys(message);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Post']"))).click();
  }

  
  public void clickLoginButton() {
    assertTrue(driver.findElement(By.id("loginbutton")).isDisplayed());
    driver.findElement(By.id("loginbutton")).click();

  }

  
  public void insertPassword(String password) {
    driver.findElement(By.id("pass")).clear();
    driver.findElement(By.id("pass")).sendKeys(password);
  }

  
  public void insertUsername(String username) {
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys(username);
  }

  
  public void visitPage(String url) {
    driver.get(baseUrl);

  }

  
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  
  // Helpers:
  public String randomString() {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    String s = "";
    Random random = new Random();
    int randomLen = 1 + random.nextInt(9);
    for (int i = 0; i < randomLen; i++) {
      char c = alphabet.charAt(random.nextInt(26));
      s += c;
    }
    return s;

  }

  
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

}
