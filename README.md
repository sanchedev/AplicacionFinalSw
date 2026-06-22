# Sistema de Tesoreria de la IASD — Proyecto Final

**Curso:** Fundamentos de Programacion  
**Carrera:** Ingenieria de Software — Ciclo 1  
**IDE:** NetBeans | **Lenguaje:** Java (Modo Consola)

---

## Requerimiento General

Elaborar un sistema modular con un menu de opciones interactivo y multiples submenus enfocado en la gestion financiera y de membresia (**Sistema de Tesoreria de la IASD**). El sistema debe operar bajo restricciones de memoria estatica (arreglos unidimensionales) y persistencia logica durante el ciclo de vida de la aplicacion.

## Arquitectura del Proyecto

El proyecto sigue una estructura limpia separando la interfaz de usuario, los almacenes de datos estaticos y las utilidades del sistema:

```text
src/
├── repository/
│   ├── Miembros.java        → Modelo unificado de miembros (antes Personas + Usuarios)
│   ├── Credenciales.java    → Credenciales de acceso y roles del sistema
│   ├── Depositos.java       → Gestion de ingresos (4 tipos de monto)
│   ├── Iglesias.java        → Catalogo de iglesias/distritos
│   └── Gastos.java          → Registro de retiros de dinero por ministerio
├── utils/
│   ├── Archivo.java         → Motor CSV (delimitador punto y coma)
│   ├── Constantes.java      → Listas estaticas de departamentos y cargos
│   ├── Validaciones.java    → Validacion de DNI, fecha, correo, telefono, monto
│   ├── Exportador.java      → Exportacion de reportes a CSV y HTML
│   ├── Lector.java          → Captura y validacion de datos por consola
│   ├── Recibo.java          → Generacion de recibos HTML en navegador
│   ├── Sesion.java          → Controlador de estado de autenticacion
│   └── Errores.java         → Mensajes de error centralizados
└── vistacontrol/
    ├── Index.java           → Punto de entrada + login + creacion de admin
    ├── IndexSistema.java    → Menu principal post-login (con contadores)
    ├── IndexMiembros.java   → CRUD miembros + asignacion de roles
    ├── IndexIglesia.java    → CRUD iglesias
    ├── IndexInformes.java   → Depositos + resumenes + balance + exportacion
    └── IndexGastos.java     → Registro de gastos + exportacion
```

## Funcionalidades

### CRUD de Miembros
- **Registrar** miembro con validacion de DNI, fecha, correo, telefono e iglesia obligatoria
- **Listar** todos los miembros
- **Buscar** por nombre (coincidencia parcial)
- **Editar** perfil de miembro
- **Asignar rol** a miembro (departamento + cargo + fecha de expiracion)
- **Retirar rol** a miembro
- **Ver miembros** filtrados por departamento

### CRUD de Iglesias
- **Registrar** iglesia con nombre, direccion y aforo
- **Listar** todas las iglesias
- **Buscar** por nombre
- **Ver detalle** de iglesia
- **Editar** informacion de iglesia
- **Borrar** iglesia (desasigna automaticamente sus miembros)

### Depositos y Informes
- **Registrar deposito** con 4 tipos de monto (diezmo, ofrenda sistematica, proyecto local, pagos a instituciones)
- **Modo aportante**: miembro registrado, invitado (sin registro) o anonimo
- **Ver depositos** por iglesia o todos
- **Resumen por tipo de aporte** — totales por categora
- **Resumen por iglesia** — totales agrupados por sede
- **Balance general** — ingresos vs gastos

### Gastos
- **Registrar gasto** por iglesia y ministerio
- **Ver gastos** por iglesia o todos
- **Validacion** de monto positivo e iglesia valida

### Exportacion de Reportes
- **CSV** — archivos compatibles con Excel (se abren automaticamente)
- **HTML** — reportes estilizados (imprimir a PDF desde el navegador con Ctrl+P)
- Disponible en: listados de depositos, listados de gastos, resumenes y balance

### Validaciones
- DNI: 8 digitos numericos
- Fecha: formato dd-MM-yyyy
- Correo: debe contener @
- Telefono: 9 digitos numericos
- Monto: debe ser positivo
- Iglesia: debe existir en el catalogo
- Filas invalidas en CSV se omiten y limpian automaticamente

### Roles
| Rol | Permisos |
|-----|----------|
| Administrador | Control total: miembros, iglesias, depositos, gastos y reportes |
| Invitado | Registro temporal de depositos sin necesidad de cuenta |

### Cuenta por defecto
```
Email:       admin@iasd.com
Contrasena:  admin123
```

Se crea automaticamente en la primera ejecucion. Se recomienda eliminarla despues de crear el administrador real.

## Como ejecutar

1. Clonar el repositorio
2. Abrir en NetBeans
3. Limpiar y construir proyecto (Run > Clean and Build)
4. Ejecutar `Index.java` (Run > Run Project)
5. Iniciar sesion con las credenciales por defecto

## Autores

- Jose Sanchez (_**@sanchedev**_)
- Mathias Saavedra
- Jaasiel Munoz
- Bryan Plasencia
