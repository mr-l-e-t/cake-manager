package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.repository.CakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CakeServiceImpl implements CakeService {

    @Autowired
    private CakeRepository cakeRepository;
    @Autowired
    private CakeMapper cakeMapper;

    @Override
    public List<CakeDTO> getAllCakes() {
        return cakeRepository.findAll().stream().map(cakeMapper::toCakeDTO).toList();
    }

    @Override
    public CakeDTO getCake(int id) {
        CakeEntity cakeFromDB =cakeRepository.getReferenceById(id);

        return cakeMapper.toCakeDTO(cakeFromDB);
    }

    @Override
    public CakeDTO save(CakeDTO cakeToSave) {
        log.info("cakeToSave: {}",cakeToSave);
        CakeEntity cakeCreated = cakeRepository.save(cakeMapper.toCakeEntity(cakeToSave));
        log.info("cakeCreated: {}",cakeCreated);

        return cakeMapper.toCakeDTO(cakeCreated);
    }
}
