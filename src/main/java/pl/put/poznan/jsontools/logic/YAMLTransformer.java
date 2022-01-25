package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.Map;

/** YALTransformer takes a JSON file and returns a string in an YAML format.
 *
 */

public class YAMLTransformer extends JSONModifier {

    private YAMLMapper yamlMapper;

    public YAMLTransformer(Map<String, Object> body, YAMLMapper yamlMapper){
        super(body);
        this.yamlMapper = yamlMapper;
    }

    /**
     * modify takes a JSON body and returns a string in an YAML format
     *
     * @return String xml - string in YAML format
     */

    public String modify() {
        String yaml = null;
        try {
            yaml = yamlMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return yaml;
    }
}

