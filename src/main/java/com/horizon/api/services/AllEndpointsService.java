package com.horizon.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.horizon.api.dtos.AllEndpointsDTO;

@Service
public class AllEndpointsService {

    @Value("${app.url}")
    private String appUrl;
    
    public AllEndpointsDTO getAllEndpoints() {
        return AllEndpointsDTO.builder()
                .characters(appUrl + "/api/characters")
                .machines(appUrl + "/api/machines")
                .cauldrons(appUrl + "/api/cauldrons")
                .regions(appUrl + "/api/regions")
                .areas(appUrl + "/api/areas")
                .build();
    }
}
