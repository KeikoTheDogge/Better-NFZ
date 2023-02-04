package Login;

import DatabaseClass.DatabaseSession;
import entity.UsersEntity;
import jakarta.persistence.*;

/**
 * Klasa Authentication zajmuje się autoryzacją użytkownika. Sprawdza, czy podany przez niego login i hasło znajdują
 * się w bazie danych aplikacji.
 */
public class Authentication {
    private final DatabaseSession databaseSession;

    public Authentication(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    public UsersEntity authenticate(String login, String password) throws NoResultException {
        Query query = databaseSession.getSession().createNamedQuery("loginUser", UsersEntity.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (UsersEntity) query.getSingleResult();
    }

}
