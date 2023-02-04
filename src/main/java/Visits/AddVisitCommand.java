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
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setLenient(false);
        timeFormatter = new SimpleDateFormat("kk:mm");
        timeFormatter.setLenient(false);
    }

    public Date getVisitData() {
        System.out.println("Podaj datę wizyty: (yyyy-mm-dd)");
        String date = scanner.next();

        Date parsedVisitDate = null;
        do {
            try {
                parsedVisitDate = new Date(dateFormatter.parse(date).getTime());
            } catch (ParseException e) {
                System.out.println("Niewłaściwy format daty. Podaj jeszcze raz w formacie yyyy-mm-dd:");
                date = scanner.next();
            }
        } while (parsedVisitDate==null);
        return parsedVisitDate;
    }

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
        } while (parsedVisitTime==null);
        return parsedVisitTime;
    }

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

        return visitType;
    }

}
