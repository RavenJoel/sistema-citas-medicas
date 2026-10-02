package citasmedicas.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Una cita relaciona una fecha y hora con un doctor y un paciente.
 */
public class Cita implements Identificable, ConvertibleCsv {

    // dd/MM/aaaa HH:mm. STRICT rechaza fechas que no existen, como 31/02/2026.
    public static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private String id;
    private LocalDateTime fechaHora;
    private String motivo;
    private Doctor doctor;
    private Paciente paciente;

    public Cita(String id, LocalDateTime fechaHora, String motivo, Doctor doctor, Paciente paciente)
            throws DatoInvalidoException {
        this.id = Persona.validarTexto(id, "ID");
        this.motivo = Persona.validarTexto(motivo, "motivo");
        if (fechaHora == null || doctor == null || paciente == null) {
            throw new DatoInvalidoException("La cita necesita fecha, doctor y paciente.");
        }
        this.fechaHora = fechaHora;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    // Convierte un texto dd/MM/aaaa HH:mm en fecha y hora.
    public static LocalDateTime convertirFecha(String texto) throws DatoInvalidoException {
        try {
            return LocalDateTime.parse(texto.trim(), FORMATO);
        } catch (DateTimeParseException e) {
            throw new DatoInvalidoException("Fecha u hora no válida. Usa el formato dd/MM/aaaa HH:mm.");
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String mostrarInfo() {
        return "[" + id + "] " + fechaHora.format(FORMATO) + " | " + motivo
                + " | Doctor: " + doctor.getNombreCompleto()
                + " | Paciente: " + paciente.getNombreCompleto();
    }

    // Formato en citas.csv: id,fechaHora,motivo,idDoctor,idPaciente
    // Se guarda solo el ID del doctor y del paciente; al cargar se buscan en sus repositorios.
    @Override
    public String toCsv() {
        return id + "," + fechaHora.format(FORMATO) + "," + motivo + ","
                + doctor.getId() + "," + paciente.getId();
    }
}
