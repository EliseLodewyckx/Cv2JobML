package com.cegeka.aop;

import com.cegeka.entities.logging.JMSMessageLogger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PersistSuccesAspect {

    @Autowired
    private JMSMessageLogger logger;

    @After("execution(* *.predict(..)) && args(predictThis)")
    public void predict(String predictThis) {
        logger.log("Predicted possible job for : " + predictThis);
    }

    @After("execution(* *.trainModel(..))")
    public void trainModel() {
        logger.log("Retrained model.");
    }
}
