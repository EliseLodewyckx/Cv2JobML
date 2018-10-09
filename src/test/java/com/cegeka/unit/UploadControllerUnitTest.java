package com.cegeka.unit;

import com.cegeka.controllers.UploadController;
import com.cegeka.entities.Upload;
import com.cegeka.services.IUploadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cegeka.builder.UploadBuilder.aUpload;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UploadControllerUnitTest {
    @InjectMocks
    private UploadController controller;

    @Mock
    private IUploadService service;

    @Test
    public void get_training_by_id() {
        Upload u = aUpload().withUpload("test").build();

        when(service.getUpload(1)).thenReturn(u);

        assertEquals("test", controller.getUpload(1).getBody().getUpload());
    }
}
