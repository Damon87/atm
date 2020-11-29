package com.atm.service;

import com.atm.data.dto.atm.AtmDTO;
import com.atm.data.dto.atm.CreateAtmDTO;
import com.atm.data.dto.atm.UpdateAtmDTO;
import com.atm.data.objects.DataWrapper;

public interface AtmService {
    AtmDTO create(CreateAtmDTO createAtmDTO);

    AtmDTO update(UpdateAtmDTO updateAtmDTO);

    AtmDTO getById(Long id);

    DataWrapper<Boolean> delete(Long id);
}
