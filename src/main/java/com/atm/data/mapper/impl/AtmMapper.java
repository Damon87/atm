package com.atm.data.mapper.impl;

import com.atm.data.domain.Atm;
import com.atm.data.dto.atm.AtmDTO;
import com.atm.data.dto.atm.CreateAtmDTO;
import com.atm.data.dto.atm.UpdateAtmDTO;
import com.atm.data.mapper.CrudMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AtmMapper implements CrudMapper<Atm, AtmDTO, CreateAtmDTO, UpdateAtmDTO> {

    @Override
    public Atm toCreateEntity(CreateAtmDTO createAtmDTO, List<String> args) {
        Atm atm = new Atm();

        atm.setAmount(createAtmDTO.getAmount());

        return atm;
    }

    @Override
    public Atm toUpdateEntity(Atm atm, UpdateAtmDTO updateAtmDTO, List<String> args) {
        atm.setAmount(updateAtmDTO.getAmount());
        return atm;
    }

    @Override
    public AtmDTO toDto(Atm atm, List<String> args) {
        AtmDTO atmDTO = new AtmDTO();

        atmDTO.setId(atm.getId());
        atmDTO.setAmount(atm.getAmount());

        return atmDTO;
    }
}
