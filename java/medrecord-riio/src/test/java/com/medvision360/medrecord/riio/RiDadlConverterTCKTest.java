package com.medvision360.medrecord.riio;

import com.medvision360.medrecord.spi.LocatableParser;
import com.medvision360.medrecord.spi.LocatableSerializer;
import com.medvision360.medrecord.spi.tck.LocatableConverterTCKTestBase;
import org.openehr.rm.support.measurement.MeasurementService;
import org.openehr.rm.support.measurement.TestMeasurementService;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.rm.support.terminology.TestTerminologyService;

public class RiDadlConverterTCKTest extends LocatableConverterTCKTestBase
{
    @Override
    protected LocatableParser getParser() throws Exception
    {
        return new RIDadlConverter(ts, ms, encoding, lang);
    }

    @Override
    protected LocatableSerializer getSerializer() throws Exception
    {
        return new RIDadlConverter(ts, ms, encoding, lang);
    }

    // sometimes intellij doesn't want to find the superclass instances :/
    protected TerminologyService ts = TestTerminologyService.getInstance();
    protected MeasurementService ms = TestMeasurementService.getInstance();
}
