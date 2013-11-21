package com.medvision360.medrecord.itest;

import com.medvision360.medrecord.api.exceptions.RecordException;

@SuppressWarnings("UnusedDeclaration")
public class GenerateException extends RecordException
{
    private static final long serialVersionUID = 0x130L;
    
    public GenerateException()
    {
    }

    public GenerateException(String message)
    {
        super(message);
    }

    public GenerateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GenerateException(Throwable cause)
    {
        super(cause);
    }

    public GenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
