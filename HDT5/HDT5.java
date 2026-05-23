import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

// Simulacion de procesos en un OS de tiempo compartido usando DES (Discrete Event Simulation)
// Como no usamos SimPy, implementamos la cola de eventos manualmente con una PriorityQueue
public class HDT5 {

    // Parametros de la simulacion (se cambian para cada tarea)
    static final int    SEMILLA_ALEATORIA = 42;
    static int          CAPACIDAD_RAM     = 100; // unidades de RAM total
    static int          VEL_CPU           = 3;   // instrucciones que ejecuta el CPU por turno
    static int          NUM_CPUS          = 1;
    static final double TIEMPO_IO         = 3.0;

    // Representa un proceso en el sistema operativo
    static class Proceso {
        int    id;
        int    memoria;        // RAM que necesita (1-10)
        int    instrucciones;  // instrucciones pendientes (1-10)
        double tiempoLlegada;

        Proceso(int id, int memoria, int instrucciones, double tiempoLlegada) {
            this.id            = id;
            this.memoria       = memoria;
            this.instrucciones = instrucciones;
            this.tiempoLlegada = tiempoLlegada;
        }
    }

    // Estados posibles del proceso (cada uno es un tipo de evento)
    enum TipoEvento {
        LLEGADA,  // estado NEW: llega al sistema, pide RAM
        LISTO,    // estado READY: tiene RAM, espera CPU
        FIN_CPU,  // estado RUNNING termina su turno
        FIN_IO,   // estado WAITING termina, regresa a READY
        TERMINAR  // estado TERMINATED: sale del sistema
    }

    // Un evento tiene un tiempo y un proceso asociado
    static class Evento implements Comparable<Evento> {
        double     tiempo;
        TipoEvento tipo;
        Proceso    proceso;

        Evento(double tiempo, TipoEvento tipo, Proceso proceso) {
            this.tiempo  = tiempo;
            this.tipo    = tipo;
            this.proceso = proceso;
        }

        @Override
        public int compareTo(Evento otro) {
            return Double.compare(this.tiempo, otro.tiempo);
        }
    }

    // Estado global de la simulacion
    static double              tiempoActual;
    static int                 memoriaDisponible;
    static int                 cpusOcupados;
    static Queue<Proceso>      colaMemoria;  // procesos esperando RAM
    static Queue<Proceso>      colaCPU;      // procesos esperando CPU (ready)
    static PriorityQueue<Evento> colaEventos;
    static List<Double>        tiemposFinales;
    static Random              rand;

    // Equivalente a random.expovariate(1.0 / media) de Python
    static double expovariate(double media) {
        return -media * Math.log(rand.nextDouble());
    }

    static void agregarEvento(double tiempo, TipoEvento tipo, Proceso p) {
        colaEventos.add(new Evento(tiempo, tipo, p));
    }

    // Se llama cuando se libera RAM, para ver si algun proceso en espera puede entrar
    static void verificarColaMemoria() {
        while (!colaMemoria.isEmpty() && memoriaDisponible >= colaMemoria.peek().memoria) {
            Proceso p = colaMemoria.poll();
            memoriaDisponible -= p.memoria;
            agregarEvento(tiempoActual, TipoEvento.LISTO, p);
        }
    }

    // Se llama cuando se libera un CPU, para asignarlo al siguiente en cola
    static void verificarColaCPU() {
        while (cpusOcupados < NUM_CPUS && !colaCPU.isEmpty()) {
            Proceso p = colaCPU.poll();
            cpusOcupados++;
            agregarEvento(tiempoActual + 1.0, TipoEvento.FIN_CPU, p);
        }
    }

