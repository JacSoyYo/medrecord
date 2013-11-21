package com.medvision360.medrecord.server;

import java.io.IOException;

import com.medvision360.lib.common.exceptions.AnnotatedResourceException;
import com.medvision360.lib.server.service.JsonStatusService;
import com.medvision360.medrecord.api.exceptions.AnnotatedIllegalArgumentException;
import com.medvision360.medrecord.api.exceptions.AnnotatedUnsupportedOperationException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import com.medvision360.medrecord.api.exceptions.RuntimeRecordException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;

public class CustomStatusService extends JsonStatusService
{
    @Override
    public final Status getStatus(
            Throwable throwable,
            final Request request,
            final Response response)
    {
        if (throwable instanceof IllegalArgumentException)
        {
            throwable = new AnnotatedIllegalArgumentException(throwable.getMessage(), throwable);
        }
        if (throwable instanceof UnsupportedOperationException)
        {
            throwable = new AnnotatedUnsupportedOperationException(throwable.getMessage(), throwable);
        }
        else if (
                throwable instanceof IOException ||
                throwable instanceof RuntimeRecordException)
        {
            throwable = toAnnotatedResourceException(throwable);
        }
        return super.getStatus(throwable, request, response);
    }

    private Throwable toAnnotatedResourceException(Throwable throwable)
    {
        Throwable cause = throwable.getCause();
        if (cause != null && cause instanceof AnnotatedResourceException)
        {
            throwable = cause;
        }
        else
        {
            throwable = new RecordException(
                    throwable.getClass().getSimpleName() + ": " + throwable.getMessage(), throwable);
        }
        return throwable;
    }
}
