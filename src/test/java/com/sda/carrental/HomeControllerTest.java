package com.sda.carrental;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.web.mvc.IndexController;


public class HomeControllerTest extends BaseTest
{
    @Test
    public void testIndexPage() throws Exception {

        final DepartmentService departmentService = new DepartmentService(departmentRepository);

        IndexController indexController = new IndexController(departmentService);
        MockMvc mockMvc = standaloneSetup(indexController).build();
        mockMvc.perform(get("/")).andExpect(view().name("index"));

    }
}
