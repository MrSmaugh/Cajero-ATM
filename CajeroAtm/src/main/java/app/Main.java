package app;


import dao.Conexion;
import dao.OperacionDAO;
import dao.TarjetaDAO;
import entidades.Tarjeta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final int MAX_INTENTOS_PIN = 4;

    private static Connection conectarBaseDeDatos() {
        // Reemplace con su configuración de base de datos
        String URL = "jdbc:sqlserver://localhost:1434;database=Atm";
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Conexión a la base de datos
        Connection conexion = conectarBaseDeDatos();
        if (conexion == null) {
            System.out.println("Error al conectar a la base de datos.");
            return;
        }

        TarjetaDAO tarjetaDAO = new TarjetaDAO(conexion);
        OperacionDAO operacionDAO = new OperacionDAO(conexion);

        // Login con número de tarjeta
        String numero;
        do {
            System.out.print("Ingrese número de tarjeta: ");
            numero = scanner.nextLine();
        } while (!validarNumeroTarjeta(numero));

        // Login con PIN
        int intentosPIN = 0;
        int pin;
        do {
            System.out.print("Ingrese PIN: ");
            pin = scanner.nextInt();
            intentosPIN++;

            try {
                tarjetaDAO.autenticarTarjeta(numero, pin);
                break;
            } catch (IllegalArgumentException | SQLException e) {
                System.out.println("Error: " + e.getMessage());
                if (intentosPIN == MAX_INTENTOS_PIN) {
                    System.out.println("Tarjeta bloqueada. Reintente más tarde.");
                    return;
                }
            }
        } while (true);

        // Menú principal
        int opcion;
        do {
            System.out.println("\nMenú principal:");
            System.out.println("1. Consultar balance");
            System.out.println("2. Realizar retiro");
            System.out.println("3. Salir");
            System.out.print("Ingrese opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    consultarBalance(numero, operacionDAO);
                    break;
                case 2:
                    realizarRetiro(numero, operacionDAO, scanner);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (true);
    }



    private static boolean validarNumeroTarjeta(String numeroTarjeta) {
        // Implementar la lógica de validación de número de tarjeta (ver código anterior)
        if (numeroTarjeta.length() < 13 || numeroTarjeta.length() > 19) {
            return false;
        }

        for (int i = 0; i < numeroTarjeta.length(); i++) {
            char caracter = numeroTarjeta.charAt(i);
            if (i % 5 == 0 && caracter != '-') {
                return false;
            } else if (i % 5 != 0 && !Character.isDigit(caracter)) {
                return false;
            }
        }

        return true;
    }

    private static void consultarBalance(String numeroTarjeta, OperacionDAO operacionDAO) {
        Connection conexion = conectarBaseDeDatos();
        if (conexion == null) {
            System.out.println("Error al conectar a la base de datos.");
            return;
        }
        try {
            // Obtener la tarjeta para conocer su id
            TarjetaDAO tarjetaDAO = new TarjetaDAO(conexion);
            Tarjeta tarjeta = tarjetaDAO.obtenerTarjetaPorNumero(numeroTarjeta);
            if (tarjeta != null) {
                operacionDAO.consultarBalance(tarjeta.getIdTarjeta());
            } else {
                System.out.println("Tarjeta no encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void realizarRetiro(String numeroTarjeta, OperacionDAO operacionDAO, Scanner scanner) {
        Connection conexion = conectarBaseDeDatos();
        if (conexion == null) {
            System.out.println("Error al conectar a la base de datos.");
            return;
        }
        try {
            // Obtener la tarjeta para conocer su id
            TarjetaDAO tarjetaDAO = new TarjetaDAO(conexion);
            Tarjeta tarjeta = tarjetaDAO.obtenerTarjetaPorNumero(numeroTarjeta);
            if (tarjeta != null) {
                operacionDAO.realizarRetiro(tarjeta.getIdTarjeta());
            } else {
                System.out.println("Tarjeta no encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}