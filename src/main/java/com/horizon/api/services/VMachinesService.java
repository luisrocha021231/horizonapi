package com.horizon.api.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.horizon.api.dtos.VMachinesDTO;
import com.horizon.api.entitites.VMachinesEntity;
import com.horizon.api.i18n.TranslationService;
import com.horizon.api.repositories.VMachinesRepository;

@Service
public class VMachinesService {
    
    @Autowired
    private VMachinesRepository vMachinesRepository;

    @Autowired
    private TranslationService translationService;

    public VMachinesDTO getMachineById(Long id, String lang) {
        VMachinesEntity entity = vMachinesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Machine not found with id: " + id));
        VMachinesDTO dto = new VMachinesDTO(entity);
        return translationService.translate(dto, lang);
    }

    public Map<String, Object> getMachines(Integer page, Integer size, String search, String lang) {
        Map<String, Object> response = new HashMap<>();

        if(search != null && !search.trim().isEmpty()) {
            int currentPage = (page != null && page >= 0) ? page : 0;
            int pageSize;

            if(size == null) {
                pageSize = 25;
            } else if(size < 25 || size > 50) {
                pageSize = 25;
            } else {
                pageSize = size;
            }

            Pageable pageable = PageRequest.of(currentPage, pageSize);
            Page<VMachinesEntity> searchPage = vMachinesRepository.findByNameContainingIgnoreCase(search, pageable);
            Page<VMachinesDTO> dtoPage = searchPage.map(VMachinesDTO::new);

            List<VMachinesDTO> translatedMachines = translationService.translateList(dtoPage.getContent(), lang);

            Map<String, Object> pagination = new HashMap<>();
            pagination.put("pageSize", pageSize);
            pagination.put("currentPage", currentPage);
            pagination.put("totalPages", dtoPage.getTotalPages());
            pagination.put("nextPage", dtoPage.hasNext() 
                            ? "/api/machines?search=" + search + "&page=" + (currentPage + 1) : null);
            pagination.put("previousPage", dtoPage.hasPrevious()
                            ? "/api/machines?search=" + search + "&page=" + (currentPage - 1) : null);
            
            response.put("pagination", pagination);
            response.put("machines", translatedMachines);
            return response;
        }

        if(page == null && size == null) {
            List<VMachinesDTO> allMachines = vMachinesRepository.findAll()
                                                                .stream()
                                                                .map(VMachinesDTO::new)
                                                                .toList();

            List<VMachinesDTO> translatedMachines = translationService.translateList(allMachines, lang);

            response.put("machines", translatedMachines);
            return response;
        }

        int currentPage = (page != null && page >= 0) ? page : 0;
        int pageSize;

        if(size == null) {
            pageSize = 25;
        } else if(size < 25 || size > 50) {
            pageSize = 25;
        } else {
            pageSize = size;
        }

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<VMachinesEntity> machinesPage = vMachinesRepository.findAll(pageable);
        Page<VMachinesDTO> dtoPage = machinesPage.map(VMachinesDTO::new);

        List<VMachinesDTO> translatedMachines = translationService.translateList(dtoPage.getContent(), lang);

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("pageSize", pageSize);
        pagination.put("currentPage", currentPage);
        pagination.put("totalPages", dtoPage.getTotalPages());
        pagination.put("nextPage", dtoPage.hasNext()
                        ? "/api/machines?page=" + (currentPage + 1) + "&size=" + pageSize : null);
        pagination.put("previousPage", dtoPage.hasPrevious()
                        ? "/api/machines?page=" + (currentPage - 1) + "&size=" + pageSize : null);

        response.put("pagination", pagination);
        response.put("machines", translatedMachines);

        return response;
    }
}
