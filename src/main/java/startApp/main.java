package startApp;

import Login.Authentication;
import Login.Password;
import Login.Registration;
import Login.RegistrationCommand;
import Schedules.Schuedules;
import Visits.CreateVisitByDoc;
import DatabaseClass.DatabaseSession;
import DatabaseClass.showDocSpec;
import Visits.DeleteVisitbyDoc;
import Visits.RegisterforVisit;
import Visits.ShowAvailableVisits;
import entity.DoctorsEntity;
import entity.PatientsEntity;
import entity.UsersEntity;
import Reader.MenuReader;
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
            MenuReader.readFile("GeneralMenu.txt");
            String answer1 = scanner.next();
            switch (answer1) {
                case "1": {
                    Authentication authentication = new Authentication(databaseSession);
                    UsersEntity user;
                    System.out.println("Podaj login:");
                    String login = scanner.next();
                    System.out.println("Podaj hasło:");
                    String password = scanner.next();
                    try {
                        user = authentication.authenticate(login, password);
                        int logout = 1;
                        if (user instanceof PatientsEntity) {
                            do {
                                System.out.println("Witaj pacjencie " + user.getSurname() + ". Co chcesz dzisiaj zrobić?");
                                MenuReader.readFile("userMenuInfo.txt");
                                String answer2 = scanner.next();
                                switch (answer2) {
                                    case "1" -> {
                                        System.out.println("nie ma");
                                        int myId = user.getUserId();
                                        System.out.println("podaj nr id wizyty, która cię interesuje lub x jeśli chcesz " +
                                                "anulować: (aby zobaczyć wolne wizyty dla specjalizacji wybierz 5)");
                                        String answeridorx = scanner.next();
                                        if(answeridorx.equals("x")) {
                                            break;
                                        } else{
                                            RegisterforVisit registerforVisit = new RegisterforVisit(databaseSession);
                                            registerforVisit.SignupUser(Integer.parseInt(answeridorx),myId);
                                        }
                                    }
                                    case "4" -> {
                                        System.out.println("nie ma");
                                        int myId = user.getUserId();
                                        System.out.println("podaj nr id wizyty, którą chcesz odwołać lub x " +
                                                "jeśli chcesz anulować: (aby zobaczyć swoe wizyty wybierz 2)");
                                        String answeridorx = scanner.next();
                                        if(answeridorx.equals("x")){
                                            break;
                                        } else{
                                            RegisterforVisit registerforVisit = new RegisterforVisit(databaseSession);
                                            registerforVisit.CancelVisit(Integer.parseInt(answeridorx),myId);
                                        }
                                    }
                                    case "3" -> {
                                        System.out.println("nie ma");
                                        int myId = user.getUserId();
                                        System.out.println("podaj nr id wizyty, której datę chcesz zmienić lub x " +
                                                "jeśli chcesz anulować: (aby zobaczyć wolne wizyty dla specjalizacji wybierz 5)");
                                        String answeridorx = scanner.next();
                                        if(answeridorx.equals("x")){
                                            break;
                                        } else{
                                            RegisterforVisit registerforVisit = new RegisterforVisit(databaseSession);
                                            registerforVisit.CancelVisit(Integer.parseInt(answeridorx),myId);
                                        }
                                        System.out.println("podaj nr id wizyty na którą chcesz się zapisać lub x " +
                                                "jeśli chcesz anulować: (aby zobaczyć wolne wizyty dla specjalizacji wybierz 5) ");
                                        answeridorx = scanner.next();
                                        if (answeridorx.equals("x")){
                                            break;
                                        }else{
                                            RegisterforVisit registerforVisit = new RegisterforVisit(databaseSession);
                                            registerforVisit.SignupUser(Integer.parseInt(answeridorx),myId);
                                        }
                                    }
                                    case "2" -> {
                                        System.out.println("Podaj specjalizację, aby zobaczyć wolne terminy wizyt:");
                                        String specialization = scanner.next();
                                        ShowAvailableVisits show = new ShowAvailableVisits(databaseSession);
                                        show.showAvailableVisits(specialization);
                                    }
                                    case "5" -> {
                                        System.out.println("Podaj specjalizację, która cię interesuje:");
                                        String specialization = scanner.next();
                                        showDocSpec specs = new showDocSpec();
                                        List<DoctorsEntity> lekarze = specs.specDoctor(specialization);
                                        for (DoctorsEntity lekarz : lekarze) {
                                            System.out.println(lekarz.getSpecialization() + " " + lekarz.getName() + " " + lekarz.getSurname());
                                        }
                                    }
                                    case "6" -> {
                                        Schuedules schedule = new Schuedules(databaseSession);
                                        schedule.patientSchuedule(user.getUserId());
                                    }
                                    case "7" -> {
                                        System.out.println("Adam Malinowski [index]");
                                        System.out.println("Julia Weber [261659]");
                                        System.out.println("Tymoteusz Całka [261800]");
                                    }
                                    case "8" -> {
                                        System.out.println("Podaj swój login:");
                                        String delLogin = scanner.next();
                                        System.out.println("Podaj swoje hasło:");
                                        String delPass = scanner.next();
                                        if (delLogin.equals(user.getLogin()) && delPass.equals(user.getPassword())) {
                                            registration.deletePatientUser((PatientsEntity) user);
                                            logout = 0;
                                        } else {
                                            System.out.println("Błędne hasło lub login - konto nie zostało usunięte.");
                                        }
                                    }
                                    case "9" -> {
                                        logout = 0;
                                    }
                                    default ->
                                            System.out.println("Zły wybór - aby kontynuować wybierz poprawną cyfrę z menu:");
                                }
                            } while (logout != 0);

                        } else {
                            do {
                                System.out.println("Witaj doktorze " + user.getSurname() + ". Co chcesz dzisiaj zrobić?");
                                MenuReader.readFile("docMenuInfo.txt");
                                String answer3 = scanner.next();
                                switch (answer3) {
                                    case "1" -> {
                                        CreateVisitByDoc createVisitByDoc = new CreateVisitByDoc(databaseSession);
                                        createVisitByDoc.promptDoctorForNewVisit(user.getUserId());
                                    }
                                    case "2" -> {
                                        Schuedules docSchedule = new Schuedules(databaseSession);
                                        docSchedule.docSchuedule(user.getUserId());
                                    }
                                    case "3" -> {
                                        System.out.println("Adam Malinowski [index]");
                                        System.out.println("Julia Weber [261659]");
                                        System.out.println("Tymoteusz Całka [261800]");
                                    }
                                    case "4" -> {
                                        System.out.println("nie ma");
                                        int myId = user.getUserId();
                                        System.out.println("podaj nr id wizyty, której datę chcesz zmienić lub x " +
                                                "" + "jeśli chcesz anulować: (aby zobaczyć wolne wizyty dla specjalizacji wybierz 5)");
                                        String answeridorx = scanner.next();
                                        if(answeridorx.equals("x")){
                                            break;
                                        } else{
                                            DeleteVisitbyDoc deleteVisitbyDoc = new DeleteVisitbyDoc(databaseSession);
                                            if(deleteVisitbyDoc.CheckifEmpty(Integer.parseInt(answeridorx)) == 0){
                                                System.out.println("can't delete a visit already occupied place the patient into a diffrent one");
                                                int idPatient = deleteVisitbyDoc.GetPatientId(Integer.parseInt(answeridorx),myId);
                                                RegisterforVisit registerforVisit = new RegisterforVisit(databaseSession);
                                                deleteVisitbyDoc.DeleteVisit(Integer.parseInt(answeridorx),myId);
                                                System.out.println("Podaj nr id wizyty do której chcesz przypisać pacjenta");
                                                int visitid2 = scanner.nextInt();
                                                scanner.nextLine();
                                                registerforVisit.SignupUser(visitid2,idPatient);
                                            }else{
                                                deleteVisitbyDoc.DeleteVisit(Integer.parseInt(answeridorx),myId);
                                            }
                                        }
                                    }
                                    case "5" -> {
                                        System.out.println("Podaj swój login:");
                                        String delLogin = scanner.next();
                                        System.out.println("Podaj swoje hasło:");
                                        String delPass = scanner.next();
                                        if (delLogin.equals(user.getLogin()) && delPass.equals(user.getPassword())) {
                                            registration.deleteDoctorUser((DoctorsEntity) user);
                                            logout = 0;
                                        } else {
                                            System.out.println("Błędne hasło lub login - konto nie zostało usunięte");
                                        }
                                    }
                                    case "6" -> {
                                        logout = 0;
                                    }
                                    default ->
                                            System.out.println("Zły wybór - aby kontynuować wybierz poprawną cyfrę z menu:");
                                }
                            } while (logout != 0);
                        }
                    } catch (NoResultException ex) {
                        System.out.println("Taki użytkownik nie istnieje.");
                    }
                    break;
                }
                case "2": {
                    RegistrationCommand command = new RegistrationCommand(scanner, registration);
                    command.registrationCommand();
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
