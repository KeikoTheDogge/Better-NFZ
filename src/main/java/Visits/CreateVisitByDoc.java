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
        newVisit.setTimeTo(calculateVisitEndTime(visitType, visitTime));
        newVisit.setDoctorId(doctorId);
        databaseSession.saveObject(newVisit);
    }

    private Time calculateVisitEndTime(String visitType, Time visitTime) {
        LocalTime time = visitTime.toLocalTime();
        LocalTime endTime = visitTime.toLocalTime();
        switch (visitType) {
            case "PORADA" -> endTime = time.plusMinutes(15);
            case "BADANIE" -> endTime = time.plusMinutes(30);
            case "SZCZEPIENIE" -> endTime = time.plusMinutes(10);
        }
        return Time.valueOf(endTime);
    }

    private boolean isVisitCollision(int doctorId, Date visitDate, Time visitTime, Time visitEndTime) {
        Query query = databaseSession.getSession().createNamedQuery("getCollideVisits", VisitsEntity.class);
        query.setParameter("doctorId", doctorId);
        query.setParameter("visitDate", visitDate);
        query.setParameter("timeStart", visitTime);
        query.setParameter("timeEnd", visitEndTime);
        try {
            query.getSingleResult();
        } catch(NoResultException e) {
            return false;
        }
        return true;
    }

    public void promptDoctorForNewVisit(int doctorId) {
        AddVisitCommand addVisitCommand = new AddVisitCommand();

        String visitType = addVisitCommand.getVisitType();
        Date date = addVisitCommand.getVisitData();
        Time time = addVisitCommand.getVisitTime();

        addNewVisit(doctorId, visitType, date, time);
    }



    public static void main(String[] args) {
        int doctorId = 3;
        DatabaseSession databaseSession = new DatabaseSession();
        databaseSession.openSession();
        CreateVisitByDoc create = new CreateVisitByDoc(databaseSession);
        create.promptDoctorForNewVisit(3);

        databaseSession.closeSession();
    }

}
