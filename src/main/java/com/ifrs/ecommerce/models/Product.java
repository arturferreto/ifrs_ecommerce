package com.ifrs.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "products")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private String favoritePhotoUrl;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Transient
    private Iterable<Object> photos;

    @Transient
    private Iterable<Object> features;

    public Iterable<Object> getPhotos() {
        return photos;
    }

    public void setPhotos(Iterable<Object> photos) {
        this.photos = photos;
    }

    public Iterable<Object> getFeatures() {
        return features;
    }

    public void setFeatures(Iterable<Object> features) {
        this.features = features;
    }
}
