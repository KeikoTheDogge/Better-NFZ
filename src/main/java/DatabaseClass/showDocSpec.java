package DatabaseClass;

import entity.DoctorsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.hibernate.Session;
import java.util.List;

public class showDocSpec{

   public List<DoctorsEntity> specDoctor(String specialization) {
      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      Session session = entityManager.unwrap(Session.class);

      Query query = session.createNamedQuery("docSpec",DoctorsEntity.class);
      query.setParameter("specialization", specialization);
      return (List<DoctorsEntity>) query.getResultList();
   }

}

