package com.horizon.api.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.horizon.api.dtos.RegionGraphQLDTO;
import com.horizon.api.dtos.VRegionsDTO;
import com.horizon.api.entitites.VRegionsEntity;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VRegionsRepository;

@Service
public class VRegionsService {
    
    @Autowired
    private VRegionsRepository vRegionsRepository;

    @Autowired
    public TranslationService translationService;

    @Autowired
    private CdnUrlService cdnUrlService;

    String path = "regions/";

    public VRegionsDTO getVRegions(Long id, String lang) {
        VRegionsEntity region = vRegionsRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found with id: " + id));

        VRegionsDTO regiondto = new VRegionsDTO(region);
        regiondto.setImage_path(cdnUrlService.buildImageUrl(path + region.getImagePath()));
        return translationService.translate(regiondto, lang);

    }
    public Map<String, Object> getRegions(String search, String lang) {
        Map<String, Object> response = new HashMap<>();

        if(search != null && !search.trim().isEmpty()) {
            List<VRegionsDTO> regions = vRegionsRepository.findByNameContainingIgnoreCase(search)
                                                        .stream()
                                                        .map(VRegionsDTO::new)
                                                        .toList();

            List<VRegionsDTO> translatedRegions = translationService.translateList(regions, lang);
            translatedRegions.forEach(dto -> dto.setImage_path(cdnUrlService.buildImageUrl(path + dto.getImage_path())));
            response.put("regions", translatedRegions);
        } else {
            List<VRegionsDTO> regions = vRegionsRepository.findAll()
                                                        .stream()
                                                        .map(VRegionsDTO::new)
                                                        .toList();

            List<VRegionsDTO> translatedRegions = translationService.translateList(regions, lang);
            translatedRegions.forEach(dto -> dto.setImage_path(cdnUrlService.buildImageUrl(path + dto.getImage_path())));
            response.put("regions", translatedRegions);
        }
        return response;
    }

    public RegionGraphQLDTO getRegionsGraphQL(
        String search,
        String lang
    ) {

        List<VRegionsEntity> result;

        if(search != null && !search.trim().isEmpty()) {
            result = vRegionsRepository.findByNameContainingIgnoreCase(search);
        }
        else {
            result = vRegionsRepository.findAll();
        }

        List<VRegionsDTO> regions = result
            .stream()
            .map(VRegionsDTO::new)
            .peek(dto -> 
                dto.setImage_path(
                    cdnUrlService.buildImageUrl(path + dto.getImage_path())
                )
            )
            .toList();
        
        regions = translationService.translateList(regions, lang);

        return new RegionGraphQLDTO(regions);
    }
}
