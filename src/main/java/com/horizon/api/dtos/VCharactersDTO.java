package com.horizon.api.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.api.entitites.VCharactersEntity;
import com.horizon.api.interfaces.TranslatableDTO;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
@Schema(description = "Data Transfer Object representing a character in the system.")
public class VCharactersDTO implements TranslatableDTO {

    @Schema(description = "Unique identifier of the character.")
    private Long id;

    @Schema(description = "Slug of the character.")
    private String slug;

    @Schema(description = "Name of the character.")
    private String name;

    @Schema(description = "Image path of the character.")
    private String image_path;

    @Schema(description = "Culture of the character.")
    private String culture;

    @Schema(description = "Gender of the character.")
    private String gender;

    @Schema(description = "Profession of the character.")
    private String profession;

    @Schema(description = "Status of the character.")
    private String status;

    @Schema(description = "Eye color of the character.")
    private String eyesColor;

    @Schema(description = "Hair color of the character.")
    private String hairColor;

    @Schema(description = "Birth date of the character.")
    private String birth_date;

    @Schema(description = "Death date of the character.")
    private String death_date;

    @Schema(description = "List of appearances of the character.")
    private List<String> appears_in;

    public VCharactersDTO(VCharactersEntity entity) {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.name = entity.getName();
        this.image_path = entity.getImagePath();
        this.culture = entity.getCulture();
        this.gender = entity.getGender();
        this.profession = entity.getProfession();
        this.status = entity.getStatus();
        this.eyesColor = entity.getEyesColor();
        this.hairColor = entity.getHairColor();
        this.birth_date = entity.getBirthDate();
        this.death_date = entity.getDeathDate();
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.appears_in = mapper.readValue(entity.getAppearances(), new TypeReference<>() {});
        } catch (Exception e) {
            this.appears_in = new ArrayList<>();
        }
    }

    @JsonIgnore
    @Override
    public String[] getExcludedFields() {
        return new String[]{"id", "slug", "image_path", "appears_in"};
    }
}
