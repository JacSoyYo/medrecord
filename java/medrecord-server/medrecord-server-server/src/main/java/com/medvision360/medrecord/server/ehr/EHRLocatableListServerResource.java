package com.medvision360.medrecord.server.ehr;

import com.medvision360.medrecord.api.ID;
import com.medvision360.medrecord.api.IDList;
import com.medvision360.medrecord.api.ehr.EHRLocatableListResource;
import com.medvision360.medrecord.api.exceptions.AnnotatedIllegalArgumentException;
import com.medvision360.medrecord.api.exceptions.AnnotatedUnsupportedOperationException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import com.medvision360.medrecord.api.exceptions.RuntimeRecordException;
import com.medvision360.medrecord.server.AbstractServerResource;
import org.restlet.representation.Representation;

public class EHRLocatableListServerResource
        extends AbstractServerResource
        implements EHRLocatableListResource
{
    @Override
    public ID postLocatable(Representation representation) throws RecordException
    {
        throw new AnnotatedUnsupportedOperationException("todo implement");
    }

    @Override
    public IDList listLocatables() throws RecordException
    {
        throw new AnnotatedUnsupportedOperationException("todo implement");
    }
}
