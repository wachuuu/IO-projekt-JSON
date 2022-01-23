package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class KeyDelete extends JSONModifier {

    public KeyDelete(Map<String, Object> body, String[] params) {
        super(body, params);
    }


    public String modify() {

        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < params.length; i++) {
            body.remove(params[i]);
        }
        for (Map.Entry<String, Object> entry : body.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
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