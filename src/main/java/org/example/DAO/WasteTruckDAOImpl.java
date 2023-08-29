package org.example.DAO;

import jakarta.persistence.*;
import org.example.config.HibernateConfig;
import org.example.model.Driver;
import org.example.model.WasteTruck;

import java.util.*;

public class WasteTruckDAOImpl implements IWasteTruckDAO
{
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("recycling");

    @Override
    public int saveWasteTruck(String brand, String registrationNumber, int capacity)
    {
        WasteTruck wasteTruck = new WasteTruck(brand, capacity, registrationNumber);
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(wasteTruck);
            em.getTransaction().commit();
        }
        if (wasteTruck.getId() != null)
        {
            return 1;
        } else
        {
            return 0;
        }
    }

    @Override
    public WasteTruck getWasteTruckById(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(WasteTruck.class, id);
        }
    }

    @Override
    public void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available)
    {
        wasteTruck.setAvailable(available);
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            wasteTruck = em.merge(wasteTruck);
            em.getTransaction().commit();
        }

    }

    @Override
    public void deleteWasteTruck(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(getWasteTruckById(id));
            em.getTransaction().commit();
        }
    }

    @Override
    public void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver)
    {
        Set<Driver> driverSet = wasteTruck.getDrivers();
        driverSet.add(driver);
        wasteTruck.setDrivers(driverSet);
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            wasteTruck = em.merge(wasteTruck);
            em.getTransaction().commit();
        }
    }

    @Override
    public void removeDriverFromWasteTruck(WasteTruck wasteTruck, String id)
    {
        Set<Driver> driverSet = wasteTruck.getDrivers();

        Iterator<Driver> iterator = driverSet.iterator();
        while (iterator.hasNext())
        {
            Driver driver = iterator.next();
            if (driver.getId() == id)
            {
                iterator.remove();
                break;
            }
            try (EntityManager em = emf.createEntityManager())
            {
                em.getTransaction().begin();
                wasteTruck = em.merge(wasteTruck);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public List<WasteTruck> getAllAvailableTrucks()
    {
        {
            EntityManager em = emf.createEntityManager();
            TypedQuery<WasteTruck> q = (TypedQuery<WasteTruck>) em.createQuery("SELECT w FROM WasteTruck w WHERE isAvailable=true");
            return q.getResultList();
        }
    }
}
