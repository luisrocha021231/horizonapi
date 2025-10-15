package com.horizon.api.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object representing all available API endpoints.")
public class AllEndpointsDTO {

    @Schema(description = "Endpoint for accessing character data.")
    private String characters;

    @Schema(description = "Endpoint for accessing weapon data.")
    private String machines;

    @Schema(description = "Endpoint for accessing cauldron data.")
    private String cauldrons;

    @Schema(description = "Endpoint for accessing region data.")
    private String regions;

    @Schema(description = "Endpoint for accessing area data.")
    private String areas;
    
}
