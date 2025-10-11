package com.horizon.api.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.horizon.api.dtos.VCharactersDTO;
import com.horizon.api.entitites.VCharactersEntity;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VCharactersRepository;

@Service
public class VCharactersService {
    
    @Autowired
    private VCharactersRepository vCharactersRepository;

    @Autowired
    private TranslationService translationService;

    // OBTENER UN PERSONAJE POR ID
    public VCharactersDTO getCharacterById(Long id, String lang) {
        VCharactersEntity character = vCharactersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found with id: " + id));
        
        VCharactersDTO dto = new VCharactersDTO(character);
        return translationService.translate(dto, lang);
    }

    // OBTENER TODOS LOS PERSONAJES CON PAGINACION Y BUSQUEDA PARCIAL
    public Map<String, Object> getCharacters(Integer page, Integer size, String search, String lang) {
    Map<String, Object> response = new HashMap<>();

    // 1 CONFIGURACION DE BUSQUEDA PARCIAL, IGNORANDO LA PAGINACION A EXCEPCION DE QUE SE SOBREPASEN 25 RESULTADOS
    if (search != null && !search.trim().isEmpty()) {
        int currentPage = (page != null && page >= 0) ? page : 0;
        int pageSize;

        if (size == null) {
            pageSize = 25;
        } else if (size < 25 || size > 50) {
            pageSize = 25;
        } else {
            pageSize = size;
        }

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<VCharactersEntity> searchPage = vCharactersRepository.findByNameContainingIgnoreCase(search, pageable);
        Page<VCharactersDTO> dtoPage = searchPage.map(VCharactersDTO::new);

        List<VCharactersDTO> translatedCharacters = translationService.translateList(dtoPage.getContent(), lang);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("pageSize", pageSize);
        pagination.put("currentPage", currentPage);
        pagination.put("totalPages", dtoPage.getTotalPages());
        pagination.put("nextPage", dtoPage.hasNext()
                ? "/api/characters?search=" + search + "&page=" + (currentPage + 1) : null);
        pagination.put("previousPage", dtoPage.hasPrevious()
                ? "/api/characters?search=" + search + "&page=" + (currentPage - 1) : null);

        response.put("pagination", pagination);
        response.put("characters", translatedCharacters);
        return response;
    }

    // 2 SI NO SE ESPECIFICA BUSQUEDA NI PAGINACION DEVUELVE TODOS LOS RESULTADOS
    if (page == null && size == null) {
        List<VCharactersDTO> allCharacters = vCharactersRepository.findAll()
                                                                  .stream()
                                                                  .map(VCharactersDTO::new)
                                                                  .toList();

        List<VCharactersDTO> translatedCharacters = translationService.translateList(allCharacters, lang);
        response.put("characters", translatedCharacters);
        return response;
    }

    // 3 SI SE ESPECIFICA PAGINACION GENERAL
    int currentPage = (page != null && page >= 0) ? page : 0;
    int pageSize;

    if (size == null) {
        pageSize = 25;
    } else if (size < 25 || size > 50) {
        pageSize = 25;
    } else {
        pageSize = size;
    }

    Pageable pageable = PageRequest.of(currentPage, pageSize);
    Page<VCharactersEntity> charactersPage = vCharactersRepository.findAll(pageable);
    Page<VCharactersDTO> dtoPage = charactersPage.map(VCharactersDTO::new);

    List<VCharactersDTO> translatedCharacters = translationService.translateList(dtoPage.getContent(), lang);

    // BLOQUE DE PAGINACION GENERAL
    Map<String, Object> pagination = new HashMap<>();
    pagination.put("pageSize", pageSize);
    pagination.put("currentPage", currentPage);
    pagination.put("totalPages", dtoPage.getTotalPages());
    pagination.put("nextPage", dtoPage.hasNext()
            ? "/api/characters?page=" + (currentPage + 1) + "&size=" + pageSize
            : null);
    pagination.put("previousPage", dtoPage.hasPrevious()
            ? "/api/characters?page=" + (currentPage - 1) + "&size=" + pageSize
            : null);

    response.put("pagination", pagination);
    response.put("characters", translatedCharacters);

    return response;
}

}
