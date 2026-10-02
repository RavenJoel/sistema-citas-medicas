package citasmedicas.persistencia;

import citasmedicas.modelo.Cita;
import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Doctor;
import citasmedicas.modelo.Paciente;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CitaRepositorio extends RepositorioCsv<Cita> {

    // Para reconstruir una cita hay que encontrar a su doctor y a su paciente.
    private final DoctorRepositorio doctores;
    private final PacienteRepositorio pacientes;

    public CitaRepositorio(DoctorRepositorio doctores, PacienteRepositorio pacientes) {
        super(Paths.get("db", "citas.csv"));
        this.doctores = doctores;
        this.pacientes = pacientes;
    }

    // Formato: id,fechaHora,motivo,idDoctor,idPaciente
    @Override
    protected Cita convertirLinea(String linea) throws DatoInvalidoException {
        String[] datos = linea.split(",");
        if (datos.length != 5) {
            throw new DatoInvalidoException("Línea de cita con formato incorrecto: " + linea);
        }
        LocalDateTime fechaHora = Cita.convertirFecha(datos[1]);
        Doctor doctor = doctores.buscarPorId(datos[3]);
        Paciente paciente = pacientes.buscarPorId(datos[4]);
        if (doctor == null || paciente == null) {
            throw new DatoInvalidoException("La cita " + datos[0] + " tiene un doctor o paciente que no existe.");
        }
        return new Cita(datos[0], fechaHora, datos[2], doctor, paciente);
    }
}
