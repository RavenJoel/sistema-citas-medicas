package citasmedicas;

import citasmedicas.modelo.Administrador;
import citasmedicas.persistencia.AdministradorRepositorio;
import java.io.IOException;
import java.util.Scanner;

/**
 * Pide el ID y la contraseña del administrador. Permite como máximo tres intentos.
 */
public class ControlAcceso {

    private static final int MAX_INTENTOS = 3;

    private final AdministradorRepositorio administradores = new AdministradorRepositorio();
    private int intentos = 0;

    public ControlAcceso() throws IOException {
        administradores.cargar();
    }

    public boolean iniciarSesion(Scanner entrada) {
        System.out.println();
        System.out.println("===== INICIO DE SESIÓN =====");
        while (intentos < MAX_INTENTOS) {
            System.out.print("ID de administrador: ");
            String id = entrada.nextLine().trim();
            System.out.print("Contraseña: ");
            String contrasena = entrada.nextLine();

            Administrador admin = administradores.buscarPorId(id);
            if (admin != null && admin.validarContrasena(contrasena)) {
                System.out.println("Bienvenido(a), " + admin.getNombreCompleto() + ".");
                return true;
            }
            intentos++;
            System.out.println("Acceso denegado. Intentos restantes: " + (MAX_INTENTOS - intentos));
        }
        return false;
    }
}
