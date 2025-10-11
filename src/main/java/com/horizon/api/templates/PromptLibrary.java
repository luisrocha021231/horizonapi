package com.horizon.api.templates;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;

public class PromptLibrary {

    public static final PromptTemplate TRANSLATION_PROMPT_TEMPLATE =
            new PromptTemplate(new ClassPathResource("prompts/translation.st"));
}
