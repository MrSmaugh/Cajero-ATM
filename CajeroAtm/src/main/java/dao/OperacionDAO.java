package dao;

import java.sql.*;
import java.util.Scanner;

public class OperacionDAO {
    private Connection conexion;

    public OperacionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void consultarBalance(int idTarjeta) throws SQLException {
        String sql = "SELECT numerosTarjetas, montos FROM tarjeta WHERE id_tarjetas = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idTarjeta);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String numeroTarjeta = resultSet.getString("numero");
                Double montoCuenta = resultSet.getDouble("monto");

                System.out.println("\nConsulta de balance:");
                System.out.println("Número de tarjeta: " + numeroTarjeta);
                System.out.println("Monto disponible: $" + montoCuenta);

                // Menú de opciones (atras, salir)
                // ...
            } else {
                System.out.println("Error: No se encontró información para la tarjeta con ID: " + idTarjeta);
            }
        }
    }

    public void realizarRetiro(int idTarjeta) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        double montoRetiro;
        do {
            System.out.print("Ingrese monto a retirar: $");
            montoRetiro = scanner.nextDouble();

            if (montoRetiro <= 0) {
                System.out.println("Error: El monto a retirar debe ser mayor a $0.");
            }
        } while (montoRetiro <= 0);

        // Consultar saldo actual
        double saldoActual = consultarSaldo(idTarjeta);

        if (montoRetiro > saldoActual) {
            System.out.println("Error: El monto a retirar supera el saldo disponible.");
        } else {
            // Actualizar saldo en la base de datos
            actualizarSaldo(idTarjeta, saldoActual - montoRetiro);

            // Guardar operación de retiro
            guardarOperacion(idTarjeta, "RETIRO", montoRetiro);

            System.out.println("\nRetiro exitoso.");
            System.out.println("Nuevo saldo disponible: $" + (saldoActual - montoRetiro));

            // Menú de opciones (aceptar, salir)
            // ...
        }
    }

    private double consultarSaldo(int idTarjeta) throws SQLException {
        String sql = "SELECT montos FROM tarjeta WHERE id_tarjetas = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idTarjeta);
        }
        return 0;
    }
    private void actualizarSaldo(int idTarjeta, double nuevoSaldo) throws SQLException {
        String sql = "UPDATE tarjeta SET montos = ? WHERE id_tarjetas = ?";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setDouble(1, nuevoSaldo);
            statement.setInt(2, idTarjeta);
            statement.executeUpdate();
        }

    }
    private void guardarOperacion(int idTarjeta, String tipoOperacion, double monto) throws SQLException {
        Timestamp fechaHoraActual = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO operacion (id_tarjeta, tipo_operacion, fecha_hora, monto) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idTarjeta);
            statement.setString(2, tipoOperacion);
            statement.setTimestamp(3, fechaHoraActual);
            statement.setDouble(4, monto);
            statement.executeUpdate();
        }
    }
}