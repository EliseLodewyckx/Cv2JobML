package com.cegeka.integration.controller;

import com.cegeka.builder.UploadBuilder;
import com.cegeka.controllers.UploadController;
import com.cegeka.entities.Upload;
import com.cegeka.representation.UploadR;
import com.cegeka.services.IUploadService;
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

import static com.cegeka.builder.UploadBuilder.aUpload;
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
public class UploadControllerIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private IUploadService service;

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
    public void setUp() {
        List<Upload> uploadList = service.getAllUpload();

        for (Upload upload : uploadList) {
            service.delete(upload.getId());
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void test_get_upload_by_id() throws Exception {
        Upload upload = aUpload().withId(0).withUpload("test").build();

        long time = upload.getTimestamp().getTime()/1000*1000;

        service.addUpload(upload);

        int id = upload.getId();

        mockMvc.perform(get(UploadController.BASE_URL + "/get/" + id)
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(UploadR.of(id, "test", new Timestamp(time)))));
    }

    @Test
    public void get_all_uploads() throws Exception {

        Upload upload = aUpload().withId(1).withUpload("test").build();
        service.addUpload(upload);

        long t = upload.getTimestamp().getTime()/1000*1000;
        int id = upload.getId();


        mockMvc.perform(get(UploadController.BASE_URL + "/all").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(singletonList(UploadR.of(id, "test", new Timestamp(t))))));

    }

    @Test
    public void add_upload_by_admin() throws Exception {
        Upload upload = new Upload();
        long t = upload.getTimestamp().getTime()/1000*1000;
        service.addUpload(upload);

        int id= service.getAllUpload().get(0).getId()+1;

        mockMvc.perform(post(UploadController.BASE_URL + "/add").with(user("admin").roles("ADMIN"))
                .content(asJson(UploadR.of(id, "", new Timestamp(t))))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        mockMvc.perform(get(UploadController.BASE_URL + "/get/" + id).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(asJson(UploadR.of(id, "", new Timestamp(t)))));


    }

    @Test
    public void add_upload_by_user() throws Exception {
        Upload upload = new Upload();
        long t = upload.getTimestamp().getTime()/1000*1000;
        int id = upload.getId();
        mockMvc.perform(post(UploadController.BASE_URL + "/add")
                .with(user("user").roles("USER"))
                .content(asJson(UploadR.of(id, "", new Timestamp(t))))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(201));


    }

    @Test
    public void test_delete_upload() throws Exception {
        Upload upload = new Upload();
        service.addUpload(upload);
        int id = upload.getId();

        service.delete(id);

        mockMvc.perform(get(UploadController.BASE_URL + "/get/" + id).with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());

    }
        @SuppressWarnings("unchecked")
    protected String asJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
