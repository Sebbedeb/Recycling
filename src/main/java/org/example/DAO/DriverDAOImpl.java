package org.example.DAO;

import jakarta.persistence.*;
import org.example.config.HibernateConfig;
import org.example.model.Driver;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Date;
import java.util.List;

public class DriverDAOImpl implements IDriverDAO
{
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("recycling");
    @Override
    public String saveDriver(String name, String surname, BigDecimal salary)
    {
        Driver driver = new Driver(name, surname, salary);
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(driver);
            em.getTransaction().commit();
        }
        if(driver.getId() != null)
        {
            return "Success";
        }
        else
        {
            return "Failure";
        }
    }

    @Override
    public Driver getDriverById(String id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.find(Driver.class, id);
        }
    }

    @Override
    public Driver updateDriver(Driver driver)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            driver = em.merge(driver);
            em.getTransaction().commit();
        }
        return driver;
    }

    @Override
    public void deleteDriver(String id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(getDriverById(id));
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Driver> findAllDriversEmployedAtTheSameYear(String year)
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Driver> q = (TypedQuery<Driver>) em.createQuery("SELECT d FROM Driver d");
        int yearAsInt = Integer.parseInt(year);
        Date earliest = new Date(yearAsInt, 1, 1);
        List<Driver> driverList = q.getResultList();
        for(Driver d : driverList)
        {
            if(!d.getEmploymentDate().before(earliest))
            {
                driverList.remove(d);
            }
        }
        return driverList;
    }

    @Override
    public List<BigDecimal> fetchAllDriversWithSalaryGreaterThan10000()
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Driver> q = (TypedQuery<Driver>) em.createQuery("SELECT d FROM Driver d WHERE salary>10000");
        List<Driver> driverList = q.getResultList();
        List<BigDecimal> fsrWereReturningDecimals = null;
        for(Driver d : driverList)
        {
            fsrWereReturningDecimals.add(d.getSalary());
        }
        return fsrWereReturningDecimals;
    }

    @Override
    public double fetchHighestSalary()
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Double> q = (TypedQuery<Double>) em.createQuery("SELECT MAX(d.salary) FROM Driver d");
        return q.getSingleResult();
    }

    @Override
    public List<String> fetchFirstNameOfAllDrivers()
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<String> q = (TypedQuery<String>) em.createQuery("SELECT d.name FROM Driver d");
        return q.getResultList();
    }

    @Override
    public long calculateNumberOfDrivers()
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery("SELECT count(d) FROM Driver d");
        return q.getSingleResult();
    }

    @Override
    public Driver fetchDriverWithHighestSalary()
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Driver> q = (TypedQuery<Driver>) em.createQuery("SELECT MAX(d.salary) FROM Driver d");
        return q.getSingleResult();
    }


}
