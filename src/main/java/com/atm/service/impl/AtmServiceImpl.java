package com.atm.service.impl;

import com.atm.data.domain.Atm;
import com.atm.data.dto.atm.AtmDTO;
import com.atm.data.dto.atm.CreateAtmDTO;
import com.atm.data.dto.atm.UpdateAtmDTO;
import com.atm.data.mapper.impl.AtmMapper;
import com.atm.data.objects.DataWrapper;
import com.atm.repository.AtmRepository;
import com.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AtmServiceImpl implements AtmService {

    @Autowired
    private AtmRepository atmRepository;

    @Autowired
    private AtmMapper atmMapper;

    @Override
    public AtmDTO create(CreateAtmDTO createAtmDTO) {
        Atm atm = atmMapper.toCreateEntity(createAtmDTO);
        return atmMapper.toDto(atmRepository.save(atm));
    }

    @Override
    public AtmDTO update(UpdateAtmDTO updateAtmDTO) {
        Atm atm = atmMapper.toUpdateEntity(atmRepository.getOne(updateAtmDTO.getId()), updateAtmDTO);
        return atmMapper.toDto(atmRepository.save(atm));
    }

    @Override
    public AtmDTO getById(Long id) {
        return atmMapper.toDto(atmRepository.getOne(id));
    }

    @Override
    public DataWrapper<Boolean> delete(Long id) {
        atmRepository.deleteById(id);
        return DataWrapper.of(true);
    }
}
