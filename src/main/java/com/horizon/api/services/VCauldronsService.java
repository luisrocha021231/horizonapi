package com.horizon.api.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.horizon.api.dtos.VCauldronDTO;
import com.horizon.api.entitites.VCauldronEntity;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VCauldronsRepository;

@Service
public class VCauldronsService {
    
    @Autowired
    private VCauldronsRepository vCauldronsRepository;

    @Autowired
    private TranslationService translationService;

    public VCauldronDTO getCauldronById(Long id, String lang) {
        VCauldronEntity cauldron = vCauldronsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cauldron not found with id: " + id));
        VCauldronDTO dto = new VCauldronDTO(cauldron);
        return translationService.translate(dto, lang);
    }

    public Map<String, Object> getCauldrons(String search, String lang){
        Map<String, Object> response = new HashMap<>();

        if(search != null && !search.trim().isEmpty()) {
            List<VCauldronDTO> cauldrons = vCauldronsRepository.findByNameContainingIgnoreCase(search)
                                                         .stream()
                                                         .map(VCauldronDTO::new)
                                                         .toList();

            List<VCauldronDTO> translatedCauldrons = translationService.translateList(cauldrons, lang);
            response.put("cauldrons", translatedCauldrons);
        } else {
            List<VCauldronDTO> cauldrons = vCauldronsRepository.findAll()
                                                         .stream()
                                                         .map(VCauldronDTO::new)
                                                         .toList();
            List<VCauldronDTO> translatedCauldrons = translationService.translateList(cauldrons, lang);                                             
            response.put("cauldrons", translatedCauldrons);
        }
        return response;
    }

}
