package com.medvision360.medrecord.spi.tck;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.medvision360.medrecord.spi.LocatableParser;
import com.medvision360.medrecord.spi.LocatableSerializer;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.support.identification.HierObjectID;

public abstract class LocatableConverterTCKTestBase extends RMTestBase
{
    protected LocatableParser m_parser;
    protected LocatableSerializer m_serializer;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        m_parser = getParser();
        m_serializer = getSerializer();
    }
    
    public void testFormat() throws Exception
    {
        String parserFormat = m_parser.getFormat();
        String parserMimeType = m_parser.getMimeType();
        String serializerFormat = m_serializer.getFormat();
        String serializerMimeType = m_serializer.getMimeType();
        
        assertNotNull(parserFormat);
        assertNotNull(parserMimeType);
        assertNotNull(serializerFormat);
        assertNotNull(serializerMimeType);
        assertEquals(parserFormat, serializerFormat);
        assertEquals(parserMimeType, serializerMimeType);
    }

    public void testSupport() throws Exception
    {
        HierObjectID uid = new HierObjectID(makeUUID());
        Locatable locatable = makeLocatable(uid, m_parent);
        assertTrue(m_parser.supports(locatable));
        assertTrue(m_parser.supports(locatable.getArchetypeDetails()));
        assertTrue(m_serializer.supports(locatable));
        assertTrue(m_serializer.supports(locatable.getArchetypeDetails()));
    }
    
    public void testRoundTrip() throws Exception
    {
        HierObjectID uid = new HierObjectID(makeUUID());
        Locatable orig = makeLocatable(uid, m_parent);
        Locatable result;
        ByteArrayOutputStream os;
        ByteArrayInputStream is;
        byte[] data;
        
        // pass one: start from constructed object

        os = new ByteArrayOutputStream();
        m_serializer.serialize(orig, os);
        data = os.toByteArray();
        assertTrue(data.length > 0);
        System.out.println("Serialized:\n-------");
        System.out.println(new String(data, "UTF-8"));
        
        is = new ByteArrayInputStream(data);
        result = m_parser.parse(is);
        assertEqualish(orig, result);
        
        // pass two: start from parsed object
        
        os = new ByteArrayOutputStream();
        m_serializer.serialize(result, os);
        data = os.toByteArray();
        assertTrue(data.length > 0);
        System.out.println("Serialized:\n-----------");
        System.out.println(new String(data, "UTF-8"));
        
        is = new ByteArrayInputStream(data);
        result = m_parser.parse(is);
        assertEqualish(orig, result);
        
        // pass three: set encoding

        os = new ByteArrayOutputStream();
        m_serializer.serialize(result, os, "UTF-8");
        data = os.toByteArray();
        assertTrue(data.length > 0);
        
        is = new ByteArrayInputStream(data);
        result = m_parser.parse(is, "UTF-8");
        assertEqualish(orig, result);
    }
    
    protected abstract LocatableParser getParser() throws Exception;
    
    protected abstract LocatableSerializer getSerializer() throws Exception;
}
