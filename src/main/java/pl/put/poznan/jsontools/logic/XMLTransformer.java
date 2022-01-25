package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.util.Map;

/** XMLTransformer takes a JSON file and returns a string in an XML format.
 *
 */

public class XMLTransformer extends JSONModifier {

    private XmlMapper xmlMapper;

    public XMLTransformer(Map<String, Object> body, XmlMapper xmlMapper) {
        super(body);
        this.xmlMapper = xmlMapper;
    }

    /**
     * modify takes a JSON body and returns a string in an XML format
     *
     * @return String xml - string in XML format
     */

    public String modify() {
        String xml = null;
        try{
            xml = xmlMapper.writeValueAsString(body);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return xml;
    }
}
