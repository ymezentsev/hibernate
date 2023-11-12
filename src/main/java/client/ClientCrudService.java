package client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ClientCrudService {
    private final SessionFactory sessionFactory;

    public ClientCrudService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Client getClientByID(long id) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    public List<Client> getAllClients() {
        Session session = sessionFactory.openSession();
        List<Client> clients = session.createQuery("from Client", Client.class).list();
        session.close();
        return clients;
    }

    public void addClient(Client client) {
        try {
            validateClientName(client.getName());
        } catch (ClientNameException e) {
            System.out.println(e.getMessage());
            return;
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(client);
        transaction.commit();
        session.close();
    }

    public void updateClient(long id, String newName) {
        try {
            validateClientName(newName);
        } catch (ClientNameException e) {
            System.out.println(e.getMessage());
            return;
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Client clientByID = session.get(Client.class, id);
        if (clientByID != null) {
            clientByID.setName(newName);
            session.persist(clientByID);
        }

        transaction.commit();
        session.close();
    }

    public void deleteClientById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Client clientByID = session.get(Client.class, id);
        if (clientByID != null) {
            session.remove(clientByID);
        }

        transaction.commit();
        session.close();
    }

    private void validateClientName(String name) throws ClientNameException {
        if (name.length() > 200 || name.length() < 3) {
            throw new ClientNameException();
        }
    }
}
