package com.example.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Projection {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="theater_id", nullable=false)
    private Theater theater;

    @Column(nullable=false)
    private LocalDateTime startDateAndTime;

    @Column(nullable=false)
    private int ticketPrice;

    @Column(nullable=false)
    private int numberOfAvailableSeats;

    private boolean deleted;

    @OneToMany(mappedBy="projection", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public Projection() {}

    public Projection(Movie movie, Theater theater, LocalDateTime startDateAndTime, int ticketPrice) {
        this.movie = movie;
        this.theater = theater;
        this.startDateAndTime = startDateAndTime;
        this.ticketPrice = ticketPrice;
        this.numberOfAvailableSeats = theater.getNumberOfSeats();
    }

    public Projection(Movie movie, Theater theater, LocalDateTime startDateAndTime, int ticketPrice, boolean deleted) {
        this.movie = movie;
        this.theater = theater;
        this.startDateAndTime = startDateAndTime;
        this.ticketPrice = ticketPrice;
        this.numberOfAvailableSeats = theater.getNumberOfSeats();
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public LocalDateTime getEndDateAndTime() {
        return this.getStartDateAndTime().plusMinutes(this.getMovie().getLength());
    }

    @Override
    public String toString() {
        return "Projection{" +
                "id=" + id +
                ", movie=" + movie +
                ", theater=" + theater +
                ", startDateAndTime=" + startDateAndTime +
                ", ticketPrice=" + ticketPrice +
                ", numberOfAvailableSeats=" + numberOfAvailableSeats +
                ", deleted=" + deleted +
                '}';
    }

}
