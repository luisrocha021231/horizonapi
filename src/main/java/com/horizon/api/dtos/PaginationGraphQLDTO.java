package com.horizon.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationGraphQLDTO {
    private Integer pageSize;
    private Integer currentPage;
    private Integer totalPages;
    private String nextPage;
    private String previousPage;
}
