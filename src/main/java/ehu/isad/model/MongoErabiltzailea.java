package ehu.isad.model;

public class MongoErabiltzailea {
    private static MongoErabiltzailea erabiltzailea=new MongoErabiltzailea();

    private String izena="";
    private String pasahitza="";
    private String collection="";

    private MongoErabiltzailea(){
    }

    public static MongoErabiltzailea getInstance(){
        return erabiltzailea;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
