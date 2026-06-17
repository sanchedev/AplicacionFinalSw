# Sistema de Tesorería de la IASD — Proyecto Final

**Curso:** Fundamentos de Programación  
**Carrera:** Ingeniería de Software — Ciclo 1  
**IDE:** NetBeans | **Lenguaje:** Java (Modo Consola)

---

## 📌 Requerimiento General
Elaborar un sistema modular con un menú de opciones interactivo y múltiples submenús enfocado en la gestión financiera y de membresía (**Sistema de Tesorería de la IASD**). El sistema debe operar bajo restricciones de memoria estática (arreglos unidimensionales) y persistencia lógica durante el ciclo de vida de la aplicación.

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una estructura limpia separando la interfaz de usuario, los almacenes de datos estáticos y las utilidades del sistema:

```text
src/
├── repository/
│   ├── Depositos.java       → Gestión de ingresos (Diezmos, Ofrendas, Fechas)
│   ├── Iglesias.java        → Catálogo de iglesias/distritos y direcciones
│   ├── Personas.java        → Padrón de miembros y asignación de iglesias
│   └── Usuarios.java        → Credenciales de acceso y roles del sistema
├── utils/
│   ├── Errores.java         → Manejador centralizado de excepciones y alertas
│   ├── Lector.java          → Captura y validación de tipos de datos por consola
│   ├── Recibo.java          → Motor de impresión de comprobantes en navegador web
│   └── Sesion.java          → Controlador de estado de autenticación activa
└── vistacontrol/
    ├── Index.java           → Punto de entrada principal de la aplicación
    ├── IndexTesoreria.java  → Módulo financiero central
    ├── IndexDepositos.java  → CRUD y registro de aportes económicos
    ├── IndexFeligres.java   → Panel de usuario (Miembros / Feligresía)
    ├── IndexIglesias.java   → Gestión de sedes e iglesias locales
    ├── IndexPersonas.java   → Administración del padrón de miembros
    └── IndexUsuarios.java   → Panel administrativo de credenciales y accesos
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
| Rol         | Permisos                                      |
|-------------|-----------------------------------------------|
| Tesorero(a) | Control total (CRUD) sobre usuarios, miembros, iglesias locales y auditoría de depósitos financieros.                  |
| Feligres    | Acceso al menú de feligresía: visualización de información personal, historial de depósitos propios y registro de nuevos aportes.          |

### Tesorero predefinido
```
Email:       ejemplo@correo.com
Contraseña:  12345678
```

## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en NetBeans
3. Limpiar y construir proyecto
4. Ejecutar `Index.java`

## Autores

- José Sánchez (_**@sanchedev**_)
- Mathias Saavedra
- Jaasiel Muñoz
- Bryan Plasencia
