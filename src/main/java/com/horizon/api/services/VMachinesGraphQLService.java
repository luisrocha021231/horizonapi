package com.horizon.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.horizon.api.dtos.MachinePageGraphQLDTO;
import com.horizon.api.dtos.PaginationGraphQLDTO;
import com.horizon.api.dtos.VMachinesDTO;
import com.horizon.api.entitites.VMachinesEntity;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VMachinesRepository;

@Service
public class VMachinesGraphQLService {
    
    private final VMachinesRepository vMachinesRepository;
    private final TranslationService translationService;
    private final CdnUrlService cdnUrlService;

    private static final String PATH = "machines/";

    public VMachinesGraphQLService(
            VMachinesRepository vMachinesRepository,
            TranslationService translationService,
            CdnUrlService cdnUrlService) {
        this.vMachinesRepository = vMachinesRepository;
        this.translationService = translationService;
        this.cdnUrlService = cdnUrlService;
    }

    //OBTENER POR ID
    public VMachinesDTO getById(Long id, String lang) {
        VMachinesEntity entity = vMachinesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Machine not found with id: " + id));
    
        VMachinesDTO dto = new VMachinesDTO(entity);
        dto.setPath_image(cdnUrlService.buildImageUrl(PATH + dto.getPath_image()));

        return translationService.translate(dto, lang);
    }

    public MachinePageGraphQLDTO getMachines(
        Integer page,
        Integer size,
        String search,
        String lang
    ) {
        int currentPage = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size >= 10 && size <= 50) ? size : 25;

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<VMachinesEntity> result;
        
        if(search != null && !search.trim().isEmpty()) {
            result = vMachinesRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        else {
            result = vMachinesRepository.findAll(pageable);
        }

        List<VMachinesDTO> machines = result.getContent()
            .stream()
            .map(VMachinesDTO::new)
            .peek(dto -> 
                dto.setPath_image(
                    cdnUrlService.buildImageUrl(PATH + dto.getPath_image())
                )
            )
            .toList();
        
        machines = translationService.translateList(machines, lang);

        PaginationGraphQLDTO pagination = new PaginationGraphQLDTO(
            pageSize,
            currentPage,
            result.getTotalPages(),
            result.hasNext() ? String.valueOf(currentPage + 1) : null,
            result.hasPrevious() ? String.valueOf(currentPage - 1) : null
        );

        return new MachinePageGraphQLDTO(pagination, machines);
    }
}
