package pl.put.poznan.jsontools.logic;

import java.util.Map;

public abstract class JSONModifier implements JSONTool {
    String[] params;
    Map<String, Object> body;

    public abstract String modify();

    JSONModifier(Map<String, Object> body) {
        this.body = body;
    }

    JSONModifier(Map<String, Object> body, String[] params) {
        this.params = params;
        this.body = body;
    }
}
