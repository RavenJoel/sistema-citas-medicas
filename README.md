# Sistema de administración de citas médicas

Programa de consola en Java que simula la administración de citas de un consultorio clínico: alta de doctores, alta de pacientes y creación de citas relacionadas con un doctor y un paciente. El acceso está restringido a administradores con identificador y contraseña, y toda la información se guarda en archivos CSV dentro de la carpeta `db/`.

Evidencia del curso *Computación en Java* – Universidad Tecmilenio.

La documentación completa (Acerca de, Proyecto y Guías) está en la [Wiki del repositorio](https://github.com/RavenJoel/sistema-citas-medicas/wiki).

## Instalación y configuración

**Requisitos**

- JDK 11 o superior (desarrollado con Eclipse Temurin 25 LTS y compilado con `--release 11`).
- Git.
- Cualquier editor o IDE (se usó Visual Studio Code con el *Extension Pack for Java*).

**Obtener el código**

```bash
git clone https://github.com/RavenJoel/sistema-citas-medicas.git
cd sistema-citas-medicas
```

**Compilar y generar el JAR ejecutable**

```bash
javac --release 11 -encoding UTF-8 -d out $(find src -name "*.java")
jar --create --file sistema-citas-medicas.jar --main-class citasmedicas.Principal -C out .
```

El programa solo usa clases de la biblioteca estándar de Java, por lo que el JAR contiene todo lo necesario para ejecutarse (no hay dependencias externas que empaquetar).

También puedes descargar el JAR ya compilado desde la sección **Releases** (versión `v1.0`).

**Carpeta `db/`**

Guarda los archivos `administradores.csv`, `doctores.csv`, `pacientes.csv` y `citas.csv`. Su contenido no se sube al repositorio (lo impide `db/.gitignore`). Si la carpeta o algún archivo no existe, el programa lo crea al iniciar.

## Uso del programa

Ejecuta el JAR desde la carpeta del proyecto:

```bash
java -jar sistema-citas-medicas.jar
```

1. **Inicio de sesión.** Escribe el ID y la contraseña de un administrador. Hay 3 intentos. La primera vez se crea un administrador por defecto:
   - ID: `admin`
   - Contraseña: `admin123`
2. **Menú principal:**
   1. **Dar de alta doctor:** ID único, nombre completo y especialidad.
   2. **Dar de alta paciente:** ID único y nombre completo.
   3. **Crear cita:** ID único, fecha y hora (`dd/MM/aaaa HH:mm`), motivo, ID del doctor y ID del paciente. El programa muestra los doctores y pacientes registrados para elegir.
   4. **Salir.**

Los datos se guardan en el momento en que se registran. Si se captura un dato inválido (campo vacío, ID repetido, fecha inexistente o un texto con comas), el programa muestra el error y vuelve a pedir el dato sin cerrarse.

## Créditos

Joel Antonio Valdez Gómez – desarrollo.
Profesora: Silvia Tello Zúñiga.

## Licencia

Distribuido bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.
