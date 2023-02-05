package Visits;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVisitCommand {

    private static SimpleDateFormat dateFormatter;

    private static SimpleDateFormat timeFormatter;

    private final Scanner scanner;

    public AddVisitCommand() {
        scanner = new Scanner(System.in);
        // SimpleDateFormat powala nam na wymuszenie na użytkowniku podania wejściowych danych w odpowiednim formacie
        // (automatycznie konwertuje nawet źle podane dane)
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        // setLenient spowoduje, że źle wpisane dane nie zostaną automatycznie przekonwertowane do zadanego powyżej formatu,
        // tylko będzie wymagane aby w takim formacie użytkownik podał dane
        dateFormatter.setLenient(false);
        timeFormatter = new SimpleDateFormat("kk:mm");
        timeFormatter.setLenient(false);
    }

    /**
     * Metoda zamienia podanego przez użytkownika Stringa z datą na typ date, który można wprowadzić do bazy danych
     *
     * @return: typ daty, który można dodać do bazy danych
     */
    public Date getVisitData() {
        System.out.println("Podaj datę wizyty: (yyyy-mm-dd)");
        String date = scanner.next();

        Date parsedVisitDate = null;
        do {
            try {
                parsedVisitDate = new Date(dateFormatter.parse(date).getTime());
                // Parsowanie daty do timestampu i tworzenie obiektu sql.Date wymagane przez VisitEntity.setDate
            } catch (ParseException e) {
                // jeśli użytkownik poda datę z złym formacie i niemożliwe jest jej sparsowanie, program, wyrzuca wyjątek
                System.out.println("Niewłaściwy format daty. Podaj jeszcze raz w formacie yyyy-mm-dd:");
                date = scanner.next();
            }
        } while (parsedVisitDate == null);
        return parsedVisitDate;
    }

    /**
     * Metoda zamienia podanego przez użytkownika Stringa z czasem na typ time, który można wprowadzić do bazy danych
     *
     * @return: typ czasu, który można dodać do bazy danych
     */
    public Time getVisitTime() {
        System.out.println("Podaj godzinę wizyty: (hh:mm)");
        String date = scanner.next();

        Time parsedVisitTime = null;
        do {
            try {
                parsedVisitTime = new Time(timeFormatter.parse(date).getTime());
            } catch (ParseException e) {
                System.out.println("Niewłaściwy format daty. Podaj jeszcze raz w formacie hh:mm");
                date = scanner.next();
            }
        } while (parsedVisitTime == null);
        return parsedVisitTime;
    }

    /**
     * Metoda sprawdza jaki typ wizyty podał doktor i wymusza na nim podanie typu wizyty sposród jednego z trzech
     * dostępnych
     *
     * @return: typ wizyty
     */
    public String getVisitType() {
        boolean patternFound = false;
        String visitType;
        do {
            System.out.println("Podaj typ wizyty: (PORADA, BADANIE, SZCZEPIENIE)");
            visitType = scanner.next();
            Pattern visitPattern = Pattern.compile("^PORADA|BADANIE|SZCZEPIENIE$");
            Matcher matcher = visitPattern.matcher(visitType);
            patternFound = matcher.find();
            if (!patternFound)
                System.out.println("Zly typ wizyty. Użyj jednego z PORADA, BADANIE lub SZCZEPIENIE");
        } while (!patternFound);
        // jeśli typ jest dobry to wychodzi z pętli while
        return visitType;
    }

}
