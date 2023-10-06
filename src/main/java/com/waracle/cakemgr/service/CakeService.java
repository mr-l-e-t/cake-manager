package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;

import java.util.List;

public interface CakeService {
    List<CakeDTO> getAllCakes();

    CakeDTO getCake(int id);

    CakeDTO save(CakeDTO cakeToSave);
}
