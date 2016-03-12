package com.example.erin.calendarapp;

/**
 * Created by njaunich on 2/18/16.
 */
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;


public class EventDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.example.erin.calendarapp"); //Scheme for GreenDAO ORM
        createDB(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java/");
    }

    private static void createDB(Schema schema) {

        //TODO: Add the following string properties to the Guest entity
        //TODO: (cont.) firstName, lastName, email, phone
        //TODO: Add a Boolean property named "display" (this will always be set to true)
        //Add Guest
        Entity event = schema.addEntity("Event");
        event.addIdProperty();
        event.addIntProperty("year");
        event.addIntProperty("month");
        event.addIntProperty("day");
        event.addStringProperty("name");
        event.addStringProperty("startTime");
        event.addStringProperty("endTime");

    }

}