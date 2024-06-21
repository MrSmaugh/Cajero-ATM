package entidades;

import java.sql.Timestamp;

public class Operacion {
    private int id_operacion;
    private int id_tarjeta;
    private String tipo_operacion;
    private Timestamp fecha_hora;
    private Boolean monto;

    public int getId_operacion() {
        return id_operacion;
    }

    public void setId_operacion(int id_operacion) {
        this.id_operacion = id_operacion;
    }

    public int getId_tarjeta() {
        return id_tarjeta;
    }

    public void setId_tarjeta(int id_tarjeta) {
        this.id_tarjeta = id_tarjeta;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Boolean getMonto() {
        return monto;
    }

    public void setMonto(Boolean monto) {
        this.monto = monto;
    }
}
