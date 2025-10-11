package com.horizon.api.entitites;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "v_characters", schema = "horizonapi")
public class VCharactersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "culture")
    private String culture;

    @Column(name = "gender")
    private String gender;

    @Column(name = "profession")
    private String profession;

    @Column(name = "status")
    private String status;

    @Column(name = "eyes_color")
    private String eyesColor;

    @Column(name = "hair_color")
    private String hairColor;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "death_date")
    private String deathDate;

    @Column(name = "appearances", columnDefinition = "json")
    private String appearances;

}
