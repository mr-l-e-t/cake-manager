package com.waracle.cakemgr.service;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.mapper.CakeMapper;
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CakeServiceTest {
    @Mock
    private CakeRepository cakeRepository;

    @Mock
    private CakeMapper cakeMapper;

    @Captor
    private ArgumentCaptor<List<CakeEntity>> cakeEntitiesCaptor;//type of argument  I intend to capture when  calling entitiesToDTOs()
    @Captor
    private ArgumentCaptor<CakeEntity> cakeEntityCaptor;//type of argument  I intend to capture when  calling entityToDTO()
    @Captor
    private ArgumentCaptor<CakeDTO> cakeDTOCaptor;//type of argument  I intend to capture when  calling dtoToEntity()

    @Autowired
    @InjectMocks //the annotation will initialize the CakeServiceImpl object with the CakeRepository mock
    private CakeServiceImpl cakeService;

    private List<CakeEntity> cakeEntitiesThatWillBeRetrievedFromRepository;
    private List<CakeDTO> cakeDTOsExpectedFromService;
    private CakeDTO cakeDTOExpectedFromService;

    private CakeDTO cakeDTOToBeCreated;
    private CakeDTO cakeDTOToBeUpdated;

    private CakeEntity cakeEntityThatWillBeRetrievedFromRepository;

    private CakeEntity cakeEntityToBeSavedInCreateCake;
//    private CakeEntity cakeEntityToBeUpdated;

    private static final int CAKE_ID = 1;


    @BeforeEach
    public void setUp(){
        cakeEntitiesThatWillBeRetrievedFromRepository = TestUtils.TWO_CAKE_ENTITY_LIST_WITH_ID;
        cakeEntityThatWillBeRetrievedFromRepository = TestUtils.CAKE_ENTITY;
        cakeEntityToBeSavedInCreateCake = TestUtils.CAKE_ENTITY_WITH_NO_ID;

        cakeDTOsExpectedFromService = TestUtils.TWO_CAKE_LIST_WITH_ID;
        cakeDTOExpectedFromService = TestUtils.CAKE_DTO;
        cakeDTOToBeCreated =  TestUtils.CAKE_DTO;
        cakeDTOToBeUpdated = TestUtils.CAKE_DTO;
    }

    @AfterEach
    public void tearDown(){
        cakeEntitiesThatWillBeRetrievedFromRepository = null;
        cakeEntityThatWillBeRetrievedFromRepository = null;
        cakeEntityToBeSavedInCreateCake =null;
        cakeDTOsExpectedFromService = null;
        cakeDTOExpectedFromService=null;
        cakeDTOToBeCreated =null;
        cakeDTOToBeUpdated = null;
    }

    @Test
    public void givenGetAllCakesShouldReturnListOfAllCakes(){
        //given
        when(cakeRepository.findAll()).thenReturn(cakeEntitiesThatWillBeRetrievedFromRepository);
        when(cakeMapper.entitiesToDTOs(cakeEntitiesThatWillBeRetrievedFromRepository)).thenReturn(cakeDTOsExpectedFromService);


        //then return list of cakes
        List<CakeDTO> cakesFromService =cakeService.getAllCakes();
        //assert all results are as expected, and all parameters passed are as expected
        assertEquals(cakeDTOsExpectedFromService,cakesFromService);

        verify(cakeRepository).findAll();
        verify(cakeMapper).entitiesToDTOs(cakeEntitiesCaptor.capture()); //capture the argument passed to entitiesToDTOs()

        //inspect the capture argument that was passed to entitiesToDTOs()
        assertArrayEquals(cakeEntitiesThatWillBeRetrievedFromRepository.toArray(),  cakeEntitiesCaptor.getValue().toArray());
    }

    @Test
    public void givenGetCakeWithIdThenReturnSingleCake(){
        //given
        when(cakeRepository.getReferenceById(CAKE_ID)).thenReturn(cakeEntityThatWillBeRetrievedFromRepository);
        when(cakeMapper.entityToDTO(cakeEntityThatWillBeRetrievedFromRepository)).thenReturn(cakeDTOExpectedFromService);

        //then return single cake
        CakeDTO cakeFromService = cakeService.getCake(CAKE_ID);
        assertEquals(cakeDTOExpectedFromService,cakeFromService);

        //check that the functions are called
        verify(cakeRepository).getReferenceById(CAKE_ID);
        verify(cakeMapper).entityToDTO(cakeEntityCaptor.capture());//capture the argument passed

        //assert the argument passed is what we expected
        assertEquals(cakeEntityThatWillBeRetrievedFromRepository, cakeEntityCaptor.getValue());
    }

    @Test
    public void givenCreateCakeThenReturnCreatedCake(){
        //given
        when(cakeRepository.save(any(CakeEntity.class))).thenReturn(cakeEntityThatWillBeRetrievedFromRepository);
        when(cakeMapper.dtoToEntity(any(CakeDTO.class))).thenReturn(cakeEntityToBeSavedInCreateCake);
        when(cakeMapper.entityToDTO(any(CakeEntity.class))).thenReturn(cakeDTOToBeCreated);

        //then return single cake
        CakeDTO savedCake = cakeService.save(cakeDTOToBeCreated);
        assertEquals(cakeDTOExpectedFromService,savedCake);

        verify(cakeRepository).save(cakeEntityToBeSavedInCreateCake);
        verify(cakeMapper).dtoToEntity(cakeDTOCaptor.capture());
        verify(cakeMapper).entityToDTO(cakeEntityCaptor.capture());//capture the argument passed

        //assert the argument passed is what we expected
        assertEquals(cakeDTOToBeCreated, cakeDTOCaptor.getValue());
        assertEquals(cakeEntityThatWillBeRetrievedFromRepository, cakeEntityCaptor.getValue());
    }

    @Test
    public void givenUpdateCakeThenReturnUpdatedCake(){
        //given
        when(cakeRepository.getReferenceById(CAKE_ID)).thenReturn(cakeEntityThatWillBeRetrievedFromRepository);
        when(cakeRepository.save(any(CakeEntity.class))).thenReturn(cakeEntityThatWillBeRetrievedFromRepository);
        when(cakeMapper.entityToDTO(any(CakeEntity.class))).thenReturn(cakeDTOToBeUpdated);


        //then return single cake
        CakeDTO updatedCake = cakeService.update(cakeDTOToBeUpdated);
        assertEquals(cakeDTOExpectedFromService,updatedCake);

        verify(cakeRepository).save(cakeEntityThatWillBeRetrievedFromRepository);
        verify(cakeMapper).entityToDTO(cakeEntityCaptor.capture());//capture the argument passed

        //assert the argument passed is what we expected
        assertEquals(cakeEntityThatWillBeRetrievedFromRepository, cakeEntityCaptor.getValue());
    }

    @Test
    public void givenDeleteCakeThenDeleteCake(){
        //given
        doNothing().when(cakeRepository).deleteById(CAKE_ID);

        //then return single cake
        cakeService.delete(CAKE_ID);

        verify(cakeRepository).deleteById(CAKE_ID);
    }
}
