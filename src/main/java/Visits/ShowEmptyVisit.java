package Visits;

import DatabaseClass.DatabaseSession;
import entity.VisitsEntity;
import jakarta.persistence.Query;

import java.util.ArrayList;

public class ShowEmptyVisit {
    private final DatabaseSession databaseSession;


    public ShowEmptyVisit(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    public void showEmptyVisit(String specialization) {
        Query query = databaseSession.getSession().createNamedQuery("getVisitBySpecialisation", VisitsEntity.class);
        query.setParameter("spec", specialization);
        ArrayList<VisitsEntity> visits = (ArrayList<VisitsEntity>) query.getResultList();
        System.out.println("Wolne wizyty:");
        for (VisitsEntity v: visits) {
            System.out.println(String.format("Data: %s, godzina: %s, typ: %s",
                    v.getDate(), v.getTimeFrom(), v.getType()));
        }
    }
}
