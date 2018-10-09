package com.cegeka.integration.controller;

import com.cegeka.controllers.TrainingController;
import com.cegeka.entities.Training;
import com.cegeka.representation.TrainingR;
import com.cegeka.services.ITrainingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static com.cegeka.builder.TrainingBuilder.aTraining;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainingControllerIntegrationTest {
    private MockMvc mockMvc;
    private Date date;
    @Autowired
    private ITrainingService service;

    @Autowired
    private WebApplicationContext context;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = asList(converters)
                .stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .get();

        assertThat(mappingJackson2HttpMessageConverter)
                .isNotNull()
                .describedAs("the JSON message converter must not be null");
    }

    @Before
    public void setUp() throws Exception {
        date = new Date(2018,10,4);
        List<Training> trainings = service.getAllTrainings();
        for (Training training : trainings) {
            service.deleteTraining(training.getId());
        }
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void test_get_training_by_id() throws Exception {

        Training training = aTraining()
                .withId(0)
                .withMachineVersion("1.0")
                .withLastTrained(date).build();

        service.addTraining(training);

        int id = training.getId();

        mockMvc.perform(get(TrainingController.BASE_URL + "/get/" + id).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(
                        TrainingR.of(id, "1.0", date )
                )));
    }

    @Test
    public void test_get_all_training() throws Exception {
        Training training = aTraining()
                .withId(1)
                .withMachineVersion("1.0")
                .withLastTrained(date).build();

        service.addTraining(training);

        int id = training.getId();

        mockMvc.perform(get(TrainingController.BASE_URL + "/all").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(singletonList(
                        TrainingR.of(id, "1.0", date )
                ))));

    }

    @Test
    public void test_add_training_with_admin() throws Exception {
        Training training=new Training();
        service.addTraining(training);

        int id = service.getAllTrainings().get(0).getId()+1;

        mockMvc.perform(post(TrainingController.BASE_URL + "/add")
                .with(user("admin").roles("ADMIN"))
                .content(asJson(TrainingR.of(id, "", null)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        mockMvc.perform(get(TrainingController.BASE_URL + "/get/" + id)
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(TrainingR.of(
                        id, "", null
                ))));

    }

    @Test
    public void test_add_training_with_no_auth() throws Exception {
        Training training=new Training();
        int id = training.getId()+1;

        mockMvc.perform(post(TrainingController.BASE_URL + "/add")
                .with(user("user").roles("USER"))
                .content(asJson(TrainingR.of(id, "", null)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(403));
    }


    @Test
    public void test_delete_training() throws Exception {
        Training training=new Training();
        service.addTraining(training);
        int id = training.getId();

        service.deleteTraining(id);

        mockMvc.perform(get(TrainingController.BASE_URL + "/get/" + id).with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());


    }




    protected String asJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
