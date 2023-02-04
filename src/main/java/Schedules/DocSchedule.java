package Schedules;

import DatabaseClass.DatabaseSession;
import entity.DoctorsEntity;
import entity.UsersEntity;
import entity.VisitsEntity;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Schuedule zawiera dwie metody: docSchuedule, która printuje terminarz lekarza i patientSchuedule,
 * która printuje terminarz pacjenta. Posiada jedno pole, w którym tworzy obiket klasy databaseSession
 * potrzebny do połączenia się metod z bazą danych.
 */
public class DocSchedule {
    private final DatabaseSession databaseSession;

    public DocSchedule(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    /**
     * Metoda schuedule printuje terminarz doktora, wszystkie wizyty, jakie będzie miał w przyszłości.
     * @param doctorId: ID doktora, dla którego wyświetlany jest terminarz
     */
    public void schuedule(int doctorId) {
        Query query = databaseSession.getSession().createNamedQuery("getDoctorSchuedule", VisitsEntity.class);
        query.setParameter("doctorId", doctorId);
        ArrayList<VisitsEntity> visits = (ArrayList<VisitsEntity>) query.getResultList();
        System.out.println("Nadchodzące wizyty:");
        for (VisitsEntity v: visits) {
            System.out.println(String.format("Data: %s, godzina: %s, typ: %s",
                    v.getDate(), v.getTimeFrom(), v.getType()));
        }
    }

    public void patientSchuedule(int patientId) {
        Query query = databaseSession.getSession().createNamedQuery("getPatientSchuedule", VisitsEntity.class);
        query.setParameter("patientId", patientId);
        ArrayList<Tuple> visits = (ArrayList<Tuple>) query.getResultList();
        System.out.println("Nadchodzące wizyty");
        for (Tuple v: visits) {
            VisitsEntity wizyta = (VisitsEntity) v.get(0);
            DoctorsEntity doctor = (DoctorsEntity) v.get(1);
            System.out.println(wizyta.getDate());
        }
//        System.out.println(visits.get(0));
//        for (Tuple v: visits) {
//            VisitsEntity wizyta = (VisitsEntity) v.get(0);
//            DoctorsEntity doctor = (DoctorsEntity) v.get(1);
//            System.out.println(wizyta.getDate() + doctor.getSpecialization());
//        }

    }




    public static void main(String[] args) {
        DatabaseSession databaseSession1 = new DatabaseSession();
        databaseSession1.openSession();
        DocSchedule docSchedule = new DocSchedule(databaseSession1);
        docSchedule.schuedule(3);
        docSchedule.patientSchuedule(1);
    }
}
