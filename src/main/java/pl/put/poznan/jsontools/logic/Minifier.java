package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Minifier extends JSONModifier {
    public Minifier(Map<String, Object> body) {
        super(body);
    }

    public String modify() {

        ObjectMapper mapper = new ObjectMapper();

        String minified = null;

        try {
            minified = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return minified;
    }
}
