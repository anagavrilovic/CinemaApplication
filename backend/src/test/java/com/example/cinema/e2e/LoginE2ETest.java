package com.example.cinema.e2e;

import com.example.cinema.arguments_provider.NotValidUsernameAndPasswordArgumentsProvider;
import com.example.cinema.arguments_provider.UsernameAndPasswordArgumentsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @ArgumentsSource(UsernameAndPasswordArgumentsProvider.class)
    void Should_LoginSuccessfully(String username, String password) throws InterruptedException {
        login(username, password);
        Thread.sleep(1000);

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/home");
    }

    @ParameterizedTest
    @ArgumentsSource(NotValidUsernameAndPasswordArgumentsProvider.class)
    void Should_NotLogin_When_UsernameOrPasswordNotValid(String username, String password) throws InterruptedException {
        login(username, password);
        Thread.sleep(1000);

        WebElement loginErrorMessage = driver.findElement(By.id("login_error_message"));

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/");
        assertThat(loginErrorMessage.getText()).isEqualTo("Wrong email or password! Try again.");
    }

    @ParameterizedTest
    @ArgumentsSource(UsernameAndPasswordArgumentsProvider.class)
    void Should_LogoutSuccessfully(String username, String password) throws InterruptedException {
        login(username, password);
        Thread.sleep(1000);

        WebElement logoutButton = driver.findElement(By.id("logout_tab"));
        logoutButton.click();

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/");
    }

}
