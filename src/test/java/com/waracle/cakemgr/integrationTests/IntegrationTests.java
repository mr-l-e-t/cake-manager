package com.waracle.cakemgr.integrationTests;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeRepository cakeRepository;

    @Test
    public void givenCallingCakesGetCakesThenWillReturnAListOfCakesAndStatus200() throws Exception {
        when(cakeRepository.findAll()).thenReturn(TestUtils.TWO_CAKE_ENTITY_LIST_WITH_ID);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cakes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getTwoCakeListAsJson()));

        verify(cakeRepository,times(1)).findAll();
    }

    @Test
    public void givenCallingGetCakeWithIdThenReturnSingleCakeAndStatus200_ok() throws Exception{
        when(cakeRepository.getReferenceById(1)).thenReturn(TestUtils.CAKE_ENTITY);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cake/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getSingleCakeAsJson()));

        verify(cakeRepository,times(1)).getReferenceById(1);
    }

    @Test
    public void givenCallingGetCakeWithIdNotInDBThenReturnErrorStatus404_notFound() throws Exception{
        when(cakeRepository.getReferenceById(1)).thenThrow(EntityNotFoundException.class);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cake/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getNotFoundErrorPayloadAsJson()));
    }

    @Test
    public void givenCallingGetCakesWithNonNumericParameterThenReturnErrorStatus400_BadRequest() throws Exception{

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cake/aaa")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getBadRequestErrorPayloadAsJson()));
    }

    @Test
    public void givenCallToCreateCakeEndpointWithJsonObjectThenCreateCakeAndReturn201Created() throws Exception {
        //given
        CakeDTO cakeToCreate = TestUtils.CAKE_DTO_WITH_NO_ID;
        when(cakeRepository.save(any(CakeEntity.class))).thenReturn(TestUtils.CAKE_ENTITY);
        //when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/cake")
                .content(TestUtils.asJsonString(cakeToCreate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getSingleCakeAsJson()));
        //and then
        ArgumentCaptor<CakeEntity> capturedTransaction = ArgumentCaptor.forClass(CakeEntity.class);

        Mockito.verify(cakeRepository).save(capturedTransaction.capture());
        assertEquals(TestUtils.CAKE_DTO_WITH_NO_ID.getTitle(), capturedTransaction.getValue().getTitle());
    }

    @Test
    public void givenCallToCreateCakeEndpointWithJsonCakeWithIDThenReturn500Code() throws Exception {
        CakeDTO cakeWithID = TestUtils.CAKE_DTO;
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/cake")
                .content(TestUtils.asJsonString(cakeWithID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getInternalServerErrorValidationErrorIDPresentInCakeObjectAsJson()));;
    }
    @Test
    public void givenCallToCreateCakeEndpointWithJsonCakeWithNoTitleThenReturn500Code() throws Exception {
        CakeDTO cakeWithNoTitle = TestUtils.CAKE_DTO_WITH_NO_TITLE;
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/cake")
                .content(TestUtils.asJsonString(cakeWithNoTitle))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getInternalServerErrorValidationErrorMissingTitleAsJson()));;
    }

    @Test
    public void givenCallToCreateCakeEndpointThenThrowDBErrorAndReturn500Code() throws Exception {
        //given
        CakeDTO cakeToCreate = TestUtils.CAKE_DTO_WITH_NO_ID;
        when(cakeRepository.save(any(CakeEntity.class))).thenThrow(DataIntegrityViolationException.class);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/cake")
                .content(TestUtils.asJsonString(cakeToCreate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getInternalServerErrorJson()));;
    }
    @Test
    public void givenCallToCreateCakeEndpointThenThrowRuntimeExceptionAndReturn500Code() throws Exception {
        CakeDTO cakeToCreate = TestUtils.CAKE_DTO_WITH_NO_ID;
        when(cakeRepository.save(any(CakeEntity.class))).thenThrow(RuntimeException.class);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/cake")
                .content(TestUtils.asJsonString(cakeToCreate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getInternalServerErrorJson()));;
    }

    @Test
    public void givenCallToUpdateCakeEndpointThenUpdateCakeAndReturn200Updated() throws Exception {

        //given
        CakeDTO cakeToUpdate = TestUtils.CAKE_DTO;

        //when
        when(cakeRepository.getReferenceById(1)).thenReturn(TestUtils.CAKE_ENTITY);
        when(cakeRepository.save(any(CakeEntity.class))).thenReturn(TestUtils.CAKE_ENTITY);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/cake")
                .content(TestUtils.asJsonString(cakeToUpdate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getSingleCakeAsJson()));
        //and then
        ArgumentCaptor<CakeEntity> capturedTransaction = ArgumentCaptor.forClass(CakeEntity.class);

        Mockito.verify(cakeRepository).save(capturedTransaction.capture());
        assertEquals(TestUtils.CAKE_DTO_WITH_NO_ID.getTitle(), capturedTransaction.getValue().getTitle());
    }

    @Test
    public void givenCallToUpdateCakeWithNoIDThenThenReturn500Code() throws Exception {
        CakeDTO cakeToUpdateWithNoID = TestUtils.CAKE_DTO_WITH_NO_ID;
        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/cake")
                .content(TestUtils.asJsonString(cakeToUpdateWithNoID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getInternalServerErrorNoIDInCakeObjectErrorPayloadAsJson()));
    }

    @Test
    public void givenCallToUpdateCakeNotInDBThenReturnErrorStatus404_notFound() throws Exception {
        when(cakeRepository.getReferenceById(1)).thenThrow(EntityNotFoundException.class);
        CakeDTO cakeToUpdate= TestUtils.CAKE_DTO;

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/cake")
                .content(TestUtils.asJsonString(cakeToUpdate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getNotFoundErrorPayloadAsJson()));
    }

    @Test
    public void givenCallToDeleteCakeEndpointThenDeleteCakeAndReturn204() throws Exception {
        //given
        doNothing().when(cakeRepository).deleteById(1);

        //when
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/cake/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        //then
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        //and then
        ArgumentCaptor<Integer> capturedTransaction = ArgumentCaptor.forClass(Integer.class);

        Mockito.verify(cakeRepository).deleteById(capturedTransaction.capture());
        assertEquals(1, capturedTransaction.getValue());
    }

}
