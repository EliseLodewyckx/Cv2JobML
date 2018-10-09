package com.cegeka.integration.controller;

import com.cegeka.controllers.LogController;
import com.cegeka.controllers.TrainingController;
import com.cegeka.entities.logging.Log;
import com.cegeka.representation.LogR;
import com.cegeka.services.ILogService;
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
import java.sql.Timestamp;
import java.util.List;

import static com.cegeka.builder.LogBuilder.aLog;
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
public class LogControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ILogService service;

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
        List<Log> logs = service.All();

        for(Log log : logs) {
            service.delete(log.getId());
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void test_get_log_by_id() throws Exception {
        Log log = aLog()
                .withMessage("Test log.")
                .build();
        long time = log.getTime().getTime() /1000 * 1000;
        service.persist(log);

        int id = log.getId();

        mockMvc.perform(get(LogController.BASE_URL + "/get/" + id)
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(
                        LogR.of(id, "Test log.", new Timestamp(time))
                )));
    }

    @Test
    public void test_all_logs() throws Exception {
        Log log = aLog()
                .withMessage("Test log.")
                .build();

        service.persist(log);
        long t = log.getTime().getTime()/1000*1000;
        int id = log.getId();

        mockMvc.perform(get(LogController.BASE_URL + "/all")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(
                        singletonList(LogR.of(id,
                                "Test log.",
                                new Timestamp(t))))
                ));
    }

    @Test
    public void test_add_log_by_admin() throws Exception {
        Log log = new Log();
        long time = log.getTime().getTime() /1000 *1000;
        service.persist(log);

        int id= service.All().get(0).getId()+1;

        mockMvc.perform(post(LogController.BASE_URL + "/add").with(user("admin").roles("ADMIN"))
                .content(asJson(LogR.of(id, "", new Timestamp(time))))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        mockMvc.perform(get(LogController.BASE_URL + "/get/" + id).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(LogR.of(id, "", new Timestamp(time)))));
    }

    @Test
    public void test_add_log_no_auth() throws Exception {
        Log log = new Log();
        int id= log.getId()+1;
        long time = log.getTime().getTime() /1000 *1000;

        mockMvc.perform(post(TrainingController.BASE_URL + "/add")
                .with(user("user").roles("USER"))
                .content(asJson(LogR.of(id, "", new Timestamp(time))))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(403));

    }


    @Test
    public void test_delete_log() throws Exception {
        Log log = new Log();
        service.persist(log);
        int id = log.getId();

        service.delete(id);

        mockMvc.perform(get(LogController.BASE_URL + "/get/" + id).with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());

    }

    @SuppressWarnings("unchecked")
    protected String asJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}