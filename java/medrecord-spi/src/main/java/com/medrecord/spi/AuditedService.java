package com.medrecord.spi;

/**
 * Service interface implemented by services that support audit logging. Clients SHOULD call
 * {@link #setAuditInfo(AuditInfo)} before doing any other operations on services that implement this interface. 
 * Services that implement this interface will then use the provided audit info to write audit information when 
 * their other methods are called.
 * 
 * For services that also implement {@link TransactionalService}, <code>setAuditInfo()</code> SHOULD be called 
 * directly after each invocation of {@link TransactionalService#begin()} to set up the audit info for that 
 * particular transaction. The provided audit info will then apply only up to and including the next {@link 
 * TransactionalService#commit()} or {@link com.medrecord.spi.TransactionalService#rollback()}.
 */
public interface AuditedService {

    /**
     * Specify the audit parameters to use for other API calls that follow.
     * 
     * @param auditInfo the audit parameters to use from now on.
     * @throws NullPointerException if any of the provided arguments are null.
     * @throws IllegalArgumentException if the provided audit info is not allowed for this service.
     */
    public void setAuditInfo(AuditInfo auditInfo);
}
