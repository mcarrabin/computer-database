package com.excilys.computerdatabase.selenium;

import org.openqa.selenium.WebDriver;

public class Sort {
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
    // public void testSort() throws Exception {
    // driver.get(baseUrl +
    // "/computerdatabase/home?page=1&elements=10&sort=asc");
    // driver.findElement(By.xpath("(//a[@type='button'])[5]")).click();
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