    static void simular(int numProcesos, double intervalo) {
        // Reiniciar todo
        tiempoActual      = 0.0;
        memoriaDisponible = CAPACIDAD_RAM;
        cpusOcupados      = 0;
        colaMemoria       = new LinkedList<>();
        colaCPU           = new LinkedList<>();
        colaEventos       = new PriorityQueue<>();
        tiemposFinales    = new ArrayList<>();
        rand              = new Random(SEMILLA_ALEATORIA);

        // Generar las llegadas de todos los procesos con distribucion exponencial
        double tiempoLlegada = 0.0;
        for (int i = 1; i <= numProcesos; i++) {
            tiempoLlegada += expovariate(intervalo);
            int mem   = rand.nextInt(10) + 1;
            int instr = rand.nextInt(10) + 1;
            agregarEvento(tiempoLlegada, TipoEvento.LLEGADA,
                          new Proceso(i, mem, instr, tiempoLlegada));
        }

        // Bucle principal: procesar eventos en orden cronologico (como env.run() de SimPy)
        while (!colaEventos.isEmpty()) {
            Evento  ev = colaEventos.poll();
            tiempoActual = ev.tiempo;
            Proceso p  = ev.proceso;

            switch (ev.tipo) {

                case LLEGADA:
                    // El proceso pide RAM; si no hay, queda en cola
                    if (memoriaDisponible >= p.memoria) {
                        memoriaDisponible -= p.memoria;
                        agregarEvento(tiempoActual, TipoEvento.LISTO, p);
                    } else {
                        colaMemoria.add(p);
                    }
                    break;

                case LISTO:
                    // El proceso quiere el CPU; si no hay, queda en cola
                    if (cpusOcupados < NUM_CPUS) {
                        cpusOcupados++;
                        agregarEvento(tiempoActual + 1.0, TipoEvento.FIN_CPU, p);
                    } else {
                        colaCPU.add(p);
                    }
                    break;

                case FIN_CPU:
                    // Ejecutar hasta VEL_CPU instrucciones; si quedan menos, libera antes
                    p.instrucciones -= Math.min(p.instrucciones, VEL_CPU);

                    cpusOcupados--;
                    verificarColaCPU();

                    if (p.instrucciones == 0) {
                        agregarEvento(tiempoActual, TipoEvento.TERMINAR, p);
                    } else {
                        // numero entre 1-21: si es 1 va a I/O, si no regresa a ready
                        int decision = rand.nextInt(21) + 1;
                        if (decision == 1) {
                            agregarEvento(tiempoActual + TIEMPO_IO, TipoEvento.FIN_IO, p);
                        } else {
                            agregarEvento(tiempoActual, TipoEvento.LISTO, p);
                        }
                    }
                    break;

                case FIN_IO:
                    // Termino I/O, regresa a la cola de ready
                    agregarEvento(tiempoActual, TipoEvento.LISTO, p);
                    break;

                case TERMINAR:
                    // Guardar tiempo total en el sistema y liberar RAM
                    tiemposFinales.add(tiempoActual - p.tiempoLlegada);
                    memoriaDisponible += p.memoria;
                    verificarColaMemoria();
                    break;
            }
        }
    }

    static double promedio(List<Double> datos) {
        if (datos.isEmpty()) return 0.0;
        double suma = 0.0;
        for (double d : datos) suma += d;
        return suma / datos.size();
    }

    static double desviacionEstandar(List<Double> datos) {
        if (datos.size() < 2) return 0.0;
        double prom = promedio(datos);
        double suma = 0.0;
        for (double d : datos) suma += (d - prom) * (d - prom);
        return Math.sqrt(suma / datos.size());
    }

    // Panel de grafica de lineas hecho con Swing (sin librerias externas)
    static class PanelGrafica extends JPanel {

        private final String     titulo;
        private final String[]   etiquetasX;
        private final double[][] series;    // series[i][j] = valor de la serie i en el punto j
        private final String[]   leyendas;

        private static final Color[] COLORES = {
            new Color(31,  119, 180),
            new Color(255, 127,  14),
            new Color(44,  160,  44),
            new Color(214,  39,  40),
            new Color(148, 103, 189),
        };

