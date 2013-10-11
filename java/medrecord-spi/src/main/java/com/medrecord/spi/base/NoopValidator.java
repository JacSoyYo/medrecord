package com.medrecord.spi.base;

import com.medrecord.spi.LocatableValidator;
import com.medrecord.spi.ValidationReport;
import com.medrecord.spi.exceptions.NotSupportedException;
import com.medrecord.spi.exceptions.ValidationException;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.common.archetyped.Locatable;

public class NoopValidator implements LocatableValidator {
    @Override
    public ValidationReport validate(Locatable locatable)
            throws NotSupportedException {
        throw new UnsupportedOperationException("todo implement NoopValidator.validate()");
    }

    @Override
    public void check(Locatable locatable)
            throws ValidationException, NotSupportedException {
        throw new UnsupportedOperationException("todo implement NoopValidator.check()");
    }

    @Override
    public boolean supports(Locatable locatable) {
        throw new UnsupportedOperationException("todo implement NoopValidator.supports()");
    }

    @Override
    public boolean supports(Archetyped archetyped) {
        throw new UnsupportedOperationException("todo implement NoopValidator.supports()");
    }
}
