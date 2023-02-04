package startApp;

import Login.Authentication;
import Login.Password;
import Login.Registration;
import Schedules.DocSchedule;
import Visits.CreateVisitByDoc;
import DatabaseClass.DatabaseSession;
import DatabaseClass.showDocSpec;
import entity.DoctorsEntity;
import entity.PatientsEntity;
import entity.UsersEntity;
import jakarta.persistence.*;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        DatabaseSession databaseSession = new DatabaseSession();
        Registration registration = new Registration(databaseSession);
        databaseSession.openSession();
        label:
        while (true) {
            System.out.println("Wybierz [1] aby się zalogować. Wybierz [2] aby założyć konto. [3] aby zakończyć działanie programu");
            String answer1 = scanner.next();
            switch (answer1) {
                case "1": {
                    Authentication authentication = new Authentication(databaseSession);
                    UsersEntity user;
                    System.out.println("Podaj login");
                    String login = scanner.next();
                    System.out.println("Podaj hasło");
                    String password = scanner.next();
                    try {
                        user = authentication.authenticate(login, password);
                        int logout = 1;
                        if (user instanceof PatientsEntity) {
                            do {
                                System.out.println("Witaj pacjencie " + user.getSurname() + " Wybierz operację.");
                                try (BufferedReader br = new BufferedReader(new FileReader("userMenuInfo.txt"))) {
                                    String line = br.readLine();
                                    while (line != null) {
                                        System.out.println(line);
                                        line = br.readLine();
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                String answer2 = scanner.next();
                                switch (answer2) {
                                    case "1", "2", "3", "4", "6" -> System.out.println("work in progress");
                                    case "5" -> {
                                        System.out.println("Podaj specjalizację, która cię interesuje");
                                        String specialization = scanner.next();
                                        showDocSpec specs = new showDocSpec();
                                        List<DoctorsEntity> lekarze = specs.specDoctor(specialization);
                                        for (DoctorsEntity lekarz : lekarze) {
                                            System.out.println(lekarz.getSpecialization() + " " + lekarz.getName() + " " + lekarz.getSurname());
                                        }
                                    }
                                    case "7" -> {
                                        System.out.println("Adam Malinowski [index]");
                                        System.out.println("Julia Weber [index]");
                                        System.out.println("Tymoteusz Całka [261800]");
                                    }
                                    case "8" -> {
                                        System.out.println("Podaj swój login");
                                        String delLogin = scanner.next();
                                        System.out.println("Podaj swoje hasło");
                                        String delPass = scanner.next();
                                        registration.deletePatientUser((PatientsEntity) user);
                                        logout = 0;
                                    }
                                    case "9" -> {
                                        logout = 0;
                                    }
                                    default ->
                                            System.out.println("Zły wybór. Aby kontynuować wybierz poprawną cyfrę z menu:");
                                }
                            } while (logout != 0);

                        } else {
                            do {
                                System.out.println("Witaj doktorze " + user.getSurname() + " Wybierz operację");

                                try (BufferedReader br = new BufferedReader(new FileReader("docMenuInfo.txt"))) {
                                    String line = br.readLine();
                                    while (line != null) {
                                        System.out.println(line);
                                        line = br.readLine();
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                String answer3 = scanner.next();
                                switch (answer3) {
                                    case "1" -> {
                                        CreateVisitByDoc createVisitByDoc = new CreateVisitByDoc(databaseSession);
                                        createVisitByDoc.promptDoctorForNewVisit(user.getUserId());

                                    }
                                    case "2" -> {
                                        DocSchedule docSchedule = new DocSchedule(databaseSession);
                                        docSchedule.schuedule(user.getUserId());
                                    }
                                    case "3" -> {
                                        System.out.println("Adam Malinowski [index]");
                                        System.out.println("Julia Weber [index]");
                                        System.out.println("Tymoteusz Całka [261800]");
                                    }
                                    case "4" -> {
                                        System.out.println("Podaj swój login");
                                        String delLogin = scanner.next();
                                        System.out.println("Podaj swoje hasło");
                                        String delPass = scanner.next();
                                        registration.deleteDoctorUser((DoctorsEntity) user);
                                        logout = 0;
                                    }
                                    case "5" -> {
                                        logout = 0;
                                    }
                                    default ->
                                            System.out.println("Zły wybór. Aby kontynuować wybierz poprawną cyfrę z menu:");
                                }
                            } while (logout != 0);
                        }
                    } catch (NoResultException ex) {
                        System.out.println("Taki użytkownik nie istnieje.");
                    }
                    break;
                }
                case "2": {
                    System.out.println("Podaj swoje imię ");
                    String name = scanner.next();
                    System.out.println("Podaj swoje nazwisko ");
                    String surname = scanner.next();
                    System.out.println("Podaj login jakim chcesz się posługiwać");
                    String login = scanner.next();
                    System.out.println("Podaj hasło jakim chcesz się posługiwać");
                    String pass = scanner.next();
                    Password password = new Password(pass);
                    registration.addNewPatient(name, surname, login, password);
                    break;
                }
                case "3":
                    System.out.println("Dziękujemy za użycie naszej aplikacji");
                    break label;
                default:
                    System.out.println("Wybór podany źle, podaj 1 lub 2");
                    break;
            }
        }
        databaseSession.closeSession();
    }
}
