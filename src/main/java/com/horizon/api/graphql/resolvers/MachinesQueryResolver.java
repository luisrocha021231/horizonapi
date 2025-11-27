package com.horizon.api.graphql.resolvers;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.horizon.api.dtos.MachinePageGraphQLDTO;
import com.horizon.api.dtos.VMachinesDTO;
import com.horizon.api.services.VMachinesGraphQLService;

@Controller
public class MachinesQueryResolver {
    
    private final VMachinesGraphQLService vMachinesGraphQLService;

    public MachinesQueryResolver(VMachinesGraphQLService vMachinesGraphQLService) {
        this.vMachinesGraphQLService = vMachinesGraphQLService;
    }

    @QueryMapping
    public VMachinesDTO machine(
        @Argument Long id,
        @Argument String lang
    ) {
        return vMachinesGraphQLService.getById(id, lang);
    }

    @QueryMapping
    public MachinePageGraphQLDTO machines(
        @Argument Integer page,
        @Argument Integer size,
        @Argument String search,
        @Argument String lang
    ) {
        return vMachinesGraphQLService.getMachines(page, size, search, lang);
    }
}
