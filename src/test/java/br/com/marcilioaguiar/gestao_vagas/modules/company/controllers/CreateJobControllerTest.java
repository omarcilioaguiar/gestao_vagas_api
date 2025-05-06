package br.com.marcilioaguiar.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.marcilioaguiar.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.marcilioaguiar.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.marcilioaguiar.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.marcilioaguiar.gestao_vagas.modules.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before("setup")
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Deve Ser Capaz De Criar Uma Nova Vaga")
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION_TEST")
                .email("email@company.com")
                .password("1234567890")
                .username("COMPANY_USERNAME_TEST")
                .name("COMPANY_NAME_TEST")
                .build();

        company = companyRepository.saveAndFlush(company);

        var createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION_TEST")
                .benefits("BENEFITS_TEST")
                .level("LEVEL_TEST")
                .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(createJobDTO))
                .header("Authorization",
                        TestUtils.generateToken(company.getId(),
                                "JAVAGAS_@123#")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    @Test
    @DisplayName("Deve Retornar Erro Se A Empresa Não For Encontrada")
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {

        var createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION_TEST")
                .benefits("BENEFITS_TEST")
                .level("LEVEL_TEST")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(createJobDTO))
                .header("Authorization",
                        TestUtils.generateToken(UUID.randomUUID(),
                                "JAVAGAS_@123#")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}