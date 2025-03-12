package com.cwi.cooperativa.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CooperativaException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(CooperativaException.class);

    public CooperativaException(String mensagem) {
        super(mensagem);
        logger.info("CooperativaException.class: {}", mensagem);
    }
}
