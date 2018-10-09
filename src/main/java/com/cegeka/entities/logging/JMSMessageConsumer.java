package com.cegeka.entities.logging;


import com.cegeka.services.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JMSMessageConsumer {
    @Autowired
    private ILogService service;

    @JmsListener(destination = "LoggerQueue")
    public void onMessage(TextMessage message) {

        Log log = new Log();
        try {
            log.setMessage(message.getText());

        } catch (JMSException e) {
            log.setMessage("Log failed: " + e.getMessage());
        } finally {
            service.persist(log);
        }

    }
}
