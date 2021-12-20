package pl.put.poznan.jsontools.logic;

import java.util.Map;

public class Minifier extends JSONModifier{
    public Minifier(Map<String, Object> body) {
        super(body);
    }

    public String modify() {
        return "sample";
    }
}
