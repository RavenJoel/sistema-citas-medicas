package citasmedicas;

import citasmedicas.modelo.Cita;
import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Doctor;
import citasmedicas.modelo.Paciente;
import citasmedicas.persistencia.CitaRepositorio;
import citasmedicas.persistencia.DoctorRepositorio;
import citasmedicas.persistencia.PacienteRepositorio;
import java.io.IOException;
import java.time.LocalDateTime;
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
                        altaPaciente();
                        break;
                    case "3":
                        crearCita();
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

    private void altaPaciente() throws IOException, DatoInvalidoException {
        System.out.println();
        System.out.println("--- Alta de paciente ---");
        String id;
        do {
            System.out.print("ID del paciente (ej. P001): ");
            id = entrada.nextLine().trim();
            if (id.isEmpty() || pacientes.existe(id)) {
                System.out.println("ID no válido: está vacío o ya está registrado.");
            }
        } while (id.isEmpty() || pacientes.existe(id));

        Paciente paciente = null;
        while (paciente == null) {
            System.out.print("Nombre completo: ");
            String nombre = entrada.nextLine();
            try {
                paciente = new Paciente(id, nombre);
            } catch (DatoInvalidoException e) {
                System.out.println("Datos incompletos: " + e.getMessage());
            }
        }

        pacientes.agregar(paciente);
        System.out.println("Paciente registrado con éxito: " + paciente.mostrarInfo());
    }

    private void crearCita() throws IOException, DatoInvalidoException {
        System.out.println();
        System.out.println("--- Crear cita ---");
        if (doctores.listar().isEmpty() || pacientes.listar().isEmpty()) {
            System.out.println("Primero registre doctores y pacientes.");
            return;
        }

        String id;
        do {
            System.out.print("ID de la cita (ej. C001): ");
            id = entrada.nextLine().trim();
            if (id.isEmpty() || citas.existe(id)) {
                System.out.println("ID no válido: está vacío o ya está registrado.");
            }
        } while (id.isEmpty() || citas.existe(id));

        LocalDateTime fechaHora = null;
        while (fechaHora == null) {
            System.out.print("Fecha y hora (dd/MM/aaaa HH:mm): ");
            try {
                fechaHora = Cita.convertirFecha(entrada.nextLine());
            } catch (DatoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        String motivo;
        do {
            System.out.print("Motivo de la cita: ");
            motivo = entrada.nextLine().trim();
            if (motivo.isEmpty() || motivo.contains(",")) {
                System.out.println("El motivo no puede estar vacío ni llevar comas.");
            }
        } while (motivo.isEmpty() || motivo.contains(","));

        // Se muestran los registros para que el usuario vea qué ID escribir (polimorfismo con mostrarInfo).
        System.out.println("Doctores registrados:");
        for (Doctor d : doctores.listar()) {
            System.out.println("  " + d.mostrarInfo());
        }
        Doctor doctor;
        do {
            System.out.print("ID del doctor: ");
            doctor = doctores.buscarPorId(entrada.nextLine());
            if (doctor == null) {
                System.out.println("Doctor no encontrado.");
            }
        } while (doctor == null);

        System.out.println("Pacientes registrados:");
        for (Paciente p : pacientes.listar()) {
            System.out.println("  " + p.mostrarInfo());
        }
        Paciente paciente;
        do {
            System.out.print("ID del paciente: ");
            paciente = pacientes.buscarPorId(entrada.nextLine());
            if (paciente == null) {
                System.out.println("Paciente no encontrado.");
            }
        } while (paciente == null);

        Cita cita = new Cita(id, fechaHora, motivo, doctor, paciente);
        citas.agregar(cita);
        System.out.println("Cita creada con éxito: " + cita.mostrarInfo());
    }
}
