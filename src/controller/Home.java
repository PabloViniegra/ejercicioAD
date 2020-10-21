package controller;

import models.Country;
import models.Rugby;
import views.View;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Home {
    /*Descripción del ejercicio*/
    /*Es un fichero CSV. Hacer una aplicación que gestione un torneo
     * de 6 naciones e Rugby. 1º Leer en un archivo CSV los datos separandolos por punto y coma
     * 2º Mostrar el nombre de los estadios que tienen una capacidad superior a la media. 3º
     * Mostrar el país que más tantos ha marcado. 4º Mostrar el país que menos tantos ha marcado.5º Incluir
     * menú con opciones5 Mostrar el equipo que más tantos ha recibido. 6 presentar en pantalla el fichero. 7 Sacar la clasificación de los equipos
     * dando 3 puntos por ganar, 1 por empatar y 0 por perder. En caso de empate a puntos saldrá primero el que tenga la mejor diferencia de tantos(tantos totales marcados - tantos totales encajados).
     * Tres columnas equipos, puntos y diferencia de goles en contra */
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
                case 5:
                    showCountryWithMorePointsAgainst(file);
                    break;
                case 6:
                    showInformation(file);
                    break;
                case 7:
                    classificationBuild(file);
                    break;
                default:
                    System.out.println("Dato erróneo");
                    break;
            }
        } while (opcion != 0);

    }

    //Escribe líneas en el fichero, con el objeto Rugby
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

    //Lee el fichero rugby.csv y muestra todas las líneas
    public static void readInFile(File file) {

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                System.out.println(tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3] + " " + tokens[4] + " " + tokens[5] + " " + tokens[6]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Muestra aquellos estadios que tienen más capacidad de la media
    public static void showCapacityOverAverage(File file) {
        float average = 0;
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<Integer> capacidades = new ArrayList<>();
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                capacidades.add(Integer.parseInt(tokens[1]));
            }
            average = getAverage(capacidades);


        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader bf2 = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf2.readLine()) != null) {
                String[] tokens = line.split(";");
                if (Float.parseFloat(tokens[1]) > average)
                    System.out.println(tokens[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Llena el array de Países
    public static void fillCountries(File file, ArrayList<Country> countries) {

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            int localPoints;
            int visitantPoints;
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                Country countryLocal = new Country();
                countryLocal.setCountry(tokens[3]);
                Country countryVisitant = new Country();
                countryVisitant.setCountry(tokens[4]);
                if (Integer.parseInt(tokens[5]) > Integer.parseInt(tokens[6])) {
                    localPoints = 3;
                    visitantPoints = 0;
                } else if (Integer.parseInt(tokens[5]) < Integer.parseInt(tokens[6])) {
                    localPoints = 0;
                    visitantPoints = 3;
                } else {
                    localPoints = 1;
                    visitantPoints = 1;
                }
                if (countries.isEmpty()) {
                    createCountry(countries, localPoints, tokens, countryLocal, 5, 6);
                    createCountry(countries, visitantPoints, tokens, countryVisitant, 6, 5);
                } else {
                    boolean foundLocal = false;
                    boolean foundVisitant = false;
                    for (Country country : countries) {
                        if (country.getCountry().equalsIgnoreCase(tokens[3])) {
                            country.setPoints(country.getPoints() + Integer.parseInt(tokens[5]));
                            country.setTotalPoints(country.getTotalPoints() + localPoints);
                            country.setDifference(country.getDifference() + (Integer.parseInt(tokens[5]) - Integer.parseInt(tokens[6])));
                            foundLocal = true;
                        }
                        if (country.getCountry().equalsIgnoreCase(tokens[4])) {
                            country.setPoints(country.getPoints() + Integer.parseInt(tokens[6]));
                            country.setTotalPoints(country.getTotalPoints() + visitantPoints);
                            country.setDifference(country.getDifference() + (Integer.parseInt(tokens[6]) - Integer.parseInt(tokens[5])));
                            foundVisitant = true;
                        }
                    }
                    if (!foundLocal) {
                        createCountry(countries, localPoints, tokens, countryLocal, 5, 6);
                    }
                    if (!foundVisitant) {
                        createCountry(countries, visitantPoints, tokens, countryVisitant, 6, 5);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createCountry(ArrayList<Country> countries, int localPoints, String[] tokens, Country countryLocal, int i, int i2) {
        countryLocal.setPoints(Integer.parseInt(tokens[i]));
        countryLocal.setTotalPoints(localPoints);
        countryLocal.setDifference(Integer.parseInt(tokens[i]) - Integer.parseInt(tokens[i2]));
        countries.add(countryLocal);
    }

    //Compara una lista de paises y saca el páis con más puntos
    public static void showCountryWithMorePoints(File file) {
        ArrayList<Country> countries = new ArrayList<>();
        fillCountries(file, countries);
        Country countryWinner = new Country();
        for (Country country : countries) {
            if (country.getPoints() > countryWinner.getPoints()) {
                countryWinner = country;
            }
        }
        System.out.println("El ganador es " + countryWinner.getCountry() + " con " + countryWinner.getPoints());
    }

    public static void classificationBuild(File file) {

        ArrayList<Country> countries = new ArrayList<>();
        fillCountries(file, countries);
        System.out.println("EQUIPO       PUNTOS      TOTAL AVERAGE");
        orderCountries(countries);
        for (Country country : countries) {
            System.out.println(country.getCountry() + "   " + country.getTotalPoints() + "   " + country.getDifference());
        }

    }

    private static void orderCountries(ArrayList<Country> countries) {
        countries.sort((o1, o2) -> {
            if (o1.getTotalPoints() > o2.getTotalPoints()) {
                return -1;
            } else if (o1.getTotalPoints() < o2.getTotalPoints()) {
                return 1;
            } else {
                if (o1.getDifference() > o2.getDifference()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    public static void showInformation(File file) {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("FECHA        LOCAL       VISITANTE");
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens[5].equalsIgnoreCase("X")) {
                    System.out.println(transformDateString(tokens[0]) + "   " + tokens[3] + "-" + tokens[5] + "   " + "APLAZADO");
                } else {
                    System.out.println(transformDateString(tokens[0]) + "   " + tokens[3] + "-" + tokens[5] + "   " + tokens[4] + "-" + tokens[6]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String transformDateString(String s) {
        String[] parts = s.split("-");
        String day = parts[2];
        String month = parts[1];
        String year = parts[0];
        return (day.concat("/").concat(month).concat("/").concat(year));
    }

    //retorna la media de un array
    public static float getAverage(ArrayList<Integer> array) {
        int totalNum = 0;
        for (Integer integer : array) {
            totalNum += integer;
        }
        return (float) (totalNum / array.size());
    }

    //Compara una lista de paises y saca el páis con menos puntos
    public static void showCountryWithLessPoints(File file) {
        ArrayList<Country> countries = new ArrayList<>();
        fillCountries(file, countries);
        Country countryWinner = new Country();
        for (Country country : countries) {
            if (country.getPoints() < countryWinner.getPoints() || countryWinner.getPoints() == 0) {
                countryWinner = country;
            }
        }
        System.out.println("El ganador es " + countryWinner.getCountry() + " con " + countryWinner.getPoints());
    }

    public static void fillCountriesNegative(File file, ArrayList<Country> countries) {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(";");
                Country countryLocal = new Country();
                countryLocal.setCountry(tokens[3]);
                Country countryVisitant = new Country();
                countryVisitant.setCountry(tokens[4]);
                if (countries.isEmpty()) {
                    countryLocal.setPoints(Integer.parseInt(tokens[6]));
                    countries.add(countryLocal);
                    countryVisitant.setPoints(Integer.parseInt(tokens[5]));
                    countries.add(countryVisitant);
                } else {
                    boolean foundLocal = false;
                    boolean foundVisitant = false;
                    for (Country country : countries) {
                        if (country.getCountry().equalsIgnoreCase(tokens[3])) {
                            country.setPoints(country.getPoints() + Integer.parseInt(tokens[6]));
                            foundLocal = true;
                        }
                        if (country.getCountry().equalsIgnoreCase(tokens[4])) {
                            country.setPoints(country.getPoints() + Integer.parseInt(tokens[5]));
                            foundVisitant = true;
                        }
                    }
                    if (!foundLocal) {
                        countryLocal.setPoints(Integer.parseInt(tokens[6]));
                        countries.add(countryLocal);
                    }
                    if (!foundVisitant) {
                        countryVisitant.setPoints(Integer.parseInt(tokens[5]));
                        countries.add(countryVisitant);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCountryWithMorePointsAgainst(File file) {
        ArrayList<Country> countries = new ArrayList<>();
        fillCountriesNegative(file, countries);
        Country countryLoser = new Country();
        for (Country country : countries) {
            if (country.getPoints() > countryLoser.getPoints()) {
                countryLoser = country;
            }
        }
        System.out.println("El país con más puntos en contra es " + countryLoser.getCountry() + " con " + countryLoser.getPoints());
    }
}
