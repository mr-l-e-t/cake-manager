package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.mapper.CakeMapper;
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
        log.info("cake to save: {}",cakeToSave);
        CakeEntity cakeCreated = cakeRepository.save(cakeMapper.toCakeEntity(cakeToSave));

        log.info("cakeCreated: {}",cakeCreated);

        return cakeMapper.toCakeDTO(cakeCreated);
    }

    @Override
    public CakeDTO update(CakeDTO cakeToUpdate) {

        log.info("cake to update: {}",cakeToUpdate);
        CakeEntity cakeFromDB =cakeRepository.getReferenceById(cakeToUpdate.getId());
        cakeFromDB.setTitle(cakeToUpdate.getTitle());
        cakeFromDB.setDescription(cakeToUpdate.getDescription());
        cakeFromDB.setImageURL(cakeToUpdate.getImageURL());
        cakeFromDB = cakeRepository.save(cakeFromDB);
        log.info("cake updated: {}",cakeFromDB);

        return cakeMapper.toCakeDTO(cakeFromDB);
    }

    @Override
    public void delete(int id) {
        cakeRepository.deleteById(id);
    }
}
