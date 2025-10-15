package com.horizon.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horizon.api.dtos.AllEndpointsDTO;
import com.horizon.api.services.AllEndpointsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "All Endpoints", description = "Endpoint to retrieve all available API endpoints.")
public class AllEndpointsController {
    
    @Autowired
    private AllEndpointsService allEndpointsService;

    @Operation(
        summary = "Get all API endpoints",
        description = "Retrieve a comprehensive list of all available API endpoints in the Horizon games universe."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all API endpoints"),
    })
    @GetMapping
    public AllEndpointsDTO getAllEndpoints() {
        return allEndpointsService.getAllEndpoints();
    }
}
