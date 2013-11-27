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
package com.medvision360.medrecord.api.locatable;

import com.medvision360.medrecord.api.EHR;
import com.medvision360.medrecord.api.exceptions.IORecordException;
import com.medvision360.medrecord.api.exceptions.InvalidLocatableIDException;
import com.medvision360.medrecord.api.exceptions.LocatableHasNoEHRException;
import com.medvision360.medrecord.api.exceptions.NotFoundException;
import com.medvision360.medrecord.api.exceptions.ParseException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import org.restlet.resource.Get;

/**
 * @apipath /locatable/{id}/ehr
 * @apipathparam id An OpenEHR UIDBasedID value identifying a Locatable.
 *   [type=string,required,single,default=71350448-25BA-4395-B354-19B9CA9D5096]
 */
@SuppressWarnings("DuplicateThrows")
public interface LocatableEHRResource
{
    /**
     * Retrieve locatable its EHR resource.
     *
     * Retrieve basic info about the EHR containing this locatable as a JSON structure.
     */
    @Get("json")
    public EHR getEHRForLocatable()
            throws NotFoundException, ParseException, InvalidLocatableIDException, LocatableHasNoEHRException,
            RecordException, IORecordException;
}
