package com.horizon.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.api.dtos.VAreasDTO;
import com.horizon.api.services.VAreasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/areas")
@Tag(name = "Areas", description = "Endpoints related to Areas of the Horizon games universe.")
public class VAreasController {
    
    @Autowired
    private VAreasService vAreasService;

    @Operation(
        summary = "Get area by ID",
        description = "Retrieve detailed information about a specific area from the Horizon games universe by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved area details"),
        @ApiResponse(responseCode = "400", description = "Invalid area ID supplied"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Area not found")
    })
    @GetMapping("/{id}")
    public VAreasDTO getAreaById(
        @Parameter(description = "Unique identifier of the area, example: 1") @PathVariable Long id,
        @Parameter(description = "Language code for translation, e.g., 'en' for English, 'es' for Spanish. Default is 'en'.") @RequestParam(defaultValue = "en", required = false) String lang)
    {
        return vAreasService.getAreaById(id, lang);
    }

    @Operation(
        summary = "Get list of areas",
        description = "Retrieve a list of areas from the Horizon games universe, with optional search functionality."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of areas"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "No areas found"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameter supplied")
    })
    @GetMapping
    public Map<String, Object> getAreas(
        @Parameter(description = "Search term for filtering areas by name.") @RequestParam(required = false) String search,
        @Parameter(description = "Language code for translation, e.g., 'en' for English, 'es' for Spanish. Default is 'en'.") @RequestParam(defaultValue = "en", required = false) String lang)
    {
        return vAreasService.getAreas(search, lang);
    }
}
