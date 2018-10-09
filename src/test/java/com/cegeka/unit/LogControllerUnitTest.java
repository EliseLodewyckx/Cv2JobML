package com.cegeka.unit;

import com.cegeka.controllers.LogController;
import com.cegeka.entities.logging.Log;
import com.cegeka.services.ILogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cegeka.builder.LogBuilder.aLog;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class LogControllerUnitTest {
    @InjectMocks
    private LogController controller;

    @Mock
    private ILogService service;

    @Test
    public void get_training_by_id() {
        Log l = aLog().withMessage("test").build();

        when(service.find(1)).thenReturn(l);

        assertEquals("test", controller.getLog(1).getBody().getMessage());
    }
}
