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

    /**
     * Metoda authenticate sprawdza, czy wprowadzone przez użytkownika login i hasło znajdują się w bazie danych.
     * Jeśli się nie znajdują, to zwraca wyjątek NoResultException.
     *
     * @param login:    podany przez użytkownika login
     * @param password: podane przez użytkownika hasło
     * @throws NoResultException: rzuca wyjątek, jeśli nie znajdzie matcha
     * @return: zwraca cały obiekt (wszystkie informacje o użytkowniku z bazy) jeśli znajdzie match
     */
    public UsersEntity authenticate(String login, String password) throws NoResultException {
        Query query = databaseSession.getSession().createNamedQuery("loginUser", UsersEntity.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        return (UsersEntity) query.getSingleResult();
    }

}
