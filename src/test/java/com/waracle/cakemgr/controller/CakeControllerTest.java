package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.validator.CakeAction;
import com.waracle.cakemgr.validator.CakeManagerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CakeService cakeService;

    @Mock
    private CakeManagerValidator cakeManagerValidator;

    @InjectMocks
    private CakeController cakeController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(cakeController).build();
    }

    @Test
    public void givenCallToGetCakeWithIdThenReturnSingleCake() throws Exception{
        when(cakeService.getCake(1)).thenReturn(TestUtils.CAKE_DTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cake/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getSingleCakeAsJson())).
                andDo(MockMvcResultHandlers.print());
        verify(cakeService).getCake(1);
        verify(cakeService,times(1)).getCake(1);
    }

    @Test
    public void givenCallToCreateCakeThenReturnCakeWithID() throws Exception{

        when(cakeService.save(any(CakeDTO.class))).thenReturn(TestUtils.CAKE_DTO);
        when(cakeManagerValidator.validateCake(any(CakeDTO.class), any(CakeAction.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cake").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getSingleCakeAsJson())).
                andDo(MockMvcResultHandlers.print());
        verify(cakeService).save(TestUtils.CAKE_DTO);
        verify(cakeService,times(1)).save(TestUtils.CAKE_DTO);
    }

    @Test
    public void givenCallToUpdateCakeThenUpdateCake() throws Exception {

        when(cakeService.update(any(CakeDTO.class))).thenReturn(TestUtils.CAKE_DTO);
//        when(cakeManagerValidator.validateCakeUpdate(any(CakeDTO.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cake").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getSingleCakeAsJson())).
                andDo(MockMvcResultHandlers.print());
        verify(cakeService).update(TestUtils.CAKE_DTO);
        verify(cakeService,times(1)).update(TestUtils.CAKE_DTO);
    }
}
