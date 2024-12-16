public class Moneda {
    private String nombre;
    private double tasaCambio;
    private String paisOrigen;

    public Moneda(String nombre, String paisOrigen,double tasaCambio) {
        this.nombre = nombre;
        this.tasaCambio = tasaCambio;
        this.paisOrigen=paisOrigen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
}
