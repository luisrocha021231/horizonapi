package com.horizon.api.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.api.entitites.VAreasEntity;
import com.horizon.api.interfaces.TranslatableDTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing an area in the Horizon games universe.")
public class VAreasDTO implements TranslatableDTO {

    @Schema(description = "Unique identifier of the area.")
    private Long id;

    @Schema(description = "Slug identifier of the area.")
    private String slug;

    @Schema(description = "Name of the area.")
    private String name;

    @Schema(description = "Description of the area.")
    private String description;

    @Schema(description = "Type of the area.")
    private String type;

    @Schema(description = "Faction associated with the area.")
    private String faction;

    @Schema(description = "Image path of the area.")
    private String image_path;

    @Schema(description = "List of regions within the area.")
    private List<String> regions;

    @Schema(description = "List of media in which the area appears.")
    private List<String> appears_in;

    public VAreasDTO(VAreasEntity entity) {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.name = entity.getName();
        this.image_path = "/images/areas/"+entity.getImagePath();
        this.description = entity.getDescription();
        this.type = entity.getType();
        this.faction = entity.getFaction();
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.regions = mapper.readValue(entity.getRegions(), new TypeReference<>() {});
            this.appears_in = mapper.readValue(entity.getAppearsIn(), new TypeReference<>() {});
        } catch (Exception e) {
            this.regions = new ArrayList<>();
            this.appears_in = new ArrayList<>();
        }
    }

    @JsonIgnore
    @Override
    public String[] getExcludedFields() {
        return new String[] { "id", "slug", "image_path", "appears_in" };
    }
}
