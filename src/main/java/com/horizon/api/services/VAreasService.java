package com.horizon.api.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.horizon.api.dtos.VAreasDTO;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VAreasRepository;

@Service
public class VAreasService {
   
    @Autowired
    private VAreasRepository vAreasRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private CdnUrlService cdnUrlService;

    String path = "area/";

    // OBTENER UN AREA POR ID
    public VAreasDTO getAreaById(Long id, String lang) {
        VAreasDTO area = vAreasRepository.findById(id)
                .map(VAreasDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Area not found with id: " + id));

            area.setImage_path(cdnUrlService.buildImageUrl(path + area.getImage_path()));
        return translationService.translate(area, lang);
    }

    public Map<String, Object> getAreas(String search, String lang) {
        Map<String, Object> response = new HashMap<>();

        List<VAreasDTO> areas;

        if(search != null && !search.trim().isEmpty()) {
            areas = vAreasRepository.findByNameContainingIgnoreCase(search)
                    .stream()
                    .map(VAreasDTO::new)
                    .toList();
        } else {
            areas = vAreasRepository.findAll()
                    .stream()
                    .map(VAreasDTO::new)
                    .toList();
        }
        areas.forEach(dto -> dto.setImage_path(cdnUrlService.buildImageUrl(path + dto.getImage_path())));
        response.put("areas", translationService.translateList(areas, lang));
        return response;
    }
}
