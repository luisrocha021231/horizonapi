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
@Table(name = "v_areas", schema = "horizonapi")
public class VAreasEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "faction")
    private String faction;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "regions", columnDefinition = "json")
    private String regions;

    @Column(name = "appears_in", columnDefinition = "json")
    private String appearsIn;
}
