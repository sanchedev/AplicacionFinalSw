package vistacontrol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utils.Errores;
import utils.Lector;
import utils.Recibo;
import utils.Validaciones;

/**
 * si lees esto es porque lo haz leido
 * 
 * @author sanchedev
 * @author plasencia
 */
public class IndexDepositos {

  public static final int[] codigos = new int[1000];
  public static final String[] dnis = new String[1000];
  public static final String[] fechas = new String[1000];
  public static final int[] codigoIglesias = new int[1000];
  public static final double[] diezmos = new double[1000];
  public static final double[] ofrendasSistemicas = new double[1000];
  public static final double[] ofrendasProyectoLocal = new double[1000];
  public static final double[] ofrendasInstituciones = new double[1000];
  public static int cantidad = 0;

  public static int buscarPorCodigo(int codigo) {
    int indice = -1;
    for (int i = 0; i < cantidad; i++) {
      if (codigos[i] == codigo) {
        indice = i;
        break;
      }
    }
    return indice;
  }

  public static void inicio() {
    System.out.println("");
    System.out.println("*** REGISTRAR DEPOSITO ***");
    String dni = Lector.preguntar("DNI");
    if (IndexMiembros.buscarPorDNI(dni) == -1) {
      Errores.personalizado("El Miembro con ese DNI no existe");
    }
    String fecha = Lector.preguntar("Fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    if (!Validaciones.esFechaValida(fecha)) {
      Errores.personalizado("Fecha invalida");
      return;
    }
    IndexIglesias.verIglesias();
    int codigoIglesia = Lector.preguntarEntero("Codigo de Iglesia");
    if (IndexIglesias.buscarPorCodigo(codigoIglesia) == -1) {
      Errores.personalizado("Iglesia con codigo \"" + codigoIglesia + "\" no existe");
      return;
    }
    double diezmo = Lector.preguntarDecimal("Diezmo", 0.0);
    if (diezmo < 0)
      diezmo = 0;
    double ofrenda = Lector.preguntarDecimal("Ofrenda", 0.0);
    if (ofrenda < 0)
      ofrenda = 0;
    double pacto = Lector.preguntarDecimal("Pacto", 0.0);
    if (pacto < 0)
      pacto = 0;
    double instituciones = Lector.preguntarDecimal("Instituciones", 0.0);
    if (instituciones < 0)
      instituciones = 0;
    double total = diezmo + ofrenda + pacto + instituciones;
    System.out.println("Total: " + String.format("%.2f", total));
    if (!Lector.confirmar("¿Desea confirmar el deposito?"))
      return;
    int codigo = 0;
    if (cantidad > 0)
      codigo = codigos[cantidad - 1] + 1;
    codigos[cantidad] = codigo;
    dnis[cantidad] = dni;
    fechas[cantidad] = fecha;
    codigoIglesias[cantidad] = codigoIglesia;
    diezmos[cantidad] = diezmo;
    ofrendasSistemicas[cantidad] = ofrenda;
    ofrendasProyectoLocal[cantidad] = pacto;
    ofrendasInstituciones[cantidad] = instituciones;
    cantidad++;
    System.out.println("Deposito registro exitosamente");
    Recibo recibo = new Recibo(cantidad - 1);
    recibo.abrir();
  }
}
