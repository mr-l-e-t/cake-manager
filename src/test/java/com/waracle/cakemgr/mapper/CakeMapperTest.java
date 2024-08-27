package com.waracle.cakemgr.mapper;


import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(MockitoExtension.class)
public class CakeMapperTest {

    private CakeMapper cakeMapper = Mappers.getMapper(CakeMapper.class);

    @Test
    public void givenCakeEntityThenMapToCakeDTO(){

        CakeDTO cakeDTO = cakeMapper.entityToDTO(TestUtils.CAKE_ENTITY);

        assertThat(cakeDTO).isNotNull();
        assertThat(cakeDTO.id()).isEqualTo(TestUtils.CAKE_ENTITY.getId());
        assertThat(cakeDTO.title()).isEqualTo(TestUtils.CAKE_ENTITY.getTitle());
        assertThat(cakeDTO.description()).isEqualTo(TestUtils.CAKE_ENTITY.getDescription());
        assertThat(cakeDTO.imageURL()).isEqualTo(TestUtils.CAKE_ENTITY.getImageURL());
    }

    @Test
    public void givenCakeEntityListThenMapToCakeDTOList(){
        List<CakeDTO> cakeDTOs = cakeMapper.entitiesToDTOs(TestUtils.TWO_CAKE_ENTITY_LIST_WITH_ID);
        assertArrayEquals(cakeDTOs.toArray(), TestUtils.TWO_CAKE_LIST_WITH_ID.toArray());
    }


    @Test
    public void givenCakeDTOThenMapToCakeEntity(){

        CakeEntity cakeEntity = cakeMapper.dtoToEntity(TestUtils.CAKE_DTO);

        assertThat(cakeEntity).isNotNull();
        assertThat(cakeEntity.getId()).isEqualTo(TestUtils.CAKE_DTO.id());
        assertThat(cakeEntity.getTitle()).isEqualTo(TestUtils.CAKE_DTO.title());
        assertThat(cakeEntity.getDescription()).isEqualTo(TestUtils.CAKE_DTO.description());
        assertThat(cakeEntity.getImageURL()).isEqualTo(TestUtils.CAKE_DTO.imageURL());
    }
}
