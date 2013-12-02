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
package com.medvision360.medrecord.server.archetype;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.medvision360.medrecord.api.IDList;
import com.medvision360.medrecord.api.archetype.ArchetypeListResource;
import com.medvision360.medrecord.api.archetype.ArchetypeRequest;
import com.medvision360.medrecord.api.exceptions.ClientParseException;
import com.medvision360.medrecord.api.exceptions.IORecordException;
import com.medvision360.medrecord.api.exceptions.MissingParameterException;
import com.medvision360.medrecord.api.exceptions.ParseException;
import com.medvision360.medrecord.api.exceptions.PatternException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import com.medvision360.medrecord.engine.MedRecordEngine;
import com.medvision360.medrecord.server.AbstractServerResource;
import com.medvision360.medrecord.spi.ArchetypeParser;
import com.medvision360.medrecord.spi.WrappedArchetype;
import com.medvision360.wslog.Events;
import org.apache.commons.io.IOUtils;
import org.openehr.rm.support.identification.ArchetypeID;

public class ArchetypeListServerResource
        extends AbstractServerResource
        implements ArchetypeListResource
{
    @Override
    public void postArchetype(ArchetypeRequest archetype) throws RecordException
    {
        String adl = archetype.getAdl();
        postArchetypeAsText(adl);
    }

    @Override
    public void postArchetypeAsText(String adl) throws RecordException
    {
        String archetypeId = null;
        try
        {
            try
            {
                if (adl == null || adl.isEmpty())
                {
                    throw new MissingParameterException("Provide a non-empty ADL containing the archetype definition");
                }
                
                MedRecordEngine engine = engine();
                ArchetypeParser parser = engine.getArchetypeParser("text/plain", "adl");
                WrappedArchetype archetype;
                try
                {
                    archetype = parser.parse(IOUtils.toInputStream(adl, "UTF-8"));
                }
                catch (ParseException e)
                {
                    Throwable root = e;
                    int limit = 10, i = 0;
                    while (e.getCause() != null && i < limit)
                    {
                        root = e.getCause();
                        i++;
                    }
                    throw new ClientParseException(root.getMessage(), e);
                }
                archetypeId = archetype.getArchetype().getArchetypeId().getValue();
                engine.getArchetypeStore().insert(archetype);
            }
            catch (IOException e)
            {
                throw new IORecordException(e.getMessage(), e);
            }
            Events.append(
                    "INSERT",
                    archetypeId,
                    "ARCHETYPE",
                    "postArchetype",
                    String.format(
                            "Inserted archetype %s",
                            archetypeId));
        }
        catch (RecordException|RuntimeException e)
        {
            Events.append(
                    "ERROR",
                    archetypeId,
                    "ARCHETYPE",
                    "postArchetypeFailure",
                    String.format(
                            "Failure to insert archetype %s: %s",
                            archetypeId,
                            e.getMessage()));
            throw e;
        }
    }

    @Override
    public IDList listArchetypes() throws RecordException
    {
        String q = null;
        try
        {
            try
            {
                Pattern pattern = null;
                q = getQueryValue("q");

                // Just to be quite clear about this: at this point q is a user-provided 'tainted' parameter.
                // It contains a regular expression. One of the many fun things you can do with regular expressions
                // is to write one which takes a glacial amount of time to process, and there is no good way to
                // predict that this may happen. So this is a great place to, for example, try to do a DOS attack
                // against the server.
                //
                // The assumption is that this API is deployed safely behind some kind of AAA similar to what
                // you would use to secure a web based SSH console or SQL admin console.

                if (q != null && !q.isEmpty())
                {
                    if (!q.startsWith("^"))
                    {
                        q = "^.*?" + q;
                    }
                    if (!q.endsWith("$"))
                    {
                        q += ".*?$";
                    }
                    try
                    {
                        pattern = Pattern.compile(q);
                    }
                    catch (PatternSyntaxException e)
                    {
                        throw new PatternException(e.getMessage());
                    }
                }
                
                Iterable<ArchetypeID> idList = engine().getArchetypeStore().list();
                Iterable<String> stringList = Iterables.transform(idList, new Function<ArchetypeID,String>() {
                    @Override
                    public String apply(ArchetypeID input)
                    {
                        if (input == null)
                        {
                            return null;
                        }
                        return input.getValue();
                    }
                });
                if (pattern != null)
                {
                    final Pattern finalPattern = pattern;
                    stringList = Iterables.filter(stringList, new Predicate<String>()
                    {
                        @Override
                        public boolean apply(String input)
                        {
                            return finalPattern.matcher(input).matches();
                        }
                    });
                }
                
                IDList result = new IDList();
                result.setIds(Lists.newArrayList(stringList));
                Events.append(
                        "LIST",
                        q == null ? "all" : q,
                        "ARCHETYPE_LIST",
                        "listArchetypes",
                        String.format(
                                "Listed archetypes%s",
                                q == null ? "" : " matching " + q));
                return result;
            }
            catch (IOException e)
            {
                throw new IORecordException(e.getMessage(), e);
            }
        }
        catch(RecordException|RuntimeException e)
        {
            Events.append(
                    "ERROR",
                    q == null ? "all" : q,
                    "ARCHETYPE_LIST",
                    "listArchetypesFailure",
                    String.format(
                            "Failure to list archetypes%s: %s",
                            q == null ? "" : " matching " + q,
                            e.getMessage()));
            throw e;
        }
    }
}
