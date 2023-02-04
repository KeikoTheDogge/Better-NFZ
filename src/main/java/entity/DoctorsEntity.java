package entity;

import jakarta.persistence.*;

@NamedQueries(
        {
                @NamedQuery(
                        name = "docSpec",
                        query = "from DoctorsEntity e where e.specialization = :specialization"
                )
        }
)

@Entity
@PrimaryKeyJoinColumn(name = "userID")
@Table(name = "doctors", schema = "nfz")
public class DoctorsEntity extends UsersEntity {
    @Id
    @Basic
    @Column(name = "userID")
    private Integer userId;
    @Basic
    @Column(name = "specialization")
    private String specialization;

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorsEntity that = (DoctorsEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (specialization != null ? !specialization.equals(that.specialization) : that.specialization != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (specialization != null ? specialization.hashCode() : 0);
        return result;
    }
}
