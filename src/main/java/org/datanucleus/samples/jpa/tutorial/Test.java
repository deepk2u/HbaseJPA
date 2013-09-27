package org.datanucleus.samples.jpa.tutorial;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.datanucleus.util.NucleusLogger;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Tutorial");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Inventory inv = new Inventory("My Inventory");
            Product product = new Product("Sony Discman", "A standard discman from Sony", 200.00);
            inv.getProducts().add(product);
            Book book = new Book("Lord of the Rings by Tolkien", "The classic story", 49.99, "JRR Tolkien", "12345678", "MyBooks Factory");
            inv.getProducts().add(book);

            em.persist(inv);

            tx.commit();
            System.out.println("Product and Book have been persisted");
        } catch (Exception e) {
            NucleusLogger.GENERAL.error(">> Exception persisting data", e);
            System.err.println("Error persisting data : " + e.getMessage());
            return;
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
        }
        emf.getCache().evictAll();
        System.out.println("");
    }

}
