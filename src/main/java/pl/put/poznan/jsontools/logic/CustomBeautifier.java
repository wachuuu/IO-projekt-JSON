package pl.put.poznan.jsontools.logic;

import java.util.Map;

public class CustomBeautifier extends JSONModifier {

    public CustomBeautifier(Map<String, Object> body, String[] params) {
        super(body, params);
    }

    public String modify() {
        return "sample";
    }
}
