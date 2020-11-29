package com.atm.controller;

import com.atm.data.dto.atm.AtmDTO;
import com.atm.data.dto.atm.CreateAtmDTO;
import com.atm.data.dto.atm.UpdateAtmDTO;
import com.atm.data.objects.DataWrapper;
import com.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

    @Autowired
    private AtmService atmService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public AtmDTO create(@RequestBody CreateAtmDTO createAtmDTO) {
        return atmService.create(createAtmDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public AtmDTO update(@RequestBody UpdateAtmDTO updateAtmDTO) {
        return atmService.update(updateAtmDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AtmDTO getById(@PathVariable Long id) {
        return atmService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public DataWrapper<Boolean> delete(@PathVariable Long id) {
        return atmService.delete(id);
    }
}
