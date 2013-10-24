package com.medvision360.medrecord.pv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.medvision360.medrecord.memstore.MemArchetypeStore;
import com.medvision360.medrecord.spi.ArchetypeStore;
import com.medvision360.medrecord.spi.LocatableParser;
import com.medvision360.medrecord.spi.LocatableSerializer;
import com.medvision360.medrecord.spi.tck.LocatableConverterTCKTestBase;
import org.openehr.am.archetype.Archetype;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.support.measurement.TestMeasurementService;
import org.openehr.rm.support.terminology.TestTerminologyService;

public class PVConverterTest extends LocatableConverterTCKTestBase
{
    protected ArchetypeStore m_archetypeStore;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        m_archetypeStore = new MemArchetypeStore();
        m_archetypeStore.initialize();
        Archetype archetype = loadArchetype();
        m_archetypeStore.insert(archetype);
    }

    @Override
    protected LocatableParser getParser() throws Exception
    {
        return new PVParser(new TestTerminologyService(), new TestMeasurementService(), encoding, lang, territory());
    }

    @Override
    protected LocatableSerializer getSerializer() throws Exception
    {
        return new PVSerializer();
    }
    
    public void testFillingInOptionalValues() throws Exception
    {
        String[] sample = {
          "/archetype_node_id" , "at0001",
          "/rm_entity" , "Composition",
          "/archetype_details/archetype_id/value" , "unittest-EHR-COMPOSITION.composition.v1",
          "/content/collection_type" , "LIST",
          "/content[at0002][1]/rm_entity" , "AdminEntry",
          "/content[at0002][1]/archetype_details/archetype_id/value" , "unittest-EHR-ADMIN_ENTRY.date.v2",
          "/content[at0002][1]/data[at0003]/items/collection_type" , "LIST",
          "/content[at0002][1]/data[at0003]/items[at0004][1]/rm_entity" , "Element",
          "/content[at0002][1]/data[at0003]/items[at0004][1]/name/value" , "header",
          "/content[at0002][1]/data[at0003]/items[at0004][1]/value/value" , "date",
          "/content[at0002][1]/data[at0003]/items[at0005][2]/rm_entity" , "Element",
          "/content[at0002][1]/data[at0003]/items[at0005][2]/name/value" , "value",
          "/content[at0002][1]/data[at0003]/items[at0005][2]/value/value" , "2008-05-17",
        };

        String json = toJSON(sample);
        System.out.println(json);
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes("UTF-8"));
        Locatable locatable = m_parser.parse(is);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        m_serializer.serialize(locatable, os);
        String result = os.toString("UTF-8");
        System.out.println(result);
        assertTrue(result.contains("/category"));
        assertTrue(result.contains("/language"));
        assertTrue(result.contains("/territory"));
        assertTrue(result.contains("/name"));
        assertTrue(result.contains("/rm_version"));
        assertTrue(result.contains("/composer"));
        assertTrue(result.contains("/subject"));
        assertTrue(result.contains("DvDate"));
        assertTrue(result.contains("DvText"));
    }

    private String toJSON(String[] pv)
    {
        StringBuilder b = new StringBuilder();
        b.append("{\n");
        for (int i = 0; i < pv.length; i++)
        {
            String path = pv[i++];
            String value = pv[i];
            b.append("  \"");
            b.append(path);
            b.append("\" : \"");
            b.append(value);
            b.append("\",\n");
        }
        b.deleteCharAt(b.length()-2);
        b.append("}");
        return b.toString();
    }
}
