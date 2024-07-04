package com.energeticsconference;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class One_moderator_multi_users {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();

        for (int count = 1; count <= 5; count++) {
        	
            ChromeOptions options = new ChromeOptions();
            
            options.addArguments("--incognito");
            
            options.addArguments("use-fake-ui-for-media-stream");

            WebDriver driver = new ChromeDriver(options);
            
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            Faker faker = new Faker();

            driver.manage().window().maximize();
            
            driver.manage().deleteAllCookies();
            
            driver.navigate().refresh();

            try {
            	
                String url = "https://conference-customer-site-dev.fromaround.com/#/login";
                
                driver.get(url);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Sign Up')]"))).click();

                String firstName = faker.name().firstName();
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='user_name']"))).sendKeys(firstName);

                String emailAddress = faker.internet().emailAddress();
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']"))).sendKeys(emailAddress);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']"))).sendKeys("Qa@12345");
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='reType']"))).sendKeys("Qa@12345");

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Continue')]"))).click();

                for (int i = 1; i <= 6; i++) {
                	
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='text'])[" + i + "]"))).sendKeys("0");
                    
                }
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Verify')]"))).click();
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'Join Meeting')]"))).click();
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='meet_id']"))).sendKeys("CON000887");
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Join')]"))).click();
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Join Meeting')]"))).click();

                driver.switchTo().frame("jitsiConferenceFrame0");
                
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='css-1hbmoh1-actionButton primary']"))).click();

                WebElement meeting_name = driver.findElement(By.xpath("//div[@class='subject-text--content css-h56vee-content']"));
                
                String meeting_text = meeting_name.getText();
                
                System.out.println(meeting_text);
                
            } catch (Exception e) {
            	
                e.printStackTrace();
                
            }
        }
    }
}