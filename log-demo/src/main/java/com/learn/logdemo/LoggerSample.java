package com.learn.logdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author xgangzai
 * @Date 2019/10/24 11:44
 */
public class LoggerSample {



    private static final Logger logger= LoggerFactory.getLogger(LoggerSample.class);

    public static void main(String[] args) {
        logger.info(logger.getClass().getName());
        if (logger.isTraceEnabled()){
            logger.trace("trace enable");
        }

        if (logger.isDebugEnabled()){
            logger.debug("debug enable");
        }

        if (logger.isInfoEnabled()){
            logger.info("info enable");
        }

        if (logger.isWarnEnabled()){
            logger.warn("warn enable");
        }

        if (logger.isErrorEnabled()){
            logger.error("error enable");
        }
    }



}
