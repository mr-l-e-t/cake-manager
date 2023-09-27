package com.waracle.cakemgr.integrationTests;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

        ResultActions result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getTwoCakeListAsJson()));

        verify(cakeRepository,times(1)).findAll();
    }

    @Test
    public void givenCallingGetCakeWithIdThenReturnSingleCakeAndStatus200_ok() throws Exception{
        when(cakeRepository.getReferenceById(1)).thenReturn(TestUtils.CAKE_ENTITY);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cakes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getSingleCakeAsJson()));

        verify(cakeRepository,times(1)).getReferenceById(1);
    }

    @Test
    public void givenCallingGetCakeWithIdNotInDBThenReturnErrorStatus404_notFound() throws Exception{
        when(cakeRepository.getReferenceById(1)).thenThrow(EntityNotFoundException.class);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cakes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getNotFoundErrorPayloadAsJson()));
    }

    @Test
    public void givenCallingGetCakesWithNonNumericParameterThenReturnErrorStatus400_BadRequest() throws Exception{

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/cakes/aaa")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(TestUtils.getBadRequestErrorPayloadAsJson()));
    }
}
