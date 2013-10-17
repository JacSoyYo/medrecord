/**
 * This file is part of MEDrecord
 *
 * @copyright Copyright 2013 by MEDvision360. All rights reserved.
 * @author Leo Simons <leo@medvision360.com>
 * @author Ralph van Etten <ralph@medvision360.com>
 */
package com.medvision360.medrecord.spi.exceptions;

@SuppressWarnings("UnusedDeclaration")
public class DuplicateException extends RecordException
{
    private static final long serialVersionUID = 0x130L;

    public DuplicateException()
    {
    }

    public DuplicateException(String message)
    {
        super(message);
    }

    public DuplicateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DuplicateException(Throwable cause)
    {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
