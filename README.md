# Sistema de Gestión de Usuarios — Proyecto Final

**Curso:** Fundamentos de Programación  
**Carrera:** Ingeniería de Software — Ciclo 1  
**IDE:** NetBeans | **Lenguaje:** Java (modo consola)

## Requerimiento General

Elaborar un menú de opciones con 2 submenús aplicando el tema: **Sistema de Tesorería de la IASD** (o afín determinado por el docente).

## Implementación Actual

Sistema de gestión de usuarios con autenticación por roles (Administrador / Usuario común) y CRUD completo sobre arreglos estáticos con menú interactivo por consola.

## Arquitectura

```
src/
├── vistacontrol/
│   ├── Index.java            → Punto de entrada (main)
│   └── IndexUsuarios.java    → Menú y CRUD de usuarios
├── repository/
│   └── Usuarios.java         → Lógica de datos (arreglos estáticos)
└── utils/
    ├── Errores.java          → Validaciones y mensajes de error
    ├── Lector.java           → Entrada por consola (Scanner)
    └── Sesion.java           → Autenticación y manejo de sesión
```

## Funcionalidades

### CRUD Básico
- **Crear** usuario con validación de email repetido
- **Listar** todos los usuarios
- **Buscar** por coincidencia parcial de nombre
- **Editar** perfil propio (previa autenticación)
- **Eliminar** usuario (con confirmación y protección contra auto-eliminación)

### Validaciones
- Email duplicado al registrar
- Límite de almacenamiento: 1000 usuarios
- Autenticación requerida antes de editar/eliminar
- Rango válido en opciones del menú
- Protección: un admin no puede eliminarse a sí mismo

### Roles
| Rol           | Permisos                                      |
|---------------|-----------------------------------------------|
| Administrador | CRUD sobre cualquier usuario                  |
| Usuario común | Ver/Editar/Eliminar su propio perfil          |

### Usuario predefinido
```
Email:       ejemplo@correo.com
Contraseña:  12345678
Rol:         Administrador
```

## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en NetBeans
3. Limpiar y construir proyecto
4. Ejecutar `Index.java`

## Autores

- José Sánchez (**sanchedev**)
- Mathias Saavedra
- Jaasiel Muñoz
- Bryan ...
