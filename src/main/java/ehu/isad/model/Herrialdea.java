package ehu.isad.model;

public class Herrialdea {
    private String module;
    private String string;

    public Herrialdea(String module, String string) {
        this.module = module;
        this.string = string;
    }

    public String getModule() {
        return module;
    }

    public String getString() {
        return string;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
