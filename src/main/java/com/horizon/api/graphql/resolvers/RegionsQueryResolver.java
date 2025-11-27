package com.horizon.api.graphql.resolvers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.horizon.api.dtos.RegionGraphQLDTO;
import com.horizon.api.dtos.VRegionsDTO;
import com.horizon.api.services.VRegionsService;

@Controller
public class RegionsQueryResolver {
    
    private final VRegionsService vRegionsService;

    public RegionsQueryResolver(VRegionsService vRegionsService) {
        this.vRegionsService = vRegionsService;
    }

    @QueryMapping
    public VRegionsDTO region(
        @Argument Long id,
        @Argument String lang
    ) {
        return vRegionsService.getVRegions(id, lang);
    }

    @QueryMapping
    public RegionGraphQLDTO regions(
        @Argument String search,
        @Argument String lang
    ) {
        return vRegionsService.getRegionsGraphQL(search, lang);
    }
}
