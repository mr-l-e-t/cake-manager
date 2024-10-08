package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.service.CakeServiceImpl;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CakeServiceImpl cakeService;

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
    }
    @Test
    public void givenCallToCreateCakeThenReturnCakeWithID() throws Exception{

        when(cakeService.save(any(CakeDTO.class))).thenReturn(TestUtils.CAKE_DTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cake").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getSingleCakeAsJson())).
                andDo(MockMvcResultHandlers.print());

        verify(cakeManagerValidator).validate(TestUtils.CAKE_DTO, CakeAction.CREATE);
        verify(cakeService).save(TestUtils.CAKE_DTO);
    }

    @Test
    public void givenCallToUpdateCakeThenUpdateCake() throws Exception {

        when(cakeService.update(any(CakeDTO.class))).thenReturn(TestUtils.CAKE_DTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cake/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getSingleCakeAsJson())).
                andDo(MockMvcResultHandlers.print());
        verify(cakeService).update(TestUtils.CAKE_DTO);
        verify(cakeManagerValidator).validate(TestUtils.CAKE_DTO, CakeAction.UPDATE);
    }

    @Test
    public void givenCallToDeleteCakeThenDeleteCake() throws Exception {
        doNothing().when(cakeService).delete(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cake/1").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getSingleCakeAsJson())).
                andDo(MockMvcResultHandlers.print());
        verify(cakeService).delete(1);
    }
}
