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
package com.medvision360.medrecord.server.ehr;

import java.io.IOException;

import com.medvision360.medrecord.api.EHR;
import com.medvision360.medrecord.api.ehr.EHRResource;
import com.medvision360.medrecord.api.exceptions.IORecordException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import org.openehr.rm.support.identification.HierObjectID;

public class EHRServerResource
        extends AbstractEHRResource implements EHRResource
{
    @Override
    public EHR getEHR()
            throws RecordException
    {
        org.openehr.rm.ehr.EHR ehr = getEHRModel();
        EHR result = toEHRResult(ehr);
        return result;
    }

    @Override
    public void deleteEHR() throws RecordException
    {
        HierObjectID id = getEHRID();
        try
        {
            engine().getEHRStore().delete(id);
        }
        catch (IOException e)
        {
            throw new IORecordException(e.getMessage(), e);
        }
    }

}
