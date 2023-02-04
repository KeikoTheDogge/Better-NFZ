package DatabaseClass;

import org.hibernate.Session;
import jakarta.persistence.*;
import org.hibernate.Transaction;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Klasa sesji z bazą danych. Posiada dwie metody: open session, która otwiera sesję z bazą oraz close session,
 * która zamyka sesję z bazą.
 */
public class DatabaseSession {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Session session;

    public void openSession() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        session = entityManager.unwrap(Session.class);

        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE);
    }

    public Session getSession() {
        return session;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void saveObject(Object entityObject) {
        session.save(entityObject);
    }

    public void deleteObject(Object entityObject) {
        Transaction transaction = session.beginTransaction();
        session.remove(entityObject);
        transaction.commit();
    }

    public void closeSession() {
        session.close();
        entityManager.close();
        entityManagerFactory.close();
    }
}