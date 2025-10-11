package com.horizon.api.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.api.entitites.VCauldronEntity;
import com.horizon.api.interfaces.TranslatableDTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a Cauldron.")
public class VCauldronDTO implements TranslatableDTO {
    
    @Schema(description = "Unique identifier of the cauldron.")
    private Long id;

    @Schema(description = "Slug identifier of the cauldron.")
    private String slug;

    @Schema(description = "Name of the cauldron.")
    private String name;

    @Schema(description = "Description of the cauldron.")
    private String description;

    @Schema(description = "Path to the cauldron's image.")
    private String path_image;

    @Schema(description = "Path to the map location image.")
    private String map_location_image;

    @Schema(description = "Region where the cauldron is located.")
    private String region;

    @Schema(description = "List of machines overridden by this cauldron.")
    private List<String> overridden_machines;

    @Schema(description = "List of locations where the cauldron appears.")
    private List<String> appears_in;

    public VCauldronDTO(VCauldronEntity entity) {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.path_image = "/images/cauldrons/"+entity.getPathImage();
        this.map_location_image = "/images/map-location/"+entity.getMapLocationImage();
        this.region = entity.getRegion();
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.overridden_machines = mapper.readValue(entity.getOverriddenMachines(), new TypeReference<>() {});
            this.appears_in = mapper.readValue(entity.getAppearsIn(), new TypeReference<>() {});
        } catch (Exception e) {
            this.overridden_machines = new ArrayList<>();
            this.appears_in = new ArrayList<>();
        }
        
    }

    @JsonIgnore
    @Override
    public String[] getExcludedFields() {
        return new String[] { "id", "slug", "path_image", "map_location_image", "appears_in" };
    }

}
