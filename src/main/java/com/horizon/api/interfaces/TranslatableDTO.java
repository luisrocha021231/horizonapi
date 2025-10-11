package com.horizon.api.interfaces;

public interface TranslatableDTO {
    
    default String[] getExcludedFields() {
        return new String[]{"id", "slug", "appears_in"};
    }
}
