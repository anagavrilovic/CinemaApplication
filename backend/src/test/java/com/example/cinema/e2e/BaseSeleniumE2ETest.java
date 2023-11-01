package com.example.cinema.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Value;

public abstract class BaseSeleniumE2ETest {

    protected String BASE_URL = "http://localhost:3000";

    protected boolean isHeadless = true;

    protected WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        //System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox");
        System.setProperty("webdriver.gecko.driver", "/opt/hostedtoolcache/geckodriver/0.33.0/x64/geckodriver");
    }

    @BeforeEach
    void setUpBeforeEach() {
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
