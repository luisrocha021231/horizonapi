package com.horizon.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.api.dtos.VMachinesDTO;
import com.horizon.api.services.VMachinesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/machines")
@Tag(name = "Machines", description = "Endpoints related to Machines of the Horizon games universe.")
public class VMachinesController {
    
    @Autowired
    private VMachinesService vMachinesService;

    @Operation(
        summary = "Get a paginated list of machines",
        description = "Retrieve a paginated list of machines from the Horizon games universe. Optional parameters for page number, page size, and search query can be provided."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of machines"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Machines not found")
    })
    @GetMapping("/{id}")
    public VMachinesDTO getMachineById(
        @Parameter(description = "ID of the machine to retrieve") @PathVariable Long id,
        @Parameter(description = "Language code for translation (e.g., 'en', 'es', 'fr')") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        return vMachinesService.getMachineById(id, lang);
    }

    @Operation(
        summary = "Get a paginated list of machines",
        description = "Retrieve a paginated list of machines from the Horizon games universe. Optional parameters for page number, page size, and search query can be provided."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of machines"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "404", description = "Machines not found")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getMachines(
        @Parameter(description = "Page number for pagination default 0.") @RequestParam(required = false) Integer page, 
        @Parameter(description = "Page size for pagination default 25, minimum 25, maximum 50.") @RequestParam(required = false) Integer size,
        @Parameter(description = "Search query for filtering machines") @RequestParam(required = false) String search,
        @Parameter(description = "Language code for translation (e.g., 'en', 'es', 'fr')") @RequestParam(required = false, defaultValue = "en") String lang)
    {
        Map<String, Object> response = vMachinesService.getMachines(page, size, search, lang);
        return ResponseEntity.ok(response);
    }
}
