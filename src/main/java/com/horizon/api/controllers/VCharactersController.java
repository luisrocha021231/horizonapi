package com.horizon.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.api.dtos.VCharactersDTO;
import com.horizon.api.services.VCharactersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/characters")
@Tag(name = "Characters", description = "Endpoints related to Characters of the Horizon games universe.")
public class VCharactersController {
    
    @Autowired
    private VCharactersService vCharactersService;

    @Operation(
        summary = "Get a paginated list of characters",
        description = "Retrieve a paginated list of characters from the Horizon games universe. Optional parameters for page number, page size, and search query can be provided."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of characters"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Characters not found")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCharacters(
            @Parameter(description = "Page number for pagination (default: 0).") @RequestParam(required = false) Integer page,
            @Parameter(description = "Page size for pagination (default: 25), minimum: 25, maximum: 50.") @RequestParam(required = false) Integer size,
            @Parameter(description = "Search query for filtering characters by name.") @RequestParam(required = false) String search,
            @Parameter(description = "Language code for translation, e.g., 'en' for English, 'es' for Spanish.") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        Map<String, Object> response = vCharactersService.getCharacters(page, size, search, lang);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get character by ID",
        description = "Retrieve detailed information about a specific character from the Horizon games universe by their unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved character details"),
        @ApiResponse(responseCode = "400", description = "Invalid character ID supplied"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Character not found")
    })
    @GetMapping("/{id}")
    public VCharactersDTO getCharacterById(
        @Parameter(description = "ID of the character to retrieve, example: 1") @PathVariable Long id,
        @Parameter(description = "Language code for translation, e.g., 'en' for English, 'es' for Spanish.") @RequestParam(required = false, defaultValue = "en") String lang) 
    {
        return vCharactersService.getCharacterById(id, lang);
    }

}
