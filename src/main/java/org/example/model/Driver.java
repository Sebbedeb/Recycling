package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Driver
{
    @Id
    String id;
    @Temporal(TemporalType.DATE)
    Date employmentDate;
    String name;
    BigDecimal salary;
    String surname;
    Integer truckId;

    public Driver(String name, String surname, BigDecimal salary)
    {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    @ManyToOne
    private WasteTruck wasteTruck;

    /*The id for the driver should be a string with the format ddMMyy-XX-XXXL.
    ddMMyy is the date of employment, (Date: 2023-08-26 -> String: 230826)
    XX is the first letters of the name and of the surname (Name: John Doe -> String: JD)
    XXX is a random number between 100 and 999
    L is the last letter of the surname (Surname: Doe -> String: E)
     */

    public String idGenerator()
    {
        employmentDate = new Date(23, Calendar.SEPTEMBER,01);

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");

        String ddMMyy = dateFormat.format(employmentDate);

        char firstLetterOfFirstName = name.charAt(0);
        char firstLetterOfLastName = surname.charAt(0);
        String xx = String.valueOf(firstLetterOfFirstName) + firstLetterOfLastName;
        Random random = new Random();
        String xxx = String.valueOf(random.nextInt(900)+100);
        char lastLetterOfLastName = surname.charAt(surname.length()-1);
        lastLetterOfLastName = Character.toUpperCase(lastLetterOfLastName);

        String ddMMyyxxXXXL = ddMMyy+"-"+xx+"-"+xxx+lastLetterOfLastName;
        return ddMMyyxxXXXL;
    }

    public Boolean validateDriverId(String driverId) {
        return driverId.matches("[0-9][0-9][0-9][0-9][0-9][0-9]-[A-Z][A-Z]-[0-9][0-9][0-9][A-Z]");
    }

    @PrePersist
    public void generateID()
    {
        String id = idGenerator();
        if(validateDriverId(id))
        {
            setId(id);
        }
    }
}
