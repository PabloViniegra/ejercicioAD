package views;

import models.Rugby;

import java.util.Scanner;

public class View {
    public void menu() {
        System.out.println("MENÚ");
        System.out.println("1. Escribir Linea");
        System.out.println("2. Mostrar estadios por encima de la media");
        System.out.println("3. Mostrar el país que más tantos ha marcado");
        System.out.println("4. Mostrar el país que menos tantos ha marcado");
        System.out.println("5. Mostrar país que más tantos ha recibido");
        System.out.println("6. Mostrar información");
        System.out.println("7. Mostrar clasificación");
    }

    public Rugby requestData (Scanner s) {
        Rugby r = new Rugby();
        System.out.print("Fecha: ");
        r.setFecha(s.nextLine());
        System.out.print("Capacidad: ");
        r.setCapacidad(s.nextInt());
        s.nextLine();
        System.out.print("Estadio: ");
        r.setEstadio(s.nextLine());
        System.out.print("Equipo Local: ");
        r.setLocal(s.nextLine());
        System.out.print("Equipo visitante: ");
        r.setVisitante(s.nextLine());
        s.nextLine();
        System.out.print("¿Se ha aplazado el Partido?[Si/No]");
        String respuesta = s.nextLine();
        if (respuesta.equalsIgnoreCase("No")){
            System.out.print("Tantos local: ");
            r.setTantosLocal(s.nextInt());
            System.out.print("Tantos Visitante: ");
            r.setTantosVisitante(s.nextInt());
            s.nextLine();
        } else {
            r.setTantosVisitante(0);
            r.setTantosLocal(0);
        }
        return r;
    }
}
