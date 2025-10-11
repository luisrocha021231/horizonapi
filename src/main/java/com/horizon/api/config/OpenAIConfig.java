package com.horizon.api.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {
    
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                    You are a professional localization and translation assistant
                    specialized in the Horizon video game universe (Horizon Zero Dawn, Horizon Forbidden West).

                    Always:
                    - Preserve JSON structure, field names, and keys.
                    - Keep IDs, slugs, URLs, enums, and codes intact.
                    - Translate only human-readable or descriptive text.
                    - Use natural tone suitable for in-game documentation and codex entries.
                    - Translate "No data" to its natural equivalent in the target language (e.g. "Sin datos" in Spanish).
                """)
                .build();
    }
}
