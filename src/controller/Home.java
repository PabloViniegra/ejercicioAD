package controller;

import models.Rugby;
import views.View;

import java.io.*;
import java.util.Scanner;

public class Home {
    /*Descripción del ejercicio*/
    /*Es un fichero CSV. Hacer una aplicación que gestione un torneo
     * de 6 naciones e Rugby. 1º Leer en un archivo CSV los datos separandolos por punto y coma
     * 2º Mostrar el nombre de los estadios que tienen una capacidad superior a la media. 3º
     * Mostrar el país que más tantos ha marcado. 4º Mostrar el país que menos tantos ha marcado.5º Incluir
     * menú con opciones*/
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        View view = new View();
        Rugby rugby;
        File file = new File("rugby.csv");
        int opcion;
        do {
            view.menu();
            System.out.print("Introduzca opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    rugby = new Rugby();
                    rugby = view.requestData(sc);
                    writeInFile(rugby, file);
                    readInFile(file);
                    break;
                case 2:
                    showCapacityOverAverage(file);
                    break;
                case 3:
                    showCountryWithMorePoints(file);
                    break;
                case 4:
                    showCountryWithLessPoints(file);
                    break;
                default:
                    System.out.println("Dato erróneo");
                    break;
            }
        } while (opcion != 0);

    }

    public static void writeInFile(Rugby r, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (r.getTantosVisitante() == 0 && r.getTantosLocal() == 0) {
                bw.write(r.getFecha() + ";" + r.getCapacidad() + ";" + r.getEstadio() + ";" + r.getLocal() + ";" + r.getVisitante() + ";" + "X;\n");
            } else {
                bw.write(r.getFecha() + ";" + r.getCapacidad() + ";" + r.getEstadio() + ";" + r.getLocal() + ";" + r.getVisitante() + ";" + r.getTantosLocal() + ";" + r.getTantosVisitante() + ";\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readInFile(File file) {

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3] + " " + tokens[4] + " " + tokens[5] + " " + tokens[6]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCapacityOverAverage(File file) {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            int contador = 0;
            int lineas = 1;
            String estadio = "";
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                contador += Integer.parseInt(tokens[1]);
                lineas++;

            }
            float average = contador / lineas;
            String aux;
            while ((aux = bf.readLine()) != null) {
                String[] tokens = aux.split(";");
                if (Float.parseFloat(tokens[1]) > average) {
                    System.out.println(tokens[2]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCountryWithMorePoints(File file) {

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            int totalLocalPoints = 0, totalVisitantPoints = 0;
            String line;
            String country = "";
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                if (Float.parseFloat(tokens[5]) > totalLocalPoints) {
                    totalLocalPoints += Integer.parseInt(tokens[5]);
                    country = tokens[5];
                }
                if (Float.parseFloat(tokens[6]) > totalVisitantPoints) {
                    totalVisitantPoints += Integer.parseInt(tokens[6]);
                    country = tokens[6];
                }
            }
            System.out.println("El pais con más puntos es: " + country);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCountryWithLessPoints(File file) {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            int totalLocalPoints = 0, totalVisitantPoints = 0;
            String line;
            String country = "";
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                if (Integer.parseInt(tokens[5]) < totalLocalPoints) {
                    totalLocalPoints += Integer.parseInt(tokens[5]);
                    country = tokens[5];
                }
                if (Integer.parseInt(tokens[6]) < totalVisitantPoints) {
                    totalVisitantPoints += Integer.parseInt(tokens[6]);
                    country = tokens[6];
                }
            }
            System.out.println("El pais con menos puntos es: " + country);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
