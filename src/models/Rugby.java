package models;

public class Rugby {
    private String fecha;
    private int capacidad;
    private String estadio;
    private String local;
    private String visitante;
    private int tantosLocal;
    private int tantosVisitante;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public int getTantosLocal() {
        return tantosLocal;
    }

    public void setTantosLocal(int tantosLocal) {
        this.tantosLocal = tantosLocal;
    }

    public int getTantosVisitante() {
        return tantosVisitante;
    }

    public void setTantosVisitante(int tantosVisitante) {
        this.tantosVisitante = tantosVisitante;
    }

}
