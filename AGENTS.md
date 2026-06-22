# AGENTS.md

## Project

Java SE 21 console app — **Sistema de Tesoreria IASD** (Iglesia Adventista del Septimo Dia). Proyecto final universitario. Sin dependencias externas, sin frameworks. Construido con NetBeans/Ant.

## Build & Run

```bash
# Clean + build (requiere ant en PATH)
ant clean jar

# Ejecutar
java -jar dist/AplicacionFinalSw.jar

# O directamente via NetBeans: Run > Run Project (F6)
```

Punto de entrada: `vistacontrol.Index.main()` (configurado en `nbproject/project.properties`)

## Arquitectura

```
src/
├── repository/     # Capa de datos — arrays estaticos como BD en memoria, cargados desde CSV
│   ├── Miembros.java        # Miembros de la iglesia (antes Personas + Usuarios)
│   ├── Credenciales.java    # Credenciales de acceso (email + password + DNI miembro)
│   ├── Iglesias.java        # Catalogo de iglesias
│   ├── Depositos.java       # Depositos financieros (4 montos)
│   └── Gastos.java          # Gastos/retiros de dinero
├── utils/          # Utilidades
│   ├── Archivo.java         # CSV I/O (punto y coma como delimitador)
│   ├── Constantes.java      # Departamentos y Cargos estaticos
│   ├── Recibo.java          # Generacion de recibos HTML
│   ├── Correo.java          # Apertura de cliente de correo
│   ├── Lector.java          # Entrada por consola
│   ├── Sesion.java          # Manejo de sesion (login unico)
│   └── Errores.java         # Mensajes de error
└── vistacontrol/   # Menus de consola — vista + controlador
    ├── Index.java           # Punto de entrada, menu de login
    ├── IndexSistema.java    # Menu principal post-login
    ├── IndexMiembros.java   # CRUD miembros + asignacion de roles
    ├── IndexIglesia.java    # CRUD iglesias
    ├── IndexInformes.java   # Registro y consulta de depositos
    └── IndexGastos.java     # Registro y consulta de gastos
```

- **Sin Java Collections** — todos los datos en arrays de tamano fijo (max 1000 registros por entidad)
- **Persistencia CSV** en `saveds/db/` via `utils/Archivo.java` (delimitador punto y coma)
- **`saveds/` esta en gitignore** — la app recrea los archivos CSV en la primera ejecucion
- **Un solo rol de usuario** — login via email + password, vinculado a un miembro por DNI
- **Departamentos y Cargos estaticos** en `Constantes.java` — no se pueden crear/editar desde la UI
- **Flujo de acceso**: `Index` → login → `IndexSistema` → sub-menus

## Convenios de Codigo

- Solo I/O de consola (`System.out.println`, `Scanner`)
- Nombres de variables/metodos en espanol (ej: `ingresarDatos`, `buscarMiembro`, `depositar`)
- Sin tests unitarios (`test/` vacio)
- Codificacion UTF-8
- Generacion de recibos HTML que se abren en el navegador por defecto (`Recibo.java`)

## Datos Importantes

- **Miembros**: DNI, nombre completo, fecha nacimiento, correo, telefono, iglesia, departamento, cargo, fecha expiracion
- **Credenciales**: Se crean al asignar rol (password = DNI del miembro). Se eliminan al expirar el rol
- **Depositos**: 4 montos (diezmo, ofrenda sistematica, proyecto local, pagos a instituciones)
- **Gastos**: Codigo de iglesia, ministerio, monto, descripcion, fecha
- **Expiracion de roles**: Se verifica al login Y al entrar al menu principal
- **Pagos anonimos**: DNI se almacena como "*anonimo"
- **Borrar iglesia**: Desasigna todos sus miembros (codigo iglesia = -1)

## Archivos Clave

| Archivo | Por que importa |
|---------|-----------------|
| `src/utils/Archivo.java` | Motor CSV — entender antes de modificar la capa de datos |
| `src/vistacontrol/Index.java` | Punto de entrada, menu principal |
| `src/repository/Miembros.java` | CRUD de miembros + asignacion de roles |
| `src/repository/Credenciales.java` | Autenticacion y gestion de accesos |
| `src/utils/Constantes.java` | Listas estaticas de departamentos y cargos |
| `nbproject/project.properties` | Config build: JDK 21, clase principal, encoding |
