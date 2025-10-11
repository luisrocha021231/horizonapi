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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Immutable
@Table(name = "v_machines", schema = "horizonapi")
public class VMachinesEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "name")
    private String name;

    @Column(name = "path_image")
    private String pathImage;

    @Column(name = "size")
    private String size;

    @Column(name = "origin")
    private String origin;

    @Column(name = "class")
    private String classs;  

    @Column(name = "strength", columnDefinition = "json")
    private String strength;

    @Column(name = "weakness", columnDefinition = "json")
    private String weakness;

    @Column(name = "explosive_components", columnDefinition = "json")
    private String explosiveComponents;

    @Column(name = "weak_points", columnDefinition = "json")
    private String weakPoints;

    @Column(name = "appears_in", columnDefinition = "json")
    private String appearsIn;

}
