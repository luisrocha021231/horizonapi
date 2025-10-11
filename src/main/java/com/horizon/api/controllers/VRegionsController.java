package com.horizon.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.api.dtos.VRegionsDTO;
import com.horizon.api.services.VRegionsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/regions")
@Tag(name = "Regions", description = "Endpoints for managing regions of the Horizon games universe.")
public class VRegionsController {
    
    @Autowired
    private VRegionsService vRegionsService;

    @Operation(
        summary = "Get region by ID",
        description = "Retrieve detailed information about a specific region from the Horizon games universe by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved region details"),
        @ApiResponse(responseCode = "400", description = "Invalid region ID supplied"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Region not found")
    })
    @GetMapping("/{id}")
    public VRegionsDTO getRegionById(
        @Parameter(description = "ID of the region to retrieve.") @PathVariable Long id,
        @Parameter(description = "Language code for localization (e.g., 'en', 'es').") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        return vRegionsService.getVRegions(id, lang);
    }

    @Operation(
        summary = "Get list of regions",
        description = "Retrieve a list of regions from the Horizon games universe, with optional search functionality."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of regions"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "No regions found"),
        @ApiResponse(responseCode = "400", description = "Invalid search parameter supplied")
    })
    @GetMapping
    public Map<String, Object> getRegions(
        @Parameter(description = "Search term for filtering regions") @RequestParam(required = false) String search,
        @Parameter(description = "Language code for localization (e.g., 'en', 'es').") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        return vRegionsService.getRegions(search, lang);
    }

}
