package entity;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "userID")
@Table(name = "patients", schema = "nfz")
public class PatientsEntity extends UsersEntity {
    @Id
    @Basic
    @Column(name = "userID")
    private Integer userId;
    @Basic
    @Column(name = "adres")
    private String adres;

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientsEntity that = (PatientsEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (adres != null ? !adres.equals(that.adres) : that.adres != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (adres != null ? adres.hashCode() : 0);
        return result;
    }
}
