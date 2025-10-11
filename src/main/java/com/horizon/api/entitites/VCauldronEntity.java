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
@Table(name = "v_cauldron", schema = "horizonapi")
public class VCauldronEntity implements Serializable {
 
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

    @Column(name = "path_image")
    private String pathImage;

    @Column(name = "map_location_image")
    private String mapLocationImage;

    @Column(name = "region")
    private String region;

    @Column(name = "overridden_machines", columnDefinition = "json")
    private String overriddenMachines;

    @Column(name = "appears_in", columnDefinition = "json")
    private String appearsIn;
}
