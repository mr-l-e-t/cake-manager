package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDTO;

import java.util.List;

public sealed interface CakeService permits CakeServiceImpl{
//public interface CakeService {
        /**
         * Retrieve all cakes and map directly into cake record
         * @return
         */
    List<CakeDTO> getAllCakes();

    CakeDTO getCake(int id);

    CakeDTO save(CakeDTO cakeToSave);

    CakeDTO update(CakeDTO cakeToUpdate);

    void delete(int id);
}
