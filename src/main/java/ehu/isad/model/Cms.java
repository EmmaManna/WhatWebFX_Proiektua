package ehu.isad.model;

public class Cms {
    private String url;
    private String cms;
    private String version;
    private String lastUpdated;

    public Cms(String url, String cms, String version, String lastUpdated) {
        this.url = url;
        this.cms = cms;
        this.version = version;
        this.lastUpdated = lastUpdated;
    }

    public String getUrl() {
        return url;
    }

    public String getCms() {
        return cms;
    }

    public String getVersion() {
        return version;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCms(String cms) {
        this.cms = cms;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
