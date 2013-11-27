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
package com.medvision360.medrecord.api.archetype;

import com.medvision360.medrecord.api.IDList;
import com.medvision360.medrecord.api.exceptions.ClientParseException;
import com.medvision360.medrecord.api.exceptions.PatternException;
import com.medvision360.medrecord.api.exceptions.DuplicateException;
import com.medvision360.medrecord.api.exceptions.IORecordException;
import com.medvision360.medrecord.api.exceptions.MissingParameterException;
import com.medvision360.medrecord.api.exceptions.ParseException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * @apipath /archetype
 */
@SuppressWarnings("DuplicateThrows")
public interface ArchetypeListResource
{
    /**
     * Create archetype resource.
     * 
     * Store an archetype from an ADL string (plain text). Will result in DUPLICATE_EXCEPTION if the archetype 
     * already exists. If you want to update an archetype that's unused, you can delete it first and then re-upload 
     * it. Updating archetypes once they are in use is not possible.
     * 
     * Note that for non-web-based tools, simply using the plain text API is probably much easier, i.e. something like
     * <code>curl -X POST -T foo.adl -H "Content-Type: text/plain" $URL/medrecord/v2/archetype</code>
     * works fine.
     */
    @Post("json")
    public void postArchetype(ArchetypeRequest archetype)
            throws DuplicateException, ClientParseException, MissingParameterException,
            RecordException, IORecordException;
    
    /**
     * Create archetype resource.
     * 
     * @apiacceptvariant postArchetype
     */
    @Post("txt")
    public void postArchetypeAsText(String adl)
            throws DuplicateException, ClientParseException, MissingParameterException,
            RecordException, IORecordException;

    /**
     * List archetype resources.
     * 
     * Retrieve a list of archetype IDs known to the server encapsulated in JSON.
     * 
     * @apiqueryparam q A regular expression to limit the returned archetypes by their name.
     *   [type=string,single,default=openEHR-EHR.*]
     */
    @Get
    public IDList listArchetypes()
            throws PatternException, ParseException, RecordException, IORecordException;
}
