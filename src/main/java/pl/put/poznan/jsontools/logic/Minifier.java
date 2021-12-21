package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Minifier takes a JSON file and returns a string in a minimized format - without any whitespaces.
 *
 */

public class Minifier extends JSONModifier {

    public Minifier(Map<String, Object> body) {
        super(body);
    }


    /**
     * modify takes a JSON body and returns a whitespace-less version of it
     *
     * @return String minified - a string without whitespaces
     */

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
