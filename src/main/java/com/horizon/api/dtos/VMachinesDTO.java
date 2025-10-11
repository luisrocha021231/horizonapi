package com.horizon.api.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.api.entitites.VMachinesEntity;
import com.horizon.api.interfaces.TranslatableDTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a Machine.")
public class VMachinesDTO implements TranslatableDTO {
    
    @Schema(description = "Unique identifier of the machine.")
    private Long id;

    @Schema(description = "Slug identifier of the machine.")
    private String slug;
    
    @Schema(description = "Name of the machine.")
    private String name;

    @Schema(description = "Path to the machine's image.")
    private String path_image;

    @Schema(description = "Size of the machine.")
    private String size;

    @Schema(description = "Origin of the machine.")
    private String origin;

    @Schema(description = "Class of the machine.")
    private String machine_class;
    
    @Schema(description = "List of strengths of the machine.")
    private List<String> strength;

    @Schema(description = "List of weaknesses of the machine.")
    private List<String> weakness;

    @Schema(description = "List of explosive components of the machine.")
    private List<String> explosive_components;

    @Schema(description = "List of weak points of the machine.")
    private List<String> weak_points;

    @Schema(description = "List of appearances of the machine.")
    private List<String> appears_in;

    public VMachinesDTO(VMachinesEntity entity) {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.name = entity.getName();
        this.path_image = entity.getPathImage();
        this.size = entity.getSize();
        this.origin = entity.getOrigin();
        this.machine_class = entity.getClasss();
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.strength = mapper.readValue(entity.getStrength(), new TypeReference<>() {});
            this.weakness = mapper.readValue(entity.getWeakness(), new TypeReference<>() {});
            this.explosive_components = mapper.readValue(entity.getExplosiveComponents(), new TypeReference<>() {});
            this.weak_points = mapper.readValue(entity.getWeakPoints(), new TypeReference<>(){});
            this.appears_in = mapper.readValue(entity.getAppearsIn(), new TypeReference<>() {});

        } catch (Exception e) {
            this.strength = new ArrayList<>();
            this.weakness = new ArrayList<>();
            this.explosive_components = new ArrayList<>();
            this.weak_points = new ArrayList<>();
            this.appears_in = new ArrayList<>();
            
        }
    }

    @JsonIgnore
    @Override
    public String[] getExcludedFields() {
        return new String[]{"id", "slug", "appears_in", "path_image"};
    }
}
