package Programas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AmpliacionMiniShell {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<ProcessBuilder> procesos = new ArrayList<>();
        List<Process> ejecutados = new ArrayList<>();

        System.out.print("¿Cuántos programas quieres lanzar? ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Introduce el programa " + (i + 1) + ": ");
            String programa = sc.nextLine().trim();

            if (programa.isEmpty()) {
                System.out.println("No se puede dejar vacío.");
                i--;
                continue;
            }

            String[] comando = programa.split("\\s+");
            procesos.add(new ProcessBuilder(comando));
        }

        for (int i = 0; i < procesos.size(); i++) {
            try {
                Process proceso = procesos.get(i).start();
                ejecutados.add(proceso);
                System.out.println("Programa " + (i + 1) + " iniciado con PID: " + proceso.toHandle().pid());
            } catch (IOException e) {
                System.out.println("El programa " + (i + 1) + " no se pudo iniciar: " + e.getMessage());
            }
        }

        for (int i = 0; i < ejecutados.size(); i++) {
            Process proceso = ejecutados.get(i);
            ProcessHandle handle = proceso.toHandle();

            try {
                System.out.println("\nProceso " + (i + 1));
                System.out.println("PID: " + handle.pid());
                System.out.println("Estado: " + (handle.isAlive() ? "En ejecución" : "Finalizado"));

                int codigoSalida = proceso.waitFor();
                System.out.println("Código de salida: " + codigoSalida);
            } catch (InterruptedException e) {
                System.out.println("La espera del proceso fue interrumpida.");
            }
        }
    }
}