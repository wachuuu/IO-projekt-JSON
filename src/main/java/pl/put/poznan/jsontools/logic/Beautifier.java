package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Beautifier extends JSONModifier {
    public Beautifier(Map<String, Object> body) {
        super(body);
    }

    public String modify() {
        //System.out.println(body.toString());
        ObjectMapper mapper = new ObjectMapper();

        String beautified = null;
        try {
            beautified = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //String beautified = "aaa";
        return beautified;
    }
}
