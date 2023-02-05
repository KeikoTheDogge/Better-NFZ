package entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
@org.hibernate.annotations.NamedQueries(
        {
                @org.hibernate.annotations.NamedQuery(name="getVisitBySpecialisation",
                        query="select v, u from VisitsEntity v inner join UsersEntity u on v.doctorId = u.id " +
                                "inner join DoctorsEntity d on u.id = d.userId where d.specialization = :spec " +
                                "and v.patientId = null"),
                @org.hibernate.annotations.NamedQuery(name="getVisitByDoctorId",
                        query="select v from VisitsEntity v inner join UsersEntity u where u.id = :doctorId"),
                @org.hibernate.annotations.NamedQuery(name="getVisitByPatientId",
                        query="select v from VisitsEntity v where v.patientId = :patientId"),
                @org.hibernate.annotations.NamedQuery(name="getCollideVisits",
                        query="from VisitsEntity where doctorId = :doctorId and date = :visitDate " +
                                "and ((:timeStart >= timeFrom AND :timeStart < timeTo) " +
                                "OR (:timeEnd > timeFrom AND :timeEnd <= timeTo))"),
                //@org.hibernate.annotations.NamedQuery(name="getShowVisit",
                        //query = "from VisitsEntity where doctorId = :doctorId and date = :date"),
                @org.hibernate.annotations.NamedQuery(name="getDoctorSchuedule",
                        query = "from VisitsEntity where doctorId = :doctorId"),
                @org.hibernate.annotations.NamedQuery(name="getPatientSchuedule",
                        query = "select v, d from VisitsEntity v inner join DoctorsEntity d " +
                                "where v.patientId = :patientId and d.userId = v.doctorId")
        }
)
@Entity
@Table(name = "visits", schema = "nfz")
public class VisitsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "visitId")
    private int visitId;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "time_from")
    private Time timeFrom;
    @Basic
    @Column(name = "time_to")
    private Time timeTo;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "doctorId")
    private int doctorId;
    @Basic
    @Column(name = "patientId")
    private Integer patientId;

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Time getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Time timeTo) {
        this.timeTo = timeTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitsEntity that = (VisitsEntity) o;

        if (visitId != that.visitId) return false;
        if (doctorId != that.doctorId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (timeFrom != null ? !timeFrom.equals(that.timeFrom) : that.timeFrom != null) return false;
        if (timeTo != null ? !timeTo.equals(that.timeTo) : that.timeTo != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (patientId != null ? !patientId.equals(that.patientId) : that.patientId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = visitId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (timeFrom != null ? timeFrom.hashCode() : 0);
        result = 31 * result + (timeTo != null ? timeTo.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + doctorId;
        result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
        return result;
    }
}
