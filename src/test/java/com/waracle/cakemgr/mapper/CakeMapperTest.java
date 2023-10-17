package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CakeMapperTest {
    @Test
    public void givenCakeEntityThenMapToCakeDTO(){

        CakeDTO cakeDTO = CakeMapper.INSTANCE.toCakeDTO(TestUtils.CAKE_ENTITY);

        assertThat(cakeDTO).isNotNull();
        assertThat(cakeDTO.getId()).isEqualTo(TestUtils.CAKE_ENTITY.getId());
        assertThat(cakeDTO.getTitle()).isEqualTo(TestUtils.CAKE_ENTITY.getTitle());
        assertThat(cakeDTO.getDescription()).isEqualTo(TestUtils.CAKE_ENTITY.getDescription());
        assertThat(cakeDTO.getImageURL()).isEqualTo(TestUtils.CAKE_ENTITY.getImageURL());
    }

    @Test
    public void givenCakeDTOThenMapToCakeEntity(){

        CakeEntity cakeEntity = CakeMapper.INSTANCE.toCakeEntity(TestUtils.CAKE_DTO);

        assertThat(cakeEntity).isNotNull();
        assertThat(cakeEntity.getId()).isEqualTo(TestUtils.CAKE_DTO.getId());
        assertThat(cakeEntity.getTitle()).isEqualTo(TestUtils.CAKE_DTO.getTitle());
        assertThat(cakeEntity.getDescription()).isEqualTo(TestUtils.CAKE_DTO.getDescription());
        assertThat(cakeEntity.getImageURL()).isEqualTo(TestUtils.CAKE_DTO.getImageURL());
    }
}
