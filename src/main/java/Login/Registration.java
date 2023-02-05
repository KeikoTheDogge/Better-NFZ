package Login;

import DatabaseClass.DatabaseSession;
import entity.DoctorsEntity;
import entity.PatientsEntity;
import entity.VisitsEntity;
import jakarta.persistence.*;

import java.util.ArrayList;

/**
 * Klasa Registration służy do rejestrowania nowego użytkownika (wyłącznie pacjenta) oraz do usuwania użytkownika
 * (zarówno pacjenta jak i lekarza)
 */
public class Registration {
    private final DatabaseSession databaseSession;

    public Registration(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    /**
     * Metoda addNewPatient dodaje do bazy danych wprowadzone przez użytkownika informacje o pacjencie (tworzy nowe
     * konto pacjenta na podstawie wprowadzanych danych).
     *
     * @param name:     imię wprowadzone przez użytkownika
     * @param surname:  nazwisko wprowadzone przez użytkownika
     * @param login:    login wprowadzony przez użytkownika
     * @param password: hasło wprowadzone przez użytkownika
     */
    public void addNewPatient(String name, String surname, String login, Password password, String adress) {
        PatientsEntity newPatient = new PatientsEntity();
        newPatient.setName(name);
        newPatient.setSurname(surname);
        newPatient.setLogin(login);
        newPatient.setAdres(adress);
        newPatient.setPassword(password.getPassword());
        databaseSession.saveObject(newPatient);
    }

    /**
     * Metoda deleteDoctorUser usuwa użytkownika doktora z bazy danych.
     *
     * @param doctor: obiekt doktor (kompletny zespół informacji o doktorze)
     */
    public void deleteDoctorUser(DoctorsEntity doctor) {
        Query query = databaseSession.getSession().createNamedQuery("getVisitByDoctorId", VisitsEntity.class);
        query.setParameter("doctorId", doctor.getUserId());
        ArrayList<VisitsEntity> visits = (ArrayList<VisitsEntity>) query.getResultList();
        //usuwane są wszystkie wizyty usuwanego lekarza
        for (VisitsEntity visit : visits) {
            databaseSession.deleteObject(visit);
        }
        //usuwany profil lekarza
        DoctorsEntity docToRemove = databaseSession.getSession().load(DoctorsEntity.class, doctor.getUserId());
        databaseSession.deleteObject(docToRemove);
        System.out.println("Konto lekarza usunięte");
    }

    /**
     * Metoda deletePatientUser usuwa użytkownika pacjenta z bazy danych.
     *
     * @param patient: obiekt pacjent (kompletny zespół informacji o pacjencie)
     */
    public void deletePatientUser(PatientsEntity patient) {
        // query zostało stworzone po to, aby móc usunąć pacjenta z wszystkich wizyt, do których był przypisany
        // gdyż bez tego nie mógłby zostać usunięty gdyby posiadał przypisaną do siebie wizytę
        Query query = databaseSession.getSession().createNamedQuery("getVisitByPatientId", VisitsEntity.class);
        query.setParameter("patientId", patient.getUserId());
        ArrayList<VisitsEntity> visits = (ArrayList<VisitsEntity>) query.getResultList();
        for (VisitsEntity v : visits) {
            // tutaj podmieniany jest patientId na nulla, żeby wizyty już nie były przypisane do usuwanego użytkownika
            v.setPatientId(null);
            databaseSession.saveObject(v);
        }
        databaseSession.deleteObject(patient);
        System.out.println("Konto pacjenta usunięte");
    }
}
