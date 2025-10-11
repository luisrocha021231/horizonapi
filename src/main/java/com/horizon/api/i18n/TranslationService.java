package com.horizon.api.i18n;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.api.interfaces.TranslatableDTO;
import com.horizon.api.templates.PromptLibrary;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class TranslationService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public TranslationService(ChatClient chatClient, ObjectMapper objectMapper, RedisTemplate<String, String> redisTemplate) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    // Traduccion de un solo DTO
    public <T extends TranslatableDTO> T translate(T dto, String targetLanguage) {
        if (dto == null || targetLanguage == null || targetLanguage.equalsIgnoreCase("en")) {
            return dto;
        }

        try {
            // Clave semántica para cache
            String cacheKey = buildCacheKey(dto, targetLanguage);

            // Verificacion en redis para saber si ya existe la traducción
            String cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) {
                return objectMapper.readValue(cached, (Class<T>) dto.getClass());
            }

            // Convertir DTO a JSON completo
            String jsonInput = objectMapper.writeValueAsString(dto);

            // Crear el prompt con el template Horizon
            Prompt prompt = PromptLibrary.TRANSLATION_PROMPT_TEMPLATE.create(Map.of(
                    "targetLang", targetLanguage,
                    "json", jsonInput
            ));

            // Llamada al modelo
            String jsonOutput = chatClient
                    .prompt(prompt)
                    .call()
                    .content()
                    .trim();

            // Convencion de la respuesta en DTO traducido
            T translated = objectMapper.readValue(jsonOutput, (Class<T>) dto.getClass());

            // Guardado temporal en Redis (TTL de 12 horas)
            redisTemplate.opsForValue().set(cacheKey, jsonOutput, Duration.ofHours(12));

            return translated;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error translating DTO: " + e.getMessage(), e);
        }
    }


    // Traduccion de una lista de DTOs.
    public <T extends TranslatableDTO> List<T> translateList(List<T> list, String targetLanguage) {
        if (targetLanguage == null || targetLanguage.equalsIgnoreCase("en")) {
            return list;
        }

        return list.stream()
                .map(dto -> translate(dto, targetLanguage))
                .toList();
    }

    private String buildCacheKey(TranslatableDTO dto, String lang) {
        String type = dto.getClass().getSimpleName().toLowerCase();
        String identifier = getFieldValue(dto, "slug");

        if (identifier == null) {
            identifier = getFieldValue(dto, "id");
        }
        if (identifier == null) {
            identifier = String.valueOf(dto.hashCode());
        }

        return String.format("translation:%s:%s:%s", type, identifier, lang);
    }

    // Intenta obtener el valor de un campo específico del DTO
    private String getFieldValue(TranslatableDTO dto, String fieldName) {
        try {
            Field field = dto.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(dto);
            return value != null ? value.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
