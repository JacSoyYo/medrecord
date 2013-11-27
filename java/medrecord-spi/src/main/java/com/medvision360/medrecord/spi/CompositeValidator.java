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

import java.util.LinkedList;
import java.util.List;

import com.medvision360.medrecord.api.ValidationReport;
import com.medvision360.medrecord.api.exceptions.NotSupportedException;
import com.medvision360.medrecord.api.exceptions.ValidationException;
import com.medvision360.medrecord.spi.base.BaseValidationReport;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.common.archetyped.Locatable;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompositeValidator implements LocatableValidator, CompositeService<LocatableValidator>
{
    private List<LocatableValidator> m_delegates = new LinkedList<>();

    public void addDelegate(LocatableValidator delegate)
    {
        m_delegates.add(checkNotNull(delegate, "delegate cannot be null"));
    }

    @Override
    public ValidationReport validate(Locatable locatable)
            throws NotSupportedException
    {
        checkNotNull(locatable, "locatable cannot be null");
        ValidationReport result = null;
        for (LocatableValidator delegate : m_delegates)
        {
            if (delegate.supports(locatable))
            {
                result = mergeReports(result, delegate.validate(locatable));
            }
        }
        if (result == null)
        {
            throw new NotSupportedException("None of the delegates support this locatable");
        }
        return result;
    }

    private ValidationReport mergeReports(ValidationReport one, ValidationReport two)
    {
        BaseValidationReport result = new BaseValidationReport();
        result.addAll(one);
        result.addAll(two);
        return result;
    }

    @Override
    public void check(Locatable locatable)
            throws ValidationException, NotSupportedException
    {
        checkNotNull(locatable, "locatable cannot be null");
        ValidationReport report = validate(locatable);
        if (!report.isValid())
        {
            throw new ValidationException(report);
        }
    }

    @Override
    public boolean supports(Locatable test)
    {
        checkNotNull(test, "locatable cannot be null");
        for (LocatableValidator delegate : m_delegates)
        {
            if (delegate.supports(test))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean supports(Archetyped test)
    {
        checkNotNull(test, "archetyped cannot be null");
        for (LocatableValidator delegate : m_delegates)
        {
            if (delegate.supports(test))
            {
                return true;
            }
        }
        return false;
    }
}
