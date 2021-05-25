package com.raminq.jpa_hibernate.entity;

import javax.persistence.*;

@Entity

public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReviewRating rating;

    private String description;

    @ManyToOne
    // @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "COURSE_ID_FK"))
    private Course course;


    public Review() {
    }

    public Review(ReviewRating rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public void setRating(ReviewRating rating) {
        this.rating = rating;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "\nReview{" +
                "id=" + id +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
