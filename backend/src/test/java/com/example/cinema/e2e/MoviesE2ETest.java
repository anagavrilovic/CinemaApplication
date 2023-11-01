package com.example.cinema.e2e;

import com.example.cinema.model.Theater;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MoviesE2ETest extends BaseSeleniumE2ETest {

    @Test
    void Should_ListMoviesForLoggedUser() throws InterruptedException {
        login();
        Thread.sleep(1000);

        WebElement moviesTab = driver.findElement(By.id("movies_tab"));
        moviesTab.click();

        WebElement movieList = driver.findElement(By.id("movie_list"));
        WebElement inceptionMovie = movieList.findElement(By.xpath("//h3[contains(text(),'Inception')]"));

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/movies");
        assertThat(movieList).isNotNull();
        assertThat(inceptionMovie).isNotNull();
    }

    @Test
    void Should_CountMoviesForLoggedUser() throws InterruptedException {
        login();
        Thread.sleep(1000);

        WebElement moviesTab = driver.findElement(By.id("movies_tab"));
        moviesTab.click();

        WebElement movieList = driver.findElement(By.id("movie_list"));
        List<WebElement> secondChildElements = movieList.findElements((By.xpath("./div/div")));

        assertThat(driver.getCurrentUrl()).isEqualTo(BASE_URL + "/movies");
        assertThat(movieList).isNotNull();
        assertThat(secondChildElements.size()).isEqualTo(7);
    }

}
