package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.Map;

public class YAMLTransformer extends JSONModifier {

    private YAMLMapper yamlMapper;

    public YAMLTransformer(Map<String, Object> body, YAMLMapper yamlMapper){
        super(body);
        this.yamlMapper = yamlMapper;
    }

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

