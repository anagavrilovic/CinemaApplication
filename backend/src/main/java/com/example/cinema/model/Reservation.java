package com.example.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="projection_id", nullable=false)
    private Projection projection;

    private boolean canceled;

    public Reservation() {
    }

    public Reservation(User user, Projection projection) {
        this.user = user;
        this.projection = projection;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Projection getProjection() {
        return projection;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", projection=" + projection +
                ", canceled=" + canceled +
                '}';
    }

}
