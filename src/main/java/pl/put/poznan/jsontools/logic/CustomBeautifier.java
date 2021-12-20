package pl.put.poznan.jsontools.logic;

public class CustomBeautifier extends JSONModifier {

    public CustomBeautifier(String body, String[] params) {
        super(body, params);
    }

    public String modify() {
        return body;
    }
}
