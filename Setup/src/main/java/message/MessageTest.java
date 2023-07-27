package message;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class MessageTest {
    private static SessionFactory factory;

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        Message message = new Message();
        message.setId(1L);
        message.setText("Hello world");

        //Save
        try(Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        }

        //Read
        try(Session session = factory.openSession()) {
            List<Message> messages = session.createQuery("from Message", Message.class).list();

            if(messages.size() != 1) {
                throw new TransactionException("Wrong transaction");
            }
            System.out.println(messages.get(0));
        }
    }
}
