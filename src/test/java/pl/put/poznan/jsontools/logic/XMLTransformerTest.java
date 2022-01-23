package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class XMLTransformerTest {

    private Map<String, Object> json;

    @BeforeEach
    void setUp() {
        this.json = new HashMap<>();
    }

    @Test
    void modifyEmptyJson() throws JsonProcessingException {
        XmlMapper xmlMapper = mock(XmlMapper.class);
        when(xmlMapper.writeValueAsString(this.json)).thenReturn("<?xml version='1.1' encoding='UTF-8'?>\n"
                + "<LinkedHashMap/>");

        XMLTransformer xmlTransformer = new XMLTransformer(this.json, xmlMapper);

        String xml = xmlTransformer.modify();
        assertEquals("<?xml version='1.1' encoding='UTF-8'?>\n<LinkedHashMap/>", xml);
    }

    @Test
    void modifyNestedJson() throws JsonProcessingException {
        Map<String, Object> subObj = new HashMap<>();
        subObj.put("name2", "value2");
        this.json.put("name1", subObj);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

        XMLTransformer xmlTransformer = new XMLTransformer(this.json, xmlMapper);
        String xml = xmlTransformer.modify();
        String expected = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                "<HashMap>\n" +
                "  <name1>\n" +
                "    <name2>value2</name2>\n" +
                "  </name1>\n" +
                "</HashMap>\n";

        assertEquals(expected, xml);
    }

    @Test
    void modifyOnePropertyJson() throws JsonProcessingException {
        this.json.put("text", "xxxx");

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

        XMLTransformer xmlTransformer = new XMLTransformer(this.json, xmlMapper);
        String xml = xmlTransformer.modify();
        String expected = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                "<HashMap>\n" +
                "  <text>xxxx</text>\n" +
                "</HashMap>\n";

        assertEquals(expected, xml);
    }

    @Test
    void modifyManyPropertiesJson() throws JsonProcessingException {
        this.json.put("text", "xxxx");
        this.json.put("text2", "xxxx");
        this.json.put("text3", "xxxx");

        String e = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                "<HashMap>\n" +
                "  <text>xxxx</text>\n" +
                "  <text2>xxxx</text2>\n" +
                "  <text3>xxxx</text3>\n" +
                "</HashMap>";

        XmlMapper xmlMapper = mock(XmlMapper.class);
        when(xmlMapper.writeValueAsString(this.json)).thenReturn(e);

        XMLTransformer xmlTransformer = new XMLTransformer(this.json, xmlMapper);

        String xml = xmlTransformer.modify();
        assertEquals(e, xml);
    }

    @Test
    void modifyManyPropertiesNestedJson() throws JsonProcessingException {
        Map<String, Object> subObj = new HashMap<>();
        subObj.put("text3", "value3");
        this.json.put("text1", "value1");
        this.json.put("text2", subObj);

        String e = "<?xml version='1.1' encoding='UTF-8'?>\n" +
                "<HashMap>\n" +
                "  <text1>value1</text1>\n" +
                "  <text2>\n" +
                "    <text3>value3</text3>\n" +
                "  </text2>\n" +
                "</HashMap>";

        XmlMapper xmlMapper = mock(XmlMapper.class);
        when(xmlMapper.writeValueAsString(this.json)).thenReturn(e);

        XMLTransformer xmlTransformer = new XMLTransformer(this.json, xmlMapper);

        String xml = xmlTransformer.modify();
        assertEquals(e, xml);
    }
}