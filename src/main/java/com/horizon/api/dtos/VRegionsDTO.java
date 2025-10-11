package com.horizon.api.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.api.entitites.VRegionsEntity;
import com.horizon.api.interfaces.TranslatableDTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a Region.")
public class VRegionsDTO implements TranslatableDTO {
    
    private Long id;
    private String slug;
    private String name;
    private String description;
    private String image_path;
    private List<String> factions;
    private String area;
    private List<String> appears_in;

    public VRegionsDTO(VRegionsEntity entity) {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.image_path = "/images/regions/" + entity.getImagePath();
        this.area = entity.getArea();
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.factions = mapper.readValue(entity.getFactions(), new TypeReference<>() {});
            this.appears_in = mapper.readValue(entity.getAppearsIn(), new TypeReference<>() {});

        } catch (Exception e) {
            this.factions = new ArrayList<>();
            this.appears_in = new ArrayList<>();
        }
    }

    @JsonIgnore
    @Override
    public String[] getExcludedFields() {
        return new String[]{"id" , "slug", "image_path", "factions", "appears_in"};
    }
}
