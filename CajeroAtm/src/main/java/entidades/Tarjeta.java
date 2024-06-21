package entidades;


public class Tarjeta {

    private int idTarjeta;
    private int numeroTarjeta;

    private String estado;

    private boolean monto;

    private int pin;

    private int fallo;

    //get set y conts


    public Tarjeta(int idTarjeta, int numeroTarjeta, String estado, boolean monto, int pin, int fallo) {
        this.idTarjeta = idTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.estado = estado;
        this.monto = monto;
        this.pin = pin;
        this.fallo = fallo;
    }

    public Tarjeta() {
        
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isMonto() {
        return monto;
    }

    public void setMonto(boolean monto) {
        this.monto = monto;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getFallo() {
        return fallo;
    }

    public void setFallo(int fallo) {
        this.fallo = fallo;
    }
}
