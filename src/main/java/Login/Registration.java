package Login;

import DatabaseClass.DatabaseSession;
import entity.DoctorsEntity;
import entity.PatientsEntity;
import entity.VisitsEntity;
import jakarta.persistence.*;
import java.util.ArrayList;

public class Registration {
    private final DatabaseSession databaseSession;

    public Registration(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    public void addNewPatient(String name, String surname, String login, Password password) {
        PatientsEntity newPatient = new PatientsEntity();
        newPatient.setName(name);
        newPatient.setSurname(surname);
        newPatient.setLogin(login);
        newPatient.setPassword(password.getPassword());
        databaseSession.saveObject(newPatient);
    }

    public void deleteDoctorUser(DoctorsEntity doctor) {
        Query query = databaseSession.getSession().createNamedQuery("getVisitByDoctorId", VisitsEntity.class);
        query.setParameter("doctorId", doctor.getUserId());
        ArrayList<VisitsEntity> visits = (ArrayList<VisitsEntity>) query.getResultList();
        for (VisitsEntity visit: visits) {
            databaseSession.deleteObject(visit);
        }
        DoctorsEntity docToRemove = databaseSession.getSession().load(DoctorsEntity.class, doctor.getUserId());
        databaseSession.deleteObject(docToRemove);
        System.out.println("Konto lekarza usunięte");
    }

    public void deletePatientUser(PatientsEntity patient) {
        databaseSession.deleteObject(patient);
        System.out.println("Konto pacjenta usunięte");
    }
}
