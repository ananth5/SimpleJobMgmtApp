package com.payoneer.jobmgmtservice.common.constants;

/**
 * @author Ananth Shanmugam 
 */
public class BatchException extends Exception {
    private static final long serialVersionUID = 8657227169611388606L;

    public BatchException(String message) {
        super(message);
    }

    public BatchException(String message, Throwable e) {
        super(message, e);
    }
}
