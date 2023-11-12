import client.Client;
import client.ClientCrudService;
import org.hibernate.SessionFactory;
import planet.Planet;
import planet.PlanetCrudService;
import storage.DatabaseInitService;
import storage.HibernateUtil;

public class App {
    public static void main(String[] args) {
        new DatabaseInitService().initDb();
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        ClientCrudService clientCrudService = new ClientCrudService(sessionFactory);
        Client wrongClient = new Client();
        wrongClient.setName("Ig");
        clientCrudService.addClient(wrongClient);

        Client newClient = new Client();
        newClient.setName("Igor Sidorov");
        clientCrudService.addClient(newClient);

        clientCrudService.updateClient(2L, "Fedor");
        clientCrudService.deleteClientById(6);
        System.out.println(clientCrudService.getClientByID(7L));
        System.out.println(clientCrudService.getAllClients());


        PlanetCrudService planetCrudService = new PlanetCrudService(sessionFactory);
        Planet wrongPlanet = new Planet();
        wrongPlanet.setId("mar1");
        wrongPlanet.setName("Mars 1");
        planetCrudService.addPlanet(wrongPlanet);

        Planet newPlanet = new Planet();
        newPlanet.setId("MAR1");
        newPlanet.setName("Mars 1");
        planetCrudService.addPlanet(newPlanet);

        planetCrudService.updatePlanet("Mre", "Update Mars");
        planetCrudService.deletePlanetById("VEN");
        System.out.println(planetCrudService.getPlanetById("EAR"));
        System.out.println(planetCrudService.getAllPlanets());

        sessionFactory.close();
    }
}


