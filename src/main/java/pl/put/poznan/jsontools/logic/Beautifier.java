package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Map;

/**
 * Beautifier takes a JSON file and returns a string in a beautified, easier to read format with proper indentation and whitespaces
 *
 */

public class Beautifier extends JSONModifier {
    private ObjectWriter writer;

    public Beautifier(Map<String, Object> body) {
        super(body);
        writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    public Beautifier(Map<String, Object> body, ObjectWriter w) {
        super(body);
        writer = w;
    }

    /**
     * modify takes a JSON body and returns a beautified version of it
     *
     * @return String beautified - a prettier, more readable text version of the JSON with
     *                             proper whitespaces and indentation
     */

    public String modify() {

        String beautified = null;
        try {
            beautified = writer.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return beautified;
    }
}
