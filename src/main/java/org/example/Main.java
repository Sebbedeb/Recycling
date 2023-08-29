package org.example;

import org.example.DAO.DriverDAOImpl;
import org.example.DAO.WasteTruckDAOImpl;
import org.example.model.Driver;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Main
{
    public static void main(String[] args)
    {
        DriverDAOImpl driverDAOImpl = new DriverDAOImpl();
        WasteTruckDAOImpl wasteTruckDAO = new WasteTruckDAOImpl();

        Driver richDriver = driverDAOImpl.fetchDriverWithHighestSalary();
        driverDAOImpl.deleteDriver(richDriver.getId());

    }
}