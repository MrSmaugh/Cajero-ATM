package dao;

import entidades.Tarjeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TarjetaDAO extends Conexion{
    private Connection conexion;

    public TarjetaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void autenticarTarjeta(String numero, int pin) throws SQLException {
        // Validar formato de número de tarjeta
        if (!validarNumeroTarjeta(numero)) {
            throw new IllegalArgumentException("Número de tarjeta inválido.");
        }

        // Validar existencia de tarjeta en base de datos
        Tarjeta tarjeta = obtenerTarjetaPorNumero(numero);
        if (tarjeta == null) {
            throw new IllegalArgumentException("Tarjeta inexistente.");
        }

        // Validar estado de la tarjeta
        if (tarjeta.getEstado().equals("BLOQUEADA")) {
            throw new IllegalArgumentException("Tarjeta bloqueada.");
        }

        // Validar PIN
        if (tarjeta.getPin() != pin) {
            throw new IllegalArgumentException("PIN incorrecto.");
        }

        // Autenticación exitosa - Invocar clase Test
       // System.out.println("Autenticación exitosa.");
       // Test test = new Test();
       // test.ejecutarTest();
    }

    private boolean validarNumeroTarjeta(String numero) {
        // Validar longitud del número de tarjeta
        if (numero.length() < 13 || numero.length() > 19) {
            return false;
        }

        // Validar formato del número de tarjeta
        for (int i = 0; i < numero.length(); i++) {
            char caracter = numero.charAt(i);
            if (i % 5 == 0 && caracter != '-') {
                return false;
            } else if (i % 5 != 0 && !Character.isDigit(caracter)) {
                return false;
            }
        }

        return true;
    }

    public Tarjeta obtenerTarjetaPorNumero(String numeroTarjeta) throws SQLException {
        String sql = "SELECT * FROM tarjeta WHERE numerosTarjetas = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, numeroTarjeta);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setIdTarjeta(resultSet.getInt("id"));
                tarjeta.setNumeroTarjeta(resultSet.getInt("numero"));
                tarjeta.setEstado(resultSet.getString("estado"));
                tarjeta.setMonto(resultSet.getBoolean("monto"));
                tarjeta.setPin(resultSet.getInt("pin"));
                tarjeta.setFallo(resultSet.getInt("fallo"));
                return tarjeta;
            } else {
                return null;
            }
        }
    }
}
