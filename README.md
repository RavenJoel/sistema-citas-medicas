# Sistema de administración de citas médicas

Programa de consola en Java que simula la administración de citas de un consultorio clínico: alta de doctores, alta de pacientes y creación de citas relacionadas con un doctor y un paciente. El acceso está restringido a administradores con identificador y contraseña, y la información se guarda en archivos CSV.

Evidencia del curso *Computación en Java* – Universidad Tecmilenio.

## Instalación y configuración

**Requisitos**

- JDK 11 o superior (desarrollado con Eclipse Temurin 25 LTS, compilado con `--release 11`).
- Git.
- Visual Studio Code con el *Extension Pack for Java* (opcional, cualquier IDE funciona).

**Obtener el código**

```bash
git clone https://github.com/RavenJoel/sistema-citas-medicas.git
cd sistema-citas-medicas
```

La carpeta `db/` guarda los archivos de datos. No se sube al repositorio; si los archivos no existen, el programa los crea al iniciar.

## Uso del programa

> En desarrollo. Las instrucciones de compilación y ejecución del JAR se agregarán en la entrega final.

1. Iniciar sesión con un identificador y contraseña de administrador.
2. Elegir una opción del menú principal:
   1. Dar de alta doctor
   2. Dar de alta paciente
   3. Crear cita
   4. Salir

## Créditos

Joel Antonio Valdez Gómez – desarrollo.
Profesora: Silvia Tello Zúñiga.

## Licencia

Distribuido bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.
