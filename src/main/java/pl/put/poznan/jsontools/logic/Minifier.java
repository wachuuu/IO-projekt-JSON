package pl.put.poznan.jsontools.logic;

public class Minifier extends JSONModifier{
    public Minifier(String body) {
        super(body);
    }

    public String modify() {
        return body;
    }
}
