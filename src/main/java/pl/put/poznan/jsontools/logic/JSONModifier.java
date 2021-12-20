package pl.put.poznan.jsontools.logic;

public abstract class JSONModifier implements JSONTool {
    String[] params;
    String body;

    public abstract String modify();

    JSONModifier(String body) {
        this.body = body;
    }

    JSONModifier(String body, String[] params) {
        this.params = params;
        this.body = body;
    }
}
