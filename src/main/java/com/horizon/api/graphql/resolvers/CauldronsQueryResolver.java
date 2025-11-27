package com.horizon.api.graphql.resolvers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.horizon.api.dtos.CauldronGraphQLDTO;
import com.horizon.api.dtos.VCauldronDTO;
import com.horizon.api.services.VCauldronsService;

@Controller
public class CauldronsQueryResolver {
    
    private final VCauldronsService vCauldronsService;

    public CauldronsQueryResolver(VCauldronsService vCauldronsService) {
        this.vCauldronsService = vCauldronsService;
    }

    @QueryMapping
    public VCauldronDTO cauldron(
        @Argument Long id,
        @Argument String lang
    ) {
        return vCauldronsService.getCauldronById(id, lang);
    }

    @QueryMapping
    public CauldronGraphQLDTO cauldrons(
        @Argument String search,
        @Argument String lang
    ) {
        return vCauldronsService.getCauldronsGraphQL(search, lang);
    }
}
