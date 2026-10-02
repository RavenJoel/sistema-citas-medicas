package citasmedicas.modelo;

/**
 * Todo registro del sistema tiene un identificador único.
 * Gracias a esta interfaz los repositorios pueden buscar por ID sin importar el tipo.
 */
public interface Identificable {

    String getId();
}
