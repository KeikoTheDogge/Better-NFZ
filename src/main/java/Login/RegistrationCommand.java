package Login;

import java.util.Scanner;

/**
 * Klasa zawiera metodę z komendami do rejestracji nowego użytkownika
 */
public class RegistrationCommand {
    Scanner scanner;
    Registration registration;

    public RegistrationCommand(Scanner scanner, Registration registration) {
        this.scanner = scanner;
        this.registration = registration;
    }

    /**
     * Metoda registrationCommand zawiera prośby do użytkownika
     */
    public void registrationCommand() {
        System.out.println("Podaj swoje imię ");
        String name = scanner.next();
        System.out.println("Podaj swoje nazwisko ");
        String surname = scanner.next();
        System.out.println("Podaj login jakim chcesz się posługiwać");
        String login = scanner.next();
        System.out.println("Podaj hasło jakim chcesz się posługiwać");
        String pass = scanner.next();
        Password password = new Password(pass);
        System.out.println("Podaj swój adres:");
        String adress = scanner.next();
        registration.addNewPatient(name, surname, login, password, adress);
    }
}
