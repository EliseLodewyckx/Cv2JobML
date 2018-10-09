package com.cegeka.unit;

import com.cegeka.controllers.TrainingController;
import com.cegeka.entities.Training;
import com.cegeka.entities.logging.JMSMessageLogger;
import com.cegeka.services.ITrainingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cegeka.builder.TrainingBuilder.aTraining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TrainingControllerUnitTest {

    @InjectMocks
    private TrainingController controller;

    @Mock
    private ITrainingService service;

    @Mock
    private JMSMessageLogger logger;

    @Test
    public void get_training_by_id() {
        Training t = aTraining().withMachineVersion("test").build();

        when(service.getTraining(1)).thenReturn(t);

        assertEquals(controller.getTraining(1).getBody().getMachineVersion(), "test");
    }

}
