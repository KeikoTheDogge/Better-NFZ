package Visits;

import DatabaseClass.DatabaseSession;
import entity.DoctorsEntity;
import entity.VisitsEntity;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.ArrayList;

/**
 * Klasa ShowAvailableVisits pokazuje wolne wizyty u wybranego przez pacjenta specjalisty. W informacji zwroten
 * oprócz daty, godziny i typu wizyty znajduje się imię i nazwisko lekarza.
 */
public class ShowAvailableVisits {
    private final DatabaseSession databaseSession;


    public ShowAvailableVisits(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    /**
     * Metoda showAvailableVisit wyjmuje z bazy danych informacje o wolnych wizytach i przypisanych do nich lekarzach
     * na podstawie specjalizacji podanej przez pacjenta
     *
     * @param specialization: specjalizacja podana przez pacjenta
     */
    public void showAvailableVisits(String specialization) {
        Query query = databaseSession.getSession().createNamedQuery("getVisitBySpecialisation", Tuple.class);
        query.setParameter("spec", specialization);
        ArrayList<Tuple> visits = (ArrayList<Tuple>) query.getResultList();
        System.out.println("Wolne wizyty:");
        for (Tuple v : visits) {
            VisitsEntity visit = (VisitsEntity) v.get(0);
            DoctorsEntity doctor = (DoctorsEntity) v.get(1);
            System.out.println(String.format("IDwizyta: %s, Data: %s, godzina: %s, typ: %s u doktora: %s %s",
                    visit.getVisitId(), visit.getDate(), visit.getTimeFrom(), visit.getType(), doctor.getName(),
                    doctor.getSurname()));
        }
    }
}
