package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.TestUtils;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.CakeServiceImpl;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CakesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CakeServiceImpl cakeService;//mocking class implementation since it is not possible to mock sealed interfaces

    @InjectMocks
    private CakesController cakesController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(cakesController).build();
    }

    @Test
    public void givenCallToGETCakesApiThenReturnListOfCakes() throws Exception {
        when(cakeService.getAllCakes()).thenReturn(TestUtils.TWO_CAKE_LIST_WITH_ID);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cakes").
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtils.getTwoCakeListAsJson())).
                andDo(MockMvcResultHandlers.print());
        verify(cakeService).getAllCakes();
    }
}
