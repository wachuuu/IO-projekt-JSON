package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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

        String xml = xmlMapper.writeValueAsString(this.json);
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

        String xml = xmlMapper.writeValueAsString(this.json);
        String expected = "<?xml version='1.1' encoding='UTF-8'?>\r\n" +
                "<HashMap>\r\n" +
                "  <name1>\r\n" +
                "    <name2>value2</name2>\r\n" +
                "  </name1>\r\n" +
                "</HashMap>\r\n";

        assertEquals(expected, xml);
    }

    @Test
    void modifyJson() throws JsonProcessingException {
        this.json.put("text", "xxxx");

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

        String xml = xmlMapper.writeValueAsString(this.json);
        String expected = "<?xml version='1.1' encoding='UTF-8'?>\r\n" +
                "<HashMap>\r\n" +
                "  <text>xxxx</text>\r\n" +
                "</HashMap>\r\n";

        assertEquals(expected, xml);
    }

    @Test
    void modifyPlainText() throws JsonProcessingException {
        String plainText = "this is some text";

        XmlMapper xmlMapper = mock(XmlMapper.class);
        when(xmlMapper.writeValueAsString(anyString())).thenAnswer(
            new Answer() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    return "<String>"+args[0].toString()+"</String>";
            }});

        String expected = "<String>this is some text</String>";

        String xml = xmlMapper.writeValueAsString(plainText);
        assertEquals(expected, xml);
    }

    @Test
    void modifyJsonAsString() throws JsonProcessingException {
        this.json.put("name", "value");

        String jsonAsString = this.json.toString();

        XmlMapper xmlMapper = mock(XmlMapper.class);
        when(xmlMapper.writeValueAsString(anyString())).thenAnswer(
                new Answer() {
                    public Object answer(InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        return "<String>"+args[0].toString()+"</String>";
                    }});

        String expected = "<String>{name=value}</String>";

        String xml = xmlMapper.writeValueAsString(jsonAsString);
        assertEquals(expected, xml);
    }
}