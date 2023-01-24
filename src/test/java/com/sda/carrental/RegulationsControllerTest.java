package com.sda.carrental;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.web.mvc.IndexController;
import com.sda.carrental.web.mvc.RegulationsController;


@SpringBootTest
public class RegulationsControllerTest extends BaseTest
{
    @Test
    public void testRegulationsPage() throws Exception {

        RegulationsController regulationsController = new RegulationsController();
        MockMvc mockMvc = standaloneSetup(regulationsController).build();
        mockMvc.perform(get("/regulations")).andExpect(view().name("regulations"));

    }
}
