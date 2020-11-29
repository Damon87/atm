package com.atm.data.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<ENTITY, DTO> {

    String SHOW_CHILD = "SHOW CHILD";
    String SHOW_PARENT = "SHOW_PARENT";

    DTO toDto(ENTITY entity, List<String> args);

    default DTO toDto(ENTITY entity) {
        return toDto(entity, new ArrayList<>());
    }

    default List<DTO> toDto(Collection<ENTITY> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return toDto(entities, Collections.emptyList());
    }

    default List<DTO> toDto(Collection<ENTITY> entities, List<String> args) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream().map(entity -> toDto(entity, args)).collect(Collectors.toList());
    }

}
