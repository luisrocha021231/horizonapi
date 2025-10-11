package com.horizon.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.api.dtos.VCauldronDTO;
import com.horizon.api.services.VCauldronsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/cauldrons")
@Tag(name = "Cauldrons", description = "Endpoints related to Cauldrons of the Horizon games universe.")
public class VCauldronController {
    
    @Autowired
    private VCauldronsService vCauldronsService;

    @Operation(
        summary = "Get cauldron by ID",
        description = "Retrieve detailed information about a specific cauldron from the Horizon games universe by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved cauldron details"),
        @ApiResponse(responseCode = "400", description = "Invalid cauldron ID supplied"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Cauldron not found")
    })
    @GetMapping("/{id}")
    public VCauldronDTO getCauldronById(
        @Parameter(description = "ID of the cauldron to retrieve, example: 1") @PathVariable Long id,
        @Parameter(description = "Language code for translation, e.g., 'en' for English, 'es' for Spanish.") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        return vCauldronsService.getCauldronById(id, lang);
    }

    @Operation(
        summary = "Get list of cauldrons",
        description = "Retrieve a list of cauldrons from the Horizon games universe, with optional search functionality."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cauldrons"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "No cauldrons found"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameter supplied")
    })
    @GetMapping
    public Map<String, Object> getCauldrons(
        @Parameter(description = "Search term for cauldrons by name.") @RequestParam(required = false) String search,
        @Parameter(description = "Language code for translation, e.g., 'en' for English, 'es' for Spanish.") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        return vCauldronsService.getCauldrons(search, lang);
    }
}
