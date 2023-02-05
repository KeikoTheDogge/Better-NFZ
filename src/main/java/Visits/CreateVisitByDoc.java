package Visits;


import DatabaseClass.DatabaseSession;
import entity.VisitsEntity;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;


public class CreateVisitByDoc {


    private final DatabaseSession databaseSession;

    public CreateVisitByDoc(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    /**
     * Metoda addNewVisit dodaję nową wizytę do bazy danych
     *
     * @param doctorId:  id doktora dodającego wizytę
     * @param visitType: typ wizyty
     * @param visitDate: data wizyty
     * @param visitTime: czas trwania wizyty
     */
    public void addNewVisit(int doctorId, String visitType, Date visitDate, Time visitTime) {
        Time endVisitTime = calculateVisitEndTime(visitType, visitTime);
        if (isVisitCollision(doctorId, visitDate, visitTime, endVisitTime)) {
            System.out.println("Nie można dodać wizyty - kolizja terminów");
            return;
        }

        VisitsEntity newVisit = new VisitsEntity();
        newVisit.setType(visitType);
        newVisit.setDate(visitDate);
        newVisit.setTimeFrom(visitTime);
        //tutaj wywoływania jest metoda, aby policzyć czas wizyty, żeby do bazy został dodany time_end
        newVisit.setTimeTo(calculateVisitEndTime(visitType, visitTime));
        newVisit.setDoctorId(doctorId);
        databaseSession.saveObject(newVisit);
    }

    /**
     * Metoda w zależności od podanego typu wizyty zlicza czas trwania wizyty
     *
     * @param visitType: typ wizyty
     * @param visitTime: czas trwania wizyty
     * @return: wartość czasu trwania wizyty
     */
    private Time calculateVisitEndTime(String visitType, Time visitTime) {
        LocalTime time = visitTime.toLocalTime();
        LocalTime endTime;
        switch (visitType) {
            case "PORADA" -> endTime = time.plusMinutes(15);
            case "BADANIE" -> endTime = time.plusMinutes(30);
            case "SZCZEPIENIE" -> endTime = time.plusMinutes(10);
            default -> throw new RuntimeException("Błędny typ wizyty");
        }
        return Time.valueOf(endTime);
    }

    /**
     * Metoda służy do wyszukiwania kolizji nowo dodawanej wizyty z wizytami już istniejącymi. Jeśli odpowiedź
     * na zapytanie danych nie zostanie znaleziona to znaczy że można dodać nową wizytę w przeciwnym wypadku nie.
     *
     * @param doctorId:     ID doktora dodającego wizytę
     * @param visitDate:    data dodawanej wizyty
     * @param visitTime:    godzina dodawanej wizyty
     * @param visitEndTime: godzina zakończenia nowej wizyty
     * @return false gdy nie ma kolizji True gdy jest
     */
    private boolean isVisitCollision(int doctorId, Date visitDate, Time visitTime, Time visitEndTime) {
        Query query = databaseSession.getSession().createNamedQuery("getCollideVisits", VisitsEntity.class);
        query.setParameter("doctorId", doctorId);
        query.setParameter("visitDate", visitDate);
        query.setParameter("timeStart", visitTime);
        query.setParameter("timeEnd", visitEndTime);
        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

    /**
     * Metoda służy do zebrania informacji o nowej wizycie i dodania jej do bazy danych.
     *
     * @param doctorId: ID doktora który dodaje wizytę
     */
    public void promptDoctorForNewVisit(int doctorId) {
        AddVisitCommand addVisitCommand = new AddVisitCommand();

        String visitType = addVisitCommand.getVisitType();
        Date date = addVisitCommand.getVisitData();
        Time time = addVisitCommand.getVisitTime();

        addNewVisit(doctorId, visitType, date, time);
    }
}

// w razie gdyby w mainie coś nie poszło podczas prezentacji
//    public static void main(String[] args) {
//        DatabaseSession databaseSession = new DatabaseSession();
//        databaseSession.openSession();
//        CreateVisitByDoc create = new CreateVisitByDoc(databaseSession);
//        create.promptDoctorForNewVisit(4);
//        databaseSession.closeSession();
//    }
//
//}

