package com.horizon.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CdnUrlService {
    
    @Value("${cdn.base-url}")
    private String cdnBaseUrl;

    public String buildImageUrl(String relativePath) {
        if (relativePath == null || relativePath.equalsIgnoreCase("No data")) {
            return null;
        }
        // Evitar dobles barras
        if (relativePath.startsWith("/")) {
            return cdnBaseUrl + relativePath;
        } else {
            return cdnBaseUrl + "/" + relativePath;
        }
    }
}
