package com.example.cinema.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginE2ETest extends BaseSeleniumE2ETest {

    private static final String APP_TITLE = "Cinescape";
    private static final String PAGE_CAPTION = "Log in";

    @Test
    void Should_ShowTitleAndCaptionVisibleHtmlElements() {
        WebElement caption = driver.findElement(By.id("caption"));

        assertThat(caption.getText()).isEqualTo(PAGE_CAPTION);
        assertThat(driver.getTitle()).isEqualTo(APP_TITLE);
    }

    @Test
    void Should_LoginSuccessfully() {
        login();
        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/home");
    }

    @Test
    void Should_LogoutSuccessfully() throws InterruptedException {
        login();
        Thread.sleep(1000);

        WebElement logoutButton = driver.findElement(By.id("logout_tab"));
        logoutButton.click();

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/");
    }

}