        PanelGrafica(String titulo, String[] etiquetasX, double[][] series, String[] leyendas) {
            this.titulo     = titulo;
            this.etiquetasX = etiquetasX;
            this.series     = series;
            this.leyendas   = leyendas;
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(780, 500));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int mL = 85, mR = 30, mT = 60, mB = 70;
            int w  = getWidth()  - mL - mR;
            int h  = getHeight() - mT - mB;

            // Encontrar el maximo para escalar el eje Y
            double maxY = 0.1;
            for (double[] s : series)
                for (double v : s)
                    if (v > maxY) maxY = v;
            maxY *= 1.15;

            // Titulo
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            FontMetrics fmT = g2.getFontMetrics();
            g2.setColor(new Color(40, 40, 40));
            g2.drawString(titulo, mL + (w - fmT.stringWidth(titulo)) / 2, 38);

            // Cuadricula y etiquetas del eje Y
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            FontMetrics fm = g2.getFontMetrics();
            for (int i = 0; i <= 6; i++) {
                double val = maxY * i / 6;
                int    y   = mT + h - (int)(h * i / 6);
                g2.setColor(new Color(220, 220, 220));
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(mL, y, mL + w, y);
                g2.setColor(new Color(80, 80, 80));
                String lbl = String.format("%.1f", val);
                g2.drawString(lbl, mL - fm.stringWidth(lbl) - 7, y + 4);
            }

            // Ejes
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(mL, mT,     mL,     mT + h);
            g2.drawLine(mL, mT + h, mL + w, mT + h);

            // Etiquetas eje X
            int nPts = etiquetasX.length;
            for (int i = 0; i < nPts; i++) {
                int x = mL + (nPts == 1 ? w / 2 : w * i / (nPts - 1));
                g2.setColor(new Color(80, 80, 80));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                g2.drawString(etiquetasX[i], x - fm.stringWidth(etiquetasX[i]) / 2, mT + h + 18);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(x, mT + h, x, mT + h + 5);
            }

            // Titulo eje X
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            FontMetrics fmX = g2.getFontMetrics();
            String lblX = "Numero de Procesos";
            g2.setColor(new Color(40, 40, 40));
            g2.drawString(lblX, mL + (w - fmX.stringWidth(lblX)) / 2, mT + h + 45);

            // Titulo eje Y (rotado)
            Graphics2D g2r = (Graphics2D) g2.create();
            g2r.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2r.setFont(new Font("SansSerif", Font.BOLD, 12));
            FontMetrics fmY = g2r.getFontMetrics();
            String lblY = "Tiempo Promedio (unidades)";
            g2r.rotate(-Math.PI / 2);
            g2r.setColor(new Color(40, 40, 40));
            g2r.drawString(lblY, -(mT + h / 2 + fmY.stringWidth(lblY) / 2), mL - 55);
            g2r.dispose();

            // Dibujar cada serie
            for (int s = 0; s < series.length; s++) {
                Color    col  = COLORES[s % COLORES.length];
                double[] data = series[s];

                int[] px = new int[data.length];
                int[] py = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    px[i] = mL + (data.length == 1 ? w / 2 : w * i / (data.length - 1));
                    py[i] = mT + h - (int)(h * data[i] / maxY);
                }

                g2.setColor(col);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                for (int i = 0; i < px.length - 1; i++)
                    g2.drawLine(px[i], py[i], px[i + 1], py[i + 1]);

                for (int i = 0; i < px.length; i++) {
                    g2.setColor(col);
                    g2.fillOval(px[i] - 5, py[i] - 5, 10, 10);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(px[i] - 3, py[i] - 3, 6, 6);
                }

