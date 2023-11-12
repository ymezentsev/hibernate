package client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import storage.DatabaseInitService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class ClientCrudServiceTest {
    private static ClientCrudService clientCrudService;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void initDb() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .buildSessionFactory();

        new DatabaseInitService().initDb();

        clientCrudService = new ClientCrudService(sessionFactory);
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @BeforeEach
    void cleanClientTable(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createMutationQuery("delete from Client").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void testGetClientById() {
        String expectedName = "getClientByIdTest";
        Client newClient = new Client();
        newClient.setName(expectedName);

        clientCrudService.addClient(newClient);
        Assertions.assertEquals(expectedName, clientCrudService.getClientByID(newClient.getId()).getName());
    }

    @Test
    void testGetAllClients() {
        List<Client> expectedClients = new ArrayList<>();

        Client newClient1 = new Client();
        newClient1.setName("newClient1");
        clientCrudService.addClient(newClient1);

        Client newClient2 = new Client();
        newClient2.setName("newClient2");
        clientCrudService.addClient(newClient2);

        expectedClients.add(newClient1);
        expectedClients.add(newClient2);

        List<Client> actualClients = clientCrudService.getAllClients();
        Assertions.assertEquals(expectedClients, actualClients);
    }

    @Test
    void testAddClient() {
        Client newClient = new Client();
        newClient.setName("addClientTest");

        Assertions.assertEquals(0, newClient.getId());
        clientCrudService.addClient(newClient);
        Assertions.assertNotEquals(0, newClient.getId());
    }

    @Test
    void testThatAddClientHandleTooShotNames() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Client newClient = new Client();
        newClient.setName("12");
        clientCrudService.addClient(newClient);

        Assertions.assertEquals("You entered an incorrect client name", output.toString().trim());
        System.setOut(standardOut);
    }

    @Test
    void testUpdateClient() {
        Client newClient = new Client();
        newClient.setName("updateClientTest");

        clientCrudService.addClient(newClient);

        String newName = "newUpdateName";
        clientCrudService.updateClient(newClient.getId(), newName);
        Assertions.assertEquals(newName, clientCrudService.getClientByID(newClient.getId()).getName());
    }

    @Test
    void testThatUpdateClientHandleTooShotNames() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        clientCrudService.updateClient(1, "12");

        Assertions.assertEquals("You entered an incorrect client name", output.toString().trim());
        System.setOut(standardOut);
    }

    @Test
    void testDeleteClientById() {
        Client newClient = new Client();
        newClient.setName("deleteClientTest");

        clientCrudService.addClient(newClient);

        clientCrudService.deleteClientById(newClient.getId());
        Assertions.assertNull(clientCrudService.getClientByID(newClient.getId()));
    }
}