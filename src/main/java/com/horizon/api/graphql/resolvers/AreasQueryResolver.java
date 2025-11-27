package com.horizon.api.graphql.resolvers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.horizon.api.dtos.AreaGraphQLDTO;
import com.horizon.api.dtos.VAreasDTO;
import com.horizon.api.services.VAreasService;

@Controller
public class AreasQueryResolver {
    
    private final VAreasService vAreasService;

    public AreasQueryResolver(VAreasService vAreasService) {
        this.vAreasService = vAreasService;
    }

    @QueryMapping
    public VAreasDTO area(
        @Argument Long id,
        @Argument String lang
    ) {
        return vAreasService.getAreaById(id, lang);
    }

    @QueryMapping
    public AreaGraphQLDTO areas(
        @Argument String search,
        @Argument String lang
    ) {
        return vAreasService.getAreasGraphQL(search, lang);
    }
}
