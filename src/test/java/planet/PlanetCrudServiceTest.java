package planet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import storage.DatabaseInitService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class PlanetCrudServiceTest {

    private static PlanetCrudService planetCrudService;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void initDb() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Planet.class)
                .buildSessionFactory();

        new DatabaseInitService().initDb();

        planetCrudService = new PlanetCrudService(sessionFactory);
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @BeforeEach
    void cleanPlanetTable(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createMutationQuery("delete from Planet").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void testGetPlanetById() {
        String expectedName = "getPlanetByIdTest";
        Planet newPlanet = new Planet();
        newPlanet.setId("GET");
        newPlanet.setName(expectedName);

        planetCrudService.addPlanet(newPlanet);
        Assertions.assertEquals(expectedName, planetCrudService.getPlanetById("GET").getName());
    }

    @Test
    void testGetAllPlanets() {
        List<Planet> expectedPlanets = new ArrayList<>();

        Planet newPlanet1 = new Planet();
        newPlanet1.setId("PL1");
        newPlanet1.setName("newPlanet1");
        planetCrudService.addPlanet(newPlanet1);

        Planet newPlanet2 = new Planet();
        newPlanet2.setId("PL2");
        newPlanet2.setName("newPlanet2");
        planetCrudService.addPlanet(newPlanet2);

        expectedPlanets.add(newPlanet1);
        expectedPlanets.add(newPlanet2);

        List<Planet> actualPlanets = planetCrudService.getAllPlanets();
        Assertions.assertEquals(expectedPlanets, actualPlanets);
    }

    @Test
    void testAddPlanet() {
        String expectedName = "addPlanet";
        Planet newPlanet = new Planet();
        newPlanet.setId("NEW");
        newPlanet.setName(expectedName);
        planetCrudService.addPlanet(newPlanet);

        Assertions.assertEquals(expectedName, planetCrudService.getPlanetById("NEW").getName());
    }

    @Test
    void testThatAddPlanetHandleWrongId() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Planet newPlanet = new Planet();
        newPlanet.setId("wrong");
        newPlanet.setName("planet");
        planetCrudService.addPlanet(newPlanet);

        Assertions.assertEquals("You entered an incorrect planet id", output.toString().trim());
        System.setOut(standardOut);
    }

    @Test
    void testUpdatePlanet() {
        Planet newPlanet = new Planet();
        newPlanet.setId("NEW");
        newPlanet.setName("newPlanet");
        planetCrudService.addPlanet(newPlanet);

        String newName = "updatePlanet";
        planetCrudService.updatePlanet("NEW", newName);
        Assertions.assertEquals(newName, planetCrudService.getPlanetById("NEW").getName());
    }

    @Test
    void testDeletePlanetById() {
        Planet newPlanet = new Planet();
        newPlanet.setId("DEL");
        newPlanet.setName("deletedPlanet");
        planetCrudService.addPlanet(newPlanet);

        planetCrudService.deletePlanetById("DEL");
        Assertions.assertNull(planetCrudService.getPlanetById("DEL"));
    }
}