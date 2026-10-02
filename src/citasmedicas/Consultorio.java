package citasmedicas;

import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Doctor;
import citasmedicas.persistencia.CitaRepositorio;
import citasmedicas.persistencia.DoctorRepositorio;
import citasmedicas.persistencia.PacienteRepositorio;
import java.io.IOException;
import java.util.Scanner;

/**
 * Muestra el menú principal y ejecuta cada opción usando los repositorios.
 */
public class Consultorio {

    private final DoctorRepositorio doctores = new DoctorRepositorio();
    private final PacienteRepositorio pacientes = new PacienteRepositorio();
    private final CitaRepositorio citas = new CitaRepositorio(doctores, pacientes);
    private final Scanner entrada;

    public Consultorio(Scanner entrada) throws IOException {
        this.entrada = entrada;
        // El orden importa: las citas necesitan que ya existan sus doctores y pacientes.
        doctores.cargar();
        pacientes.cargar();
        citas.cargar();
        System.out.println("Datos cargados: " + doctores.listar().size() + " doctores, "
                + pacientes.listar().size() + " pacientes y " + citas.listar().size() + " citas.");
    }

    public void mostrarMenu() {
        String opcion;
        do {
            System.out.println();
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Dar de alta doctor");
            System.out.println("2. Dar de alta paciente");
            System.out.println("3. Crear cita");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = entrada.nextLine().trim();

            // Cualquier error se muestra y el programa regresa al menú en lugar de cerrarse.
            try {
                switch (opcion) {
                    case "1":
                        altaDoctor();
                        break;
                    case "2":
                        System.out.println("Opción en desarrollo.");
                        break;
                    case "3":
                        System.out.println("Opción en desarrollo.");
                        break;
                    case "4":
                        System.out.println("Hasta luego.");
                        break;
                    default:
                        System.out.println("Opción no válida. Escribe un número del 1 al 4.");
                }
            } catch (IOException e) {
                System.out.println("Error al guardar en el archivo: " + e.getMessage());
            } catch (DatoInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (!opcion.equals("4"));
    }

    private void altaDoctor() throws IOException, DatoInvalidoException {
        System.out.println();
        System.out.println("--- Alta de doctor ---");
        String id;
        do {
            System.out.print("ID del doctor (ej. D001): ");
            id = entrada.nextLine().trim();
            if (id.isEmpty() || doctores.existe(id)) {
                System.out.println("ID no válido: está vacío o ya está registrado.");
            }
        } while (id.isEmpty() || doctores.existe(id));

        Doctor doctor = null;
        while (doctor == null) {
            System.out.print("Nombre completo: ");
            String nombre = entrada.nextLine();
            System.out.print("Especialidad: ");
            String especialidad = entrada.nextLine();
            try {
                doctor = new Doctor(id, nombre, especialidad);
            } catch (DatoInvalidoException e) {
                System.out.println("Datos incompletos: " + e.getMessage());
            }
        }

        doctores.agregar(doctor);
        System.out.println("Doctor registrado con éxito: " + doctor.mostrarInfo());
    }
}
