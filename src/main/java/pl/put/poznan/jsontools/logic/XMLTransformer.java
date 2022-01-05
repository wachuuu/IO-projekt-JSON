package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.util.Map;

public class XMLTransformer extends JSONModifier {

    public XMLTransformer(Map<String, Object> body) {
        super(body);
    }

    public String modify() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

        String xml = null;
        try{
            xml = xmlMapper.writeValueAsString(body);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return xml;
    }
}
