package citasmedicas.persistencia;

import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Doctor;
import java.nio.file.Paths;

public class DoctorRepositorio extends RepositorioCsv<Doctor> {

    public DoctorRepositorio() {
        super(Paths.get("db", "doctores.csv"));
    }

    @Override
    protected Doctor convertirLinea(String linea) throws DatoInvalidoException {
        return Doctor.desdeCsv(linea);
    }
}
