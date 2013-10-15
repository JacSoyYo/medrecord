/**
 * This file is part of MEDrecord
 *
 * @copyright Copyright 2013 by MEDvision360. All rights reserved.
 * @author Leo Simons <leo@medvision360.com>
 * @author Ralph van Etten <ralph@medvision360.com>
 */
package com.medvision360.medrecord.spi.exceptions;

import com.medvision360.medrecord.spi.ValidationReport;

@SuppressWarnings("UnusedDeclaration")
public class ValidationException extends RecordException {
    private static final long serialVersionUID = 0x130L;
    
    private ValidationReport report;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ValidationException(ValidationReport report) {
        this.report = report;
    }

    public ValidationException(String message, ValidationReport report) {
        super(message);
        this.report = report;
    }

    public ValidationException(String message, Throwable cause, ValidationReport report) {
        super(message, cause);
        this.report = report;
    }

    public ValidationException(Throwable cause, ValidationReport report) {
        super(cause);
        this.report = report;
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                               ValidationReport report) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.report = report;
    }

    public ValidationReport getReport() {
        return report;
    }
}
