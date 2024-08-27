package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CakeMapper {

    CakeDTO entityToDTO(CakeEntity cake);
    List<CakeDTO> entitiesToDTOs(Iterable<CakeEntity> cake);

    CakeEntity dtoToEntity(CakeDTO cake);
    List<CakeEntity> dtosToEntities(Iterable<CakeEntity> cake);
}
