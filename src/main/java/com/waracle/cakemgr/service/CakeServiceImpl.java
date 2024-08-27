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
public final class CakeServiceImpl implements CakeService{
//public class CakeServiceImpl implements CakeService{

    @Autowired
    private CakeMapper cakeMapper;

    @Autowired
    CakeRepository cakeRepository;

    @Override
    public List<CakeDTO> getAllCakes() {
        return cakeMapper.entitiesToDTOs(cakeRepository.findAll());
    }

    @Override
    public CakeDTO getCake(int id) {
        return cakeMapper.entityToDTO(cakeRepository.getReferenceById(id));
    }

    @Override
    public CakeDTO save(CakeDTO cakeToSave) {
        log.info("cake to save: {}",cakeToSave);
        CakeEntity cakeCreated = cakeRepository.save(cakeMapper.dtoToEntity(cakeToSave));

        log.info("cakeCreated: {}",cakeCreated);
        return cakeMapper.entityToDTO(cakeCreated);
    }

    @Override
    public CakeDTO update(CakeDTO cakeToUpdate) {
        log.info("cake to update: {}",cakeToUpdate);
        CakeEntity cakeFromDB =cakeRepository.getReferenceById(cakeToUpdate.id());
        cakeFromDB.setTitle(cakeToUpdate.title());
        cakeFromDB.setDescription(cakeToUpdate.description());
        cakeFromDB.setImageURL(cakeToUpdate.imageURL());
        cakeFromDB = cakeRepository.save(cakeFromDB);
        log.info("cake updated: {}",cakeFromDB);

        return cakeMapper.entityToDTO(cakeFromDB);
    }

    @Override
    public void delete(int id) {
        cakeRepository.deleteById(id);
    }
}
