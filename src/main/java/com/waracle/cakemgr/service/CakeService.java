package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDTO;

import java.util.List;

public interface CakeService {
    List<CakeDTO> getAllCakes();

    CakeDTO getCake(int id);

    CakeDTO save(CakeDTO cakeToSave);

    CakeDTO update(CakeDTO cakeToUpdate);

    void delete(int id);
}
