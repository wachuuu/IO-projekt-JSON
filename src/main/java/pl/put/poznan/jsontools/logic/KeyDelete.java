package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * KeyDelete deletes values from a JSON file whose keys are in the params array
 *
 */

public class KeyDelete extends JSONModifier {

    public KeyDelete(Map<String, Object> body, String[] params) {
        super(body, params);
    }

    /**
     * modify takes a JSON body from the constructor and removes values that
     * match any values from params String
     *
     * @return String transformed - string that has values matched with params deleted
     */

    public String modify() {

        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < params.length; i++) {
            body.remove(params[i]);
        }
        String transformed = null;
        try {
            transformed= mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return transformed;
    }
}
