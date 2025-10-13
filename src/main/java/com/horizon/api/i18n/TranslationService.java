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

    // Traducción de un solo DTO
    public <T extends TranslatableDTO> T translate(T dto, String targetLanguage) {
        if (dto == null || targetLanguage == null || targetLanguage.equalsIgnoreCase("en")) {
            return dto;
        }

        String cacheKey = buildCacheKey(dto, targetLanguage);
        String cached = null;

        try {
            // Se intenta leer Redis, si falla, se continua sin cache
            cached = redisTemplate.opsForValue().get(cacheKey);
        } catch (Exception ex) {
            System.err.println("[WARN] Redis unavailable, skipping cache read: " + ex.getMessage());
        }

        try {
            if (cached != null) {
                return objectMapper.readValue(cached, (Class<T>) dto.getClass());
            }

            // Conversion de un DTO a JSON 
            String jsonInput = objectMapper.writeValueAsString(dto);

            // Creacion del prompt
            Prompt prompt = PromptLibrary.TRANSLATION_PROMPT_TEMPLATE.create(Map.of(
                    "targetLang", targetLanguage,
                    "json", jsonInput
            ));

            // Llamada al modelo
            String jsonOutput = chatClient.prompt(prompt).call().content().trim();

            // Conversion de la respuesta al DTO traducido
            T translated = objectMapper.readValue(jsonOutput, (Class<T>) dto.getClass());

            // Se intenta escribir en Redis, si falla, se continua sin cache
            try {
                redisTemplate.opsForValue().set(cacheKey, jsonOutput, Duration.ofHours(12));
            } catch (Exception ex) {
                System.err.println("[WARN] Redis unavailable, skipping cache write: " + ex.getMessage());
            }

            return translated;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error translating DTO (OpenAI): " + e.getMessage(),
                    e
            );
        }
    }

    // Traducción de lista
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

