package com.waracle.cakemgr.service;

import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CakeServiceTest {

    @Mock
    private CakeRepository cakeRepository;

    @Mock
    private CakeMapper cakeMapper;
    @Captor
    private ArgumentCaptor<CakeEntity> captor;

    @Autowired
    @InjectMocks //the annotation will initialize the CakeServiceImpl object with the CakeRepository mock
    private CakeServiceImpl cakeService;
    private List<CakeEntity> cakeEntitiesThatWillBeRetrievedFromRepository;
    private List<CakeDTO> cakeDTOsExpectedFromService;
    private CakeDTO cakeDTOExpectedFromService;
    private CakeEntity cakeEntityThatWillBeRetrievedFromRepository;

    private static final int CAKE_ID = 1;

    @BeforeEach
    public void setUp(){
        cakeEntitiesThatWillBeRetrievedFromRepository = TestUtils.TWO_CAKE_ENTITY_LIST_WITH_ID;
        cakeEntityThatWillBeRetrievedFromRepository = TestUtils.CAKE_ENTITY;
        cakeDTOsExpectedFromService = TestUtils.TWO_CAKE_LIST_WITH_ID;
        cakeDTOExpectedFromService = TestUtils.CAKE_DTO;
    }
    @AfterEach
    public void tearDown(){
        cakeEntitiesThatWillBeRetrievedFromRepository = null;
        cakeEntityThatWillBeRetrievedFromRepository = null;
        cakeDTOsExpectedFromService = null;
        cakeDTOExpectedFromService=null;
    }

    @Test
    public void givenGetAllCakesShouldReturnListOfAllCakes(){
        //given
        when(cakeRepository.findAll()).thenReturn(cakeEntitiesThatWillBeRetrievedFromRepository);
        when(cakeMapper.toCakeDTO(cakeEntitiesThatWillBeRetrievedFromRepository.get(0))).thenReturn(cakeDTOsExpectedFromService.get(0));
        when(cakeMapper.toCakeDTO(cakeEntitiesThatWillBeRetrievedFromRepository.get(1))).thenReturn(cakeDTOsExpectedFromService.get(1));

        //then return list of cakes
        List<CakeDTO> cakesFromService =cakeService.getAllCakes();
        //assert all results are as expected, and all parameters passed are as expected
        assertEquals(cakeDTOsExpectedFromService,cakesFromService);

        verify(cakeRepository,times(1)).findAll();
        verify(cakeMapper, times(2)).toCakeDTO(captor.capture());

        assertEquals(captor.getAllValues().get(0), cakeEntitiesThatWillBeRetrievedFromRepository.get(0));
        assertEquals(captor.getAllValues().get(1), cakeEntitiesThatWillBeRetrievedFromRepository.get(1));
    }

    @Test
    public void givenGetCakeWithIdThenReturnSingleCake(){
        //given
        when(cakeRepository.getReferenceById(CAKE_ID)).thenReturn(cakeEntityThatWillBeRetrievedFromRepository);
        when(cakeMapper.toCakeDTO(cakeEntityThatWillBeRetrievedFromRepository)).thenReturn(cakeDTOExpectedFromService);

        //then return single cake
        CakeDTO cakeFromService = cakeService.getCake(CAKE_ID);
        assertEquals(cakeDTOExpectedFromService,cakeFromService);

        verify(cakeRepository,times(1)).getReferenceById(CAKE_ID);
    }
}
