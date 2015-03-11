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
package com.medvision360.medrecord.api.query;

import com.medvision360.medrecord.api.exceptions.IORecordException;
import com.medvision360.medrecord.api.exceptions.InvalidQueryException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import com.medvision360.medrecord.api.exceptions.UnsupportedQueryException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * @apipath /query/aqlquery
 */
@SuppressWarnings("DuplicateThrows")
public interface AQLQueryResource
{
    /**
     * AQLQuery locatable resources.
     * 
     * @apiqueryparam q An AQL Query, written to run against (a) collection(s) that contains openEHR XML data
     *   [type=string,required,single,default=//archetype_details/archetype_id/value/text()]
     */
    @Get("txt")
    public Representation aqlQuery()
            throws InvalidQueryException, UnsupportedQueryException,
            RecordException, IORecordException;
}
