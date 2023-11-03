package com.example.cinema.e2e;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReservationsE2ETest extends BaseSeleniumE2ETest {
    private static final Integer NUMBER_OF_TICKETS_TO_RESERVE = 3;

    @Test
    void Should_MakeReservationForAProjection() throws InterruptedException {
        login("ana@gmail.com", "123");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("projections_tab")));
        WebElement projectionsTab = driver.findElement(By.id("projections_tab"));
        projectionsTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("reserve_projection_button_1")));
        WebElement reserveProjectionButton = driver.findElement(By.id("reserve_projection_button_1"));
        reserveProjectionButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("number_of_tickets_input")));
        WebElement numberOfTicketsInput = driver.findElement(By.id("number_of_tickets_input"));
        numberOfTicketsInput.sendKeys(NUMBER_OF_TICKETS_TO_RESERVE.toString());

        wait.until(ExpectedConditions.elementToBeClickable(By.id("reserve_button")));
        WebElement reserveButton = driver.findElement(By.id("reserve_button"));
        reserveButton.click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("reservation_movie_name_Inception")));
        List<WebElement> newReservations = driver.findElements(By.id("reservation_movie_name_Inception"));

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/reservations");
        assertThat(newReservations.size()).isEqualTo(NUMBER_OF_TICKETS_TO_RESERVE);
    }

    @Test
    void Should_NotMakeReservationForAProjection_When_ThereIsNotEnoughTicketsForAProjection() throws InterruptedException {
        login("ana@gmail.com", "123");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("projections_tab")));
        WebElement projectionsTab = driver.findElement(By.id("projections_tab"));
        projectionsTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("reserve_projection_button_2")));
        WebElement reserveProjectionButton = driver.findElement(By.id("reserve_projection_button_2"));
        reserveProjectionButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("number_of_tickets_input")));
        WebElement numberOfTicketsInput = driver.findElement(By.id("number_of_tickets_input"));
        numberOfTicketsInput.sendKeys(NUMBER_OF_TICKETS_TO_RESERVE.toString());

        wait.until(ExpectedConditions.elementToBeClickable(By.id("reserve_button")));
        WebElement reserveButton = driver.findElement(By.id("reserve_button"));
        reserveButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("reserve_tickets_error")));
        WebElement reserveTicketsError = driver.findElement(By.id("reserve_tickets_error"));

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/reserveTickets/2");
        assertThat(reserveTicketsError.getText()).isEqualTo("Not enough tickets available for this projection.");
    }
}
