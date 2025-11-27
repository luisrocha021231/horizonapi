package com.horizon.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.horizon.api.dtos.CharacterPageGraphQLDTO;
import com.horizon.api.dtos.PaginationGraphQLDTO;
import com.horizon.api.dtos.VCharactersDTO;
import com.horizon.api.entitites.VCharactersEntity;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VCharactersRepository;

@Service
public class VCharactersGraphQLService {
    
    private final VCharactersRepository vCharactersRepository;
    private final TranslationService translationService;
    private final CdnUrlService cdnUrlService;

    private static final String PATH = "characters/";

    public VCharactersGraphQLService(
            VCharactersRepository vCharactersRepository,
            TranslationService translationService,
            CdnUrlService cdnUrlService) {
        this.vCharactersRepository = vCharactersRepository;
        this.translationService = translationService;
        this.cdnUrlService = cdnUrlService;
    }

    //OBTENER POR ID
    public VCharactersDTO getById(Long id, String lang) {
        VCharactersEntity entity = vCharactersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));

        VCharactersDTO dto = new VCharactersDTO(entity);
        dto.setImage_path(cdnUrlService.buildImageUrl(PATH + dto.getImage_path()));

        return translationService.translate(dto, lang);
    }

    //OBTENER CON PAGINACION + FILTROS
    public CharacterPageGraphQLDTO getCharacters(
        Integer page,
        Integer size,
        String search,
        String lang
    ) {
        int currentPage = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size >= 10 && size <= 50) ? size : 25;

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<VCharactersEntity> result;

        // FILTRADO 1: BUSQUEDA
        if(search != null && !search.trim().isEmpty()) {
            result = vCharactersRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        // SIN FILTROS
        else {
            result = vCharactersRepository.findAll(pageable);
        }

        // MAPEO DE LA ENTIDAD AL DTO + CONSTRUCCION DE URLS
        List<VCharactersDTO> characters = result.getContent()
                            .stream()
                            .map(VCharactersDTO::new)
                            .peek(dto -> 
                                    dto.setImage_path(
                                        cdnUrlService.buildImageUrl(PATH + dto.getImage_path())
                                    )
                            )
                            .toList();


        // TRADUCCION
        characters = translationService.translateList(characters, lang);

        // PAGINACION DTO
        PaginationGraphQLDTO pagination = new PaginationGraphQLDTO(
            pageSize,
            currentPage,
            result.getTotalPages(),
            result.hasNext() ? String.valueOf(currentPage + 1) : null,
            result.hasPrevious() ? String.valueOf(currentPage - 1) : null
        );

        return new CharacterPageGraphQLDTO(pagination, characters);   
    }
}
