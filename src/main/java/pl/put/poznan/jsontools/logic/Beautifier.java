package pl.put.poznan.jsontools.logic;

public class Beautifier extends JSONModifier {
    public Beautifier(String body) {
        super(body);
    }

    public String modify() {
        return body;
    }
}
