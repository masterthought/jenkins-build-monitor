package helpers;

import models.Job;

/**
 * Created by IntelliJ IDEA.
 * User: kostasmamalis
 * Date: 24/04/2012
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args){
        Job job = new Job();
        job.name = "kostas";
        job.active = true;
        job.colour = "blue";
        job.url = "http://localhost:8080";
        job.save();
        Job anotherJob = job.find.byId("kostas");
        System.out.println(anotherJob.toString());
    }
}