                // Leyenda
                if (leyendas != null && s < leyendas.length) {
                    int lx = mL + 15;
                    int ly = mT + 15 + s * 22;
                    g2.setColor(col);
                    g2.setStroke(new BasicStroke(2.5f));
                    g2.drawLine(lx, ly - 4, lx + 22, ly - 4);
                    g2.fillOval(lx + 7, ly - 9, 8, 8);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(lx + 9, ly - 7, 4, 4);
                    g2.setColor(new Color(40, 40, 40));
                    g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                    g2.drawString(leyendas[s], lx + 28, ly);
                }
            }
        }
    }

    public static void main(String[] args) {

        int[]    cantidades        = {25, 50, 100, 150, 200};
        String[] etiquetas         = {"25", "50", "100", "150", "200"};
        double[] intervalos        = {10.0, 5.0, 1.0};
        String[] nombresIntervalos = {
            "Intervalo=10 (carga normal)",
            "Intervalo=5  (carga alta)",
            "Intervalo=1  (carga muy alta)"
        };

        // resultados[configuracion][intervalo][cantidad]
        // 0=Base, 1=RAM200, 2=CPU rapido, 3=2CPUs
        double[][][] resultados = new double[4][intervalos.length][cantidades.length];

        // --- Tarea 1 y 2: config base ---
        System.out.println("=".repeat(68));
        System.out.println("   SIMULACION OS CON TIEMPO COMPARTIDO  -  HDT5");
        System.out.println("=".repeat(68));
        System.out.println("\n*** TAREA 1 y 2: Configuracion base (RAM=100 | CPU=3 | 1 CPU) ***");

        CAPACIDAD_RAM = 100; VEL_CPU = 3; NUM_CPUS = 1;

        for (int j = 0; j < intervalos.length; j++) {
            System.out.printf("%n  [%s]%n", nombresIntervalos[j]);
            System.out.printf("  %-10s %-22s %-22s%n", "Procesos", "Tiempo Promedio", "Desv. Estandar");
            System.out.println("  " + "-".repeat(54));
            for (int i = 0; i < cantidades.length; i++) {
                simular(cantidades[i], intervalos[j]);
                resultados[0][j][i] = promedio(tiemposFinales);
                System.out.printf("  %-10d %-22.4f %-22.4f%n",
                    cantidades[i], resultados[0][j][i], desviacionEstandar(tiemposFinales));
            }
        }

        // --- Tarea 3a: mas RAM ---
        System.out.println("\n\n*** TAREA 3a: RAM aumentada (RAM=200 | CPU=3 | 1 CPU) ***");

        CAPACIDAD_RAM = 200; VEL_CPU = 3; NUM_CPUS = 1;

        for (int j = 0; j < intervalos.length; j++) {
            System.out.printf("%n  [%s]%n", nombresIntervalos[j]);
            System.out.printf("  %-10s %-22s %-22s%n", "Procesos", "Tiempo Promedio", "Desv. Estandar");
            System.out.println("  " + "-".repeat(54));
            for (int i = 0; i < cantidades.length; i++) {
                simular(cantidades[i], intervalos[j]);
                resultados[1][j][i] = promedio(tiemposFinales);
                System.out.printf("  %-10d %-22.4f %-22.4f%n",
                    cantidades[i], resultados[1][j][i], desviacionEstandar(tiemposFinales));
            }
        }

        // --- Tarea 3b: CPU mas rapido ---
        System.out.println("\n\n*** TAREA 3b: CPU mas rapido (RAM=100 | CPU=6 | 1 CPU) ***");

        CAPACIDAD_RAM = 100; VEL_CPU = 6; NUM_CPUS = 1;

        for (int j = 0; j < intervalos.length; j++) {
            System.out.printf("%n  [%s]%n", nombresIntervalos[j]);
            System.out.printf("  %-10s %-22s %-22s%n", "Procesos", "Tiempo Promedio", "Desv. Estandar");
            System.out.println("  " + "-".repeat(54));
            for (int i = 0; i < cantidades.length; i++) {
                simular(cantidades[i], intervalos[j]);
                resultados[2][j][i] = promedio(tiemposFinales);
                System.out.printf("  %-10d %-22.4f %-22.4f%n",
                    cantidades[i], resultados[2][j][i], desviacionEstandar(tiemposFinales));
            }
        }

        // --- Tarea 3c: 2 CPUs ---
        System.out.println("\n\n*** TAREA 3c: Dos procesadores (RAM=100 | CPU=3 | 2 CPUs) ***");

        CAPACIDAD_RAM = 100; VEL_CPU = 3; NUM_CPUS = 2;

        for (int j = 0; j < intervalos.length; j++) {
            System.out.printf("%n  [%s]%n", nombresIntervalos[j]);
            System.out.printf("  %-10s %-22s %-22s%n", "Procesos", "Tiempo Promedio", "Desv. Estandar");
            System.out.println("  " + "-".repeat(54));
            for (int i = 0; i < cantidades.length; i++) {
                simular(cantidades[i], intervalos[j]);
                resultados[3][j][i] = promedio(tiemposFinales);
                System.out.printf("  %-10d %-22.4f %-22.4f%n",
                    cantidades[i], resultados[3][j][i], desviacionEstandar(tiemposFinales));
            }
        }

        // --- Tarea 4: recomendacion ---
        System.out.println("\n\n" + "=".repeat(68));
        System.out.println("   ESTRATEGIA RECOMENDADA PARA REDUCIR EL TIEMPO PROMEDIO");
        System.out.println("=".repeat(68));
        System.out.println(
            "\n  La mejor estrategia es:\n\n" +
            "  >>> b) Procesador mas rapido (6 instrucciones por turno) <<<\n\n" +
            "  Justificacion:\n\n" +
            "  1. El cuello de botella es el CPU, no la RAM. Los procesos\n" +
            "     pasan la mayor parte del tiempo esperando en la cola de READY.\n\n" +
            "  2. Duplicar la velocidad del CPU reduce a la mitad los turnos\n" +
            "     que cada proceso necesita, drenando la cola mucho mas rapido.\n\n" +
            "  3. Mas RAM (estrategia a) casi no ayuda porque con max 10 unidades\n" +
            "     por proceso y 100 de RAM raramente hay congestion de memoria.\n\n" +
            "  4. Dos CPUs (estrategia c) es similar a b) pero mas costoso en\n" +
            "     hardware. Un CPU mas rapido es mas simple y efectivo.\n\n" +
            "  5. En alta carga (Intervalo=1) la estrategia b) tiene los tiempos\n" +
            "     mas bajos de forma consistente para todas las cantidades.\n"
        );

        // Mostrar graficas en una ventana con pestanas
        String[] leyBaseInterv  = nombresIntervalos;
        String[] leyEstrategias = {
            "Base (RAM=100, CPU=3, 1 CPU)",
            "a) RAM=200",
            "b) CPU x2 (6 instr/turno)",
            "c) 2 CPUs"
        };

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HDT5 - Simulacion OS con Tiempo Compartido");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabs = new JTabbedPane();

            // Pestaña 1: los 3 intervalos con config base (Tarea 1 y 2)
            tabs.addTab("Tarea 1-2: Config Base",
                new PanelGrafica(
                    "Tiempo Promedio vs N. Procesos  -  Config. Base (RAM=100, CPU=3, 1 CPU)",
                    etiquetas,
                    new double[][] { resultados[0][0], resultados[0][1], resultados[0][2] },
                    leyBaseInterv
                )
            );

            // Pestanas 2-4: comparacion de estrategias por intervalo (Tarea 3 y 4)
            for (int j = 0; j < intervalos.length; j++) {
                tabs.addTab(String.format("Tarea 3-4: Intervalo=%.0f", intervalos[j]),
                    new PanelGrafica(
                        String.format("Comparacion de Estrategias  -  %s", nombresIntervalos[j]),
                        etiquetas,
                        new double[][] {
                            resultados[0][j], resultados[1][j],
                            resultados[2][j], resultados[3][j]
                        },
                        leyEstrategias
                    )
                );
            }

            frame.add(tabs);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
