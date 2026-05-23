# HDT5 - Simulación de Procesos con DES en Java

Simulación de un sistema operativo de tiempo compartido implementada en Java puro,
equivalente a la simulación con SimPy descrita en la hoja de trabajo.

## Compilar y ejecutar

```bash
javac HDT5.java
java HDT5
```

> Requiere Java 8 o superior. No se necesitan librerías externas.

## Qué hace el programa

Simula el ciclo de vida completo de procesos en un sistema operativo:

```
NEW ──► READY ──► RUNNING ──► TERMINATED
                    │
                    ├──► WAITING (I/O, prob. 1/21) ──► READY
                    └──► READY   (regresa, prob. 20/21)
```

## Parámetros por defecto

| Parámetro | Valor |
|---|---|
| RAM total | 100 unidades |
| Instrucciones por turno | 3 |
| Número de CPUs | 1 |
| Tiempo de I/O | 3 unidades |
| Semilla aleatoria | 42 |
| Memoria por proceso | 1–10 (aleatorio) |
| Instrucciones por proceso | 1–10 (aleatorio) |
| Intervalo de llegada | Distribución exponencial |

## Resultados (resumen)

### Configuración base (RAM=100, CPU=3 instr/turno, 1 CPU)

| Procesos | Interv=10 | Interv=5 | Interv=1 |
|---|---|---|---|
| 25  | 2.61  | 2.98  | 21.38  |
| 50  | 2.87  | 3.91  | 42.04  |
| 100 | 2.89  | 3.74  | 76.73  |
| 150 | 2.86  | 3.68  | 110.56 |
| 200 | 2.83  | 3.61  | 143.65 |

### CPU más rápido (estrategia b — GANADORA)

| Procesos | Interv=10 | Interv=5 | Interv=1 |
|---|---|---|---|
| 25  | 1.46 | 1.56 | 7.95  |
| 50  | 1.42 | 1.65 | 15.01 |
| 100 | 1.55 | 1.78 | 26.65 |
| 150 | 1.55 | 1.81 | 40.84 |
| 200 | 1.55 | 1.74 | 51.73 |

## Estrategia recomendada

**b) CPU más rápido (6 instrucciones por turno)**

El cuello de botella es el CPU, no la RAM. Duplicar la velocidad del
procesador reduce el tiempo promedio a ~la mitad en todos los escenarios
y es la mejora más consistente bajo alta carga (Intervalo=1).
