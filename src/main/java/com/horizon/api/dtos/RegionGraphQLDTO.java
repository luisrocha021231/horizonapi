package com.horizon.api.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionGraphQLDTO {
    private List<VRegionsDTO> items;
}
