package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Beautifier takes a JSON file and returns a string in a beautified, easier to read format with proper indentation and whitespaces
 *
 */

public class Beautifier extends JSONModifier {
    public Beautifier(Map<String, Object> body) {
        super(body);
    }

    /**
     * modify takes a JSON body and returns a beautified version of it
     *
     * @return String beautified - a prettier, more readable text version of the JSON with
     *                             proper whitespaces and indentation
     */

    public String modify() {

        ObjectMapper mapper = new ObjectMapper();

        String beautified = null;
        try {
            beautified = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return beautified;
    }
}
