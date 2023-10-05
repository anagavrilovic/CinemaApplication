package com.example.cinema.model;

import com.example.cinema.model.enums.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String director;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    @Column(nullable=false)
    private Integer length;

    @Column(nullable=false)
    private String description;

    @OneToMany(mappedBy="movie", fetch = FetchType.LAZY)
    private List<Projection> projections = new ArrayList<>();

    private boolean deleted;

    public Movie() {}

    public Movie(String name, String director, List<Genre> genres, Integer length, String description) {
        this.name = name;
        this.director = director;
        this.genres = genres;
        this.length = length;
        this.description = description;
    }

    public Movie(String name, String director, List<Genre> genres, Integer length, String description, boolean isDeleted) {
        this.name = name;
        this.director = director;
        this.genres = genres;
        this.length = length;
        this.description = description;
        this.deleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Integer getLength() {
        return length;
    }

    public String getDescription() {
        return description;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", genres=" + genres +
                ", length=" + length +
                ", description='" + description + '\'' +
                ", isDeleted=" + deleted +
                '}';
    }
}
