package citasmedicas.persistencia;

import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Paciente;
import java.nio.file.Paths;

public class PacienteRepositorio extends RepositorioCsv<Paciente> {

    public PacienteRepositorio() {
        super(Paths.get("db", "pacientes.csv"));
    }

    @Override
    protected Paciente convertirLinea(String linea) throws DatoInvalidoException {
        return Paciente.desdeCsv(linea);
    }
}
