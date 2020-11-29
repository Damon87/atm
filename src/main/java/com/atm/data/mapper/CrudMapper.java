package com.atm.data.mapper;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface CrudMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> extends Mapper<ENTITY, DTO> {

    ENTITY toCreateEntity(CREATE_DTO dto, List<String> args);

    ENTITY toUpdateEntity(ENTITY entity, UPDATE_DTO dto, List<String> args);

    default ENTITY toCreateEntity(CREATE_DTO dto) {
        return toCreateEntity(dto, new ArrayList<>());
    }

    default ENTITY toUpdateEntity(ENTITY entity, UPDATE_DTO dto) {
        return toUpdateEntity(entity, dto, new ArrayList<>());
    }

    default List<ENTITY> toCreateEntity(Collection<CREATE_DTO> dtos, List<String> args) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream().map(dto -> toCreateEntity(dto, args)).collect(Collectors.toList());
    }

    default List<ENTITY> toUpdateEntity(Collection<Pair<ENTITY, UPDATE_DTO>> pairs, List<String> args) {
        if (pairs == null) {
            return new ArrayList<>();
        }

        return pairs.stream().map(pair -> toUpdateEntity(pair.getFirst(), pair.getSecond(), args)).collect(Collectors.toList());
    }

    default List<ENTITY> toCreateEntity(Collection<CREATE_DTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return toCreateEntity(dtos, Collections.emptyList());
    }

    default List<ENTITY> toUpdateEntity(Collection<Pair<ENTITY, UPDATE_DTO>> pairs) {
        if (pairs == null) {
            return new ArrayList<>();
        }

        return toUpdateEntity(pairs, Collections.emptyList());
    }

    default ENTITY createDefault() {
        return createDefault(Collections.emptyList());
    }

    default ENTITY createDefault(List<String> args) {
        throw new IllegalStateException("Not supported");
    }

}
