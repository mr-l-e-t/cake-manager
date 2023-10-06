package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CakeMapper {

    CakeMapper INSTANCE = Mappers.getMapper(CakeMapper.class);
    CakeDTO toCakeDTO(CakeEntity cakeEntity);
    CakeEntity toCakeEntity(CakeDTO cakeDTO);
}
