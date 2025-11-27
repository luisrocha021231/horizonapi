package com.horizon.api.graphql.resolvers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.horizon.api.dtos.CharacterPageGraphQLDTO;
import com.horizon.api.dtos.VCharactersDTO;
import com.horizon.api.services.VCharactersGraphQLService;

@Controller
public class CharacterQueryResolver {
    
    private final VCharactersGraphQLService vCharactersGraphQLService;

    public CharacterQueryResolver(VCharactersGraphQLService vCharactersGraphQLService) {
        this.vCharactersGraphQLService = vCharactersGraphQLService;
    }

    @QueryMapping
    public VCharactersDTO character(
        @Argument Long id, 
        @Argument String lang) {
        return vCharactersGraphQLService.getById(id, lang);
    }

    @QueryMapping
    public CharacterPageGraphQLDTO characters(
            @Argument Integer page,
            @Argument Integer size,
            @Argument String search,
            @Argument String lang
    ) {
        return vCharactersGraphQLService.getCharacters(page, size, search, lang);
    }
}
