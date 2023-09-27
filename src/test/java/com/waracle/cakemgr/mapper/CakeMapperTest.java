package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
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
}
