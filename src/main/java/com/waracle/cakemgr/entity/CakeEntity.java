package com.waracle.cakemgr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cake")
public class CakeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false, length = 250)
    private String title;
    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageURL;
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
}
