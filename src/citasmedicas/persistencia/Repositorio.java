package citasmedicas.persistencia;

import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Identificable;
import java.io.IOException;
import java.util.List;

/**
 * Operaciones que ofrece cualquier repositorio de registros.
 */
public interface Repositorio<T extends Identificable> {

    void agregar(T elemento) throws DatoInvalidoException, IOException;

    T buscarPorId(String id);

    boolean existe(String id);

    List<T> listar();
}
