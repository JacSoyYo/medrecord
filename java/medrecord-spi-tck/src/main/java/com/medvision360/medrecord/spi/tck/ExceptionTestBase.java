/**
 * This file is part of MEDrecord.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @copyright Copyright (c) 2013 MEDvision360. All rights reserved.
 * @author Leo Simons <leo@medvision360.com>
 * @author Ralph van Etten <ralph@medvision360.com>
 */
package com.medvision360.medrecord.spi.tck;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;

public abstract class ExceptionTestBase<T extends Exception> extends TestCase
{
    protected abstract Class<T> getExceptionClass();

    protected boolean requireJDK7Constructor()
    {
        // set to false, not working properly when true
        return false;
    }

    protected String msg = "ExceptionTestBase.test";
    protected Throwable cause = new Throwable();

    public void testConstructors() throws Exception
    {
        Class<T> clazz = getExceptionClass();
        Constructor<T> constructor;
        String msg;
        Throwable cause;
        T exception;

        // no args
        clazz.newInstance();

        // msg arg
        constructor = clazz.getConstructor(String.class);
        exception = constructor.newInstance(this.msg);
        msg = exception.getMessage();
        assertTrue(msg.contains(this.msg));

        // cause arg
        constructor = clazz.getConstructor(Throwable.class);
        exception = constructor.newInstance(this.cause);
        cause = exception.getCause();
        assertEquals(this.cause, cause);

        // msg + cause arg
        constructor = clazz.getConstructor(String.class, Throwable.class);
        exception = constructor.newInstance(msg, cause);
        msg = exception.getMessage();
        assertTrue(msg.contains(this.msg));
        cause = exception.getCause();
        assertEquals(this.cause, cause);

        // fancy JDK7 suppression constructor
        if (requireJDK7Constructor())
        {
            constructor = clazz.getConstructor(String.class, Throwable.class, Boolean.TYPE, Boolean.TYPE);
            exception = constructor.newInstance(this.msg, this.cause, true, true);
            msg = exception.getMessage();
            assertTrue(msg.contains(this.msg));
            cause = exception.getCause();
            assertEquals(this.cause, cause);

            constructor.newInstance(this.msg, this.cause, true, false);
            constructor.newInstance(this.msg, this.cause, false, true);
            constructor.newInstance(this.msg, this.cause, false, false);

            // we don't test whether the boolean settings are properly ordered since it would be particularly tedious
            // to create a test that always works
        }
    }
}
