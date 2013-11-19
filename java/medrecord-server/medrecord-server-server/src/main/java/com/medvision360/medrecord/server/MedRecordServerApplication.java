/**
 * This file is part of MEDvision360 Profile Server..
 *
 * @copyright Copyright 2013 by MEDvision360. All rights reserved.
 * @author Ralph van Etten <ralph@medvision360.com>
 */
package com.medvision360.medrecord.server;

import com.medvision360.lib.server.service.JsonStatusService;
import com.medvision360.medrecord.engine.MedRecordEngine;
import com.medvision360.medrecord.server.resources.ArchetypeServerResource;
import com.medvision360.medrecord.server.resources.DemoServerResource;
import com.medvision360.medrecord.spi.Engine;
import com.medvision360.medrecord.spi.exceptions.InitializationException;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.resource.Directory;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;
import org.restlet.service.TunnelService;

import com.medvision360.lib.server.RestletApplication;
import com.medvision360.lib.server.config.ConfigurationException;
import com.medvision360.lib.server.config.ConfigurationWrapper;

/**
 * The application for the Identity Server.
 */
public class MedRecordServerApplication extends RestletApplication
{
    public MedRecordServerApplication()
    {
        // add a custom status service which understands the exception conventions used in medrecord
        setStatusService(new CustomStatusService());
        
        // need to enable preferences and extensions tunnel to be able
        // to use document.en.json

        setTunnelService(
            new TunnelService(
                true,   // enabled
                false,  // method tunnel
                true,   // preferences tunnel
                false,  // query tunnel
                true,   // extensions tunnel
                false,  // user agent tunnel
                false   // headers tunnel
            )
        );
    }

    /**
     * Initialize the application.
     */
    @Override
    public void init(final ConfigurationWrapper config)
        throws ConfigurationException
    {
        enableJackson();

        // this is a good place to load configuration and store it in the context so it can be used by the various
        // resources....
        
        MedRecordService service = new MedRecordService();
        getServices().add(service);

        /*
        IdserverConfig.storeInContext(
            config,
            getContext(),
            IdserverConfig.class
        );

        LoginServerResourceConfiguration.storeInContext(
            config,
            getContext(),
            LoginServerResourceConfiguration.class
        );

        ApiKeyAuthenticatorConfiguration.storeInContext(
            config,
            getContext(),
            ApiKeyAuthenticatorConfiguration.class
        );


        Database.storeInContext(getContext(), config);
        */
    }

    /**
     * Create all resources.
     */
    @Override
    public Restlet makeInboundRoot()
    {
        //
        // root (unsecured)
        //

        final Router root = new Router(getContext());

        root.attach("/apidocs", new Directory(getContext(), "war:///apidocs"));

        root.attach(
                "/demo",
                DemoServerResource.class
        );
        
        root.attach(
                "/archetype",
                new TransactionFilter(
                        getContext(),
                        ArchetypeServerResource.class
                )
        );

        return root;
    }
}
