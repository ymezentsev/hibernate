package planet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlanetCrudService {
    private final SessionFactory sessionFactory;

    public PlanetCrudService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Planet getPlanetById(String id) {
        Session session = sessionFactory.openSession();
        Planet planet = session.get(Planet.class, id);
        session.close();
        return planet;
    }

    public List<Planet> getAllPlanets() {
        Session session = sessionFactory.openSession();
        List<Planet> planets = session.createQuery("from Planet", Planet.class).list();
        session.close();
        return planets;
    }

    public void addPlanet(Planet planet) {
        try {
            validatePlanetId(planet.getId());
        } catch (PlanetIdException e) {
            System.out.println(e.getMessage());
            return;
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(planet);
        transaction.commit();
        session.close();
    }

    public void updatePlanet(String id, String newName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Planet planetByID = session.get(Planet.class, id);
        if (planetByID != null) {
            planetByID.setName(newName);
            session.persist(planetByID);
        }

        transaction.commit();
        session.close();
    }

    public void deletePlanetById(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Planet planetByID = session.get(Planet.class, id);
        if (planetByID != null) {
            session.remove(planetByID);
        }

        transaction.commit();
        session.close();
    }

    private void validatePlanetId(String id) throws PlanetIdException {
        if (!id.matches("[A-Z,0-9]+")) {
            throw new PlanetIdException();
        }
    }
}
