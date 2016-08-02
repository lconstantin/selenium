package com.endava;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by lconstantin on 7/29/2016.
 */
public class GmailTest {
    static WebDriver webDriver;

    @BeforeClass
    public static void setUp() {

        webDriver = new FirefoxDriver();
        webDriver.get("https://gmail.com");

        webDriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @Test
    public void testGmail() {
        //get the email field
        WebElement emailField = webDriver.findElement(By.xpath("//input[@id='Email']"));
        emailField.sendKeys("");

        WebElement nextButton = webDriver.findElement(By.xpath("//input[@id='next']"));
        nextButton.click();

        WebElement passwordField = webDriver.findElement(By.xpath("//input[@id='Passwd']"));
        passwordField.sendKeys("");

        WebElement signInButton = webDriver.findElement(By.xpath("//input[@id='signIn']"));
        signInButton.click();

        WebElement composeButton = webDriver.findElement(By.xpath("//div[text()=\"COMPOSE\"]"));
        composeButton.click();

        WebElement toField = webDriver.findElement(By.xpath("//textarea[@name='to']"));
        toField.sendKeys("");
        toField.sendKeys(Keys.RETURN);

        WebElement subjectField = webDriver.findElement(By.xpath("//input[@name='subjectbox']"));
        subjectField.sendKeys("hei");

        WebElement messageBox = webDriver.findElement(By.xpath("//div[@aria-label='Message Body']"));
        messageBox.sendKeys("Test hello");

        WebElement sendButton = webDriver.findElement(By.xpath("//div[contains(@aria-label,'Send')]"));
        sendButton.click();

        WebDriverWait waitInbox = new WebDriverWait(webDriver, 10);
        WebElement checkInbox = waitInbox.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title='Inbox (1)']")));
        checkInbox.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        WebElement checkName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/span/b[contains(text(),'hei')]")));
        //table/tbody/tr[1]/td[4]/div[2]/span[contains(text(),'me')]
        checkName.click();

        //verify Body
        WebElement verifyText = webDriver.findElement(By.xpath("//div[@dir='ltr' and contains(text(),'Test hello')]"));
        String verifyBody = verifyText.getText();
        assertEquals("Test hello", verifyBody);

        //verify Subject
        WebElement verifySubj = webDriver.findElement(By.xpath("//div/h2[contains(text(),'hei')]"));
        String subjectToVerify = verifySubj.getText();
        assertEquals("hei", subjectToVerify);
    }


    @AfterClass
    public static void tearDown() {
        webDriver.close();
    }


}