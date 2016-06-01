package com.excilys.computerdatabase.selenium;

import org.openqa.selenium.WebDriver;

public class CreationValide {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    //
    // @Before
    // public void setUp() throws Exception {
    // driver = new FirefoxDriver();
    // baseUrl = "http://localhost:8080/";
    // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    // }
    //
    // @Test
    // public void testCreationValide() throws Exception {
    // driver.get(baseUrl + "/computerdatabase/home");
    // driver.findElement(By.id("addComputer")).click();
    // driver.findElement(By.id("computerName")).clear();
    // driver.findElement(By.id("computerName")).sendKeys("test");
    // new
    // Select(driver.findElement(By.id("companyId"))).selectByVisibleText("ACVS");
    // driver.findElement(By.id("add-button")).click();
    // }
    //
    // @After
    // public void tearDown() throws Exception {
    // driver.quit();
    // String verificationErrorString = verificationErrors.toString();
    // if (!"".equals(verificationErrorString)) {
    // fail(verificationErrorString);
    // }
    // }
    //
    // private boolean isElementPresent(By by) {
    // try {
    // driver.findElement(by);
    // return true;
    // } catch (NoSuchElementException e) {
    // return false;
    // }
    // }
    //
    // private boolean isAlertPresent() {
    // try {
    // driver.switchTo().alert();
    // return true;
    // } catch (NoAlertPresentException e) {
    // return false;
    // }
    // }
    //
    // private String closeAlertAndGetItsText() {
    // try {
    // Alert alert = driver.switchTo().alert();
    // String alertText = alert.getText();
    // if (acceptNextAlert) {
    // alert.accept();
    // } else {
    // alert.dismiss();
    // }
    // return alertText;
    // } finally {
    // acceptNextAlert = true;
    // }
    // }
}
