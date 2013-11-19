package com.medvision360.medrecord.server;

import com.medvision360.medrecord.engine.MedRecordEngine;
import com.medvision360.medrecord.spi.exceptions.InitializationException;
import com.medvision360.medrecord.spi.exceptions.TransactionException;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;
import org.restlet.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionFilter extends Filter
{
    private final static Logger log = LoggerFactory.getLogger(TransactionFilter.class);

    public TransactionFilter(Context context, Class<? extends ServerResource> targetClass)
    {
        super(context);
        setNext(targetClass);
    }
    
    @Override
    protected int beforeHandle(Request request, Response response)
    {
        MedRecordService service = getApplication().getServices().get(MedRecordService.class);
        try
        {
            MedRecordEngine engine = service.engine();
            engine.begin();
            setCommitFlag(true);
            response.setAutoCommitting(false);
        }
        catch (InitializationException | TransactionException e)
        {
            StatusService statusService = getApplication().getStatusService();
            Status status = statusService.getStatus(e, request, response);
            response.setStatus(status);
            log.error(String.format("Error beginning transaction: %s", e.getMessage()), e);
            return STOP;
        }
        
        return CONTINUE;
    }

    @Override
    protected int doHandle(Request request, Response response)
    {
        try
        {
            return super.doHandle(request, response);
        }
        catch (RuntimeException e)
        {
            setCommitFlag(false);
            throw e;
        }
    }

    @Override
    protected void afterHandle(Request request, Response response)
    {
        MedRecordService service = getApplication().getServices().get(MedRecordService.class);
        MedRecordEngine engine;
        try
        {
            engine = service.engine();
        }
        catch (InitializationException e)
        {
            setCommitFlag(false);
            StatusService statusService = getApplication().getStatusService();
            Status status = statusService.getStatus(e, request, response);
            response.setStatus(status);
            log.error(String.format("Error finalizing transaction: %s", e.getMessage()), e);
            response.commit();
            return;
        }
                    
        boolean shouldCommit = getCommitFlag() && response.getStatus().isSuccess();
        try
        {
            if (shouldCommit)
            {
                engine.commit();
            }
            else
            {
                engine.rollback();
            }
        }
        catch (TransactionException e)
        {
            StatusService statusService = getApplication().getStatusService();
            Status status = statusService.getStatus(e, request, response);
            response.setStatus(status);
            log.error(String.format("Error finalizing transaction: %s", e.getMessage()), e);
        }
        finally
        {
            response.commit();
        }
    }

    private void setCommitFlag(boolean value)
    {
        getContext().getAttributes().put("TransactionFilter.commit", value);
    }

    private boolean getCommitFlag()
    {
        return Boolean.valueOf(String.valueOf(
                getContext().getAttributes().get("TransactionFilter.commit")));
    }

}
