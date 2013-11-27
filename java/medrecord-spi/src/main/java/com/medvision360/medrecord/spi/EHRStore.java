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
package com.medvision360.medrecord.spi;

import java.io.IOException;

import com.medvision360.medrecord.api.exceptions.DisposalException;
import com.medvision360.medrecord.api.exceptions.DuplicateException;
import com.medvision360.medrecord.api.exceptions.NotFoundException;
import com.medvision360.medrecord.api.exceptions.NotSupportedException;
import com.medvision360.medrecord.api.exceptions.ParseException;
import com.medvision360.medrecord.api.exceptions.SerializeException;
import org.openehr.rm.ehr.EHR;
import org.openehr.rm.support.identification.HierObjectID;

public interface EHRStore extends TransactionalService, StatusService // todo javadoc / api spec / exceptions
{
    public HierObjectID getSystemID();
    
    public EHR get(HierObjectID id) throws NotFoundException, IOException, ParseException;

    public EHR insert(EHR EHR)
            throws DuplicateException, NotSupportedException, IOException, SerializeException;

    public void delete(HierObjectID id) throws NotFoundException, IOException, ParseException, SerializeException;

    public void undelete(HierObjectID id) throws NotFoundException, IOException, ParseException, SerializeException;

    public boolean has(HierObjectID id) throws IOException, ParseException;

    public Iterable<HierObjectID> list() throws IOException;

    public Iterable<HierObjectID> list(boolean excludeDeleted) throws IOException;

    public void initialize() throws IOException;

    public void dispose() throws DisposalException;

    public void clear() throws IOException;
}
