package com.example.cinema.e2e;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public abstract class BaseSeleniumE2ETest {

    protected String BASE_URL = "http://localhost:3000";

    @Value("${headless-mode}")
    protected boolean isHeadless;

    @Value("${geckodriver.path}")
    protected String geckoDriverPath;

    @Value("${firefox.path}")
    protected String firefoxPath;

    protected WebDriver driver;
    protected static boolean initialized = false;

    @BeforeEach
    void setUpBeforeEach() {
        if (!initialized) {
            System.setProperty("webdriver.firefox.bin", firefoxPath);
            System.setProperty("webdriver.gecko.driver", geckoDriverPath);

            initialized = true;
        }

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(isHeadless);

        driver = new FirefoxDriver(options);
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    protected void login(String username, String password) {
        WebElement usernameInput = driver.findElement(By.id("username_input"));
        WebElement passwordInput = driver.findElement(By.id("password_input"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login_button"));
        loginButton.click();
    }

}
