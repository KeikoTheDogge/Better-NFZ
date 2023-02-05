package DatabaseClass;

import org.hibernate.Session;
import jakarta.persistence.*;
import org.hibernate.Transaction;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Klasa sesji z bazą danych. Posiada dwie metody: openSession, która otwiera sesję z bazą oraz closeSession,
 * która zamyka sesję z bazą.
 */
public class DatabaseSession {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Session session;

    /**
     * Metoda openSession otwiera sesję z bazą danych.
     */
    public void openSession() {
        // kod, który ukrywa logi pokazujące się w konsoli podczas łączenia się programu z bazą danych
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE);

        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        session = entityManager.unwrap(Session.class);
    }

    public Session getSession() {
        return session;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda zapisuje obiekt w bazie danych
     *
     * @param entityObject: dowolny obiekt, który chcemy zapisać
     */
    public void saveObject(Object entityObject) {
        session.save(entityObject);
    }

    /**
     * Metoda usuwa obiekt z bazy danych
     *
     * @param entityObject: dowolny obiekt entity, który chcemy usunąć
     */
    public void deleteObject(Object entityObject) {
        Transaction transaction = session.beginTransaction();
        session.remove(entityObject);
        transaction.commit();
    }

    /**
     * Metoda zamyka sesję z bazą danych
     */
    public void closeSession() {
        session.close();
        entityManager.close();
        entityManagerFactory.close();
    }
}