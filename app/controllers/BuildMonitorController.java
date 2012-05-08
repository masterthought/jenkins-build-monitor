package controllers;

import BuildJobUtils.*;
import BuildJobUtils.BuildJob;
import com.sun.xml.internal.bind.v2.runtime.ElementBeanInfoImpl;
import models.*;
import play.*;
import play.data.Form;
import play.mvc.*;
import com.avaje.ebean.Ebean;
import play.db.ebean.Transactional;


import java.util.ArrayList;
import java.util.List;

public class BuildMonitorController extends Controller {

    public static Result index() {
        List<BuildMonitorConfig> configs = new ArrayList<BuildMonitorConfig>();
        try {
            configs = BuildMonitorConfig.find.all();

        } catch (RuntimeException re) {

        } finally {
            return ok(views.html.index.render(configs));
        }
    }

    public static Result newmonitor() {
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        return ok(views.html.newmonitor.render(configForm));

    }

    public static Result create() {
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        BuildMonitorConfig config = configForm.bindFromRequest().get();
        config.save();
        return redirect("/");
    }

    public static Result display(Long id) {
        return jobDisplay(id);

    }

    public static Result delete(Long id){
        BuildMonitorConfig config = BuildMonitorConfig.find.byId(id);
        config.delete();
        return redirect("/");
    }

    public static Result edit(Long id) {
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        BuildMonitorConfig buildMonitorConfig = configForm.bindFromRequest().get();
        BuildMonitorConfig config = BuildMonitorConfig.find.byId(id);
        configForm = configForm.fill(config);
        return ok(views.html.edit.render(configForm, getJobNames(id), config));
    }

    public static Result update(Long id){
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        BuildMonitorConfig buildMonitorConfig = configForm.bindFromRequest().get();
        BuildMonitorConfig objectToupdate = BuildMonitorConfig.find.byId(id);
        buildMonitorConfig.id = objectToupdate.id;
        objectToupdate.name = buildMonitorConfig.name;
        objectToupdate.buildUrl = buildMonitorConfig.buildUrl;

        return redirect("/");
    }



    public static List<String> getJobNames(Long id) {
        List<String> result = new ArrayList<String>();
        try {
            BuildMonitorConfig config = BuildMonitorConfig.find.byId(id);

        System.out.println("Config name: " + config.name);
        config.refresh();
        System.out.println(models.BuildJob.find.where().eq("buildMonitorConfig",config).findList().size() + " found for config " + config.name);

        System.out.println("\n\n\n" + config.jobs.size() + " job(s) found.");
        for(models.BuildJob job : config.jobs){
            System.out.println(job.name);
            result.add(job.name);
        }
            } catch(NullPointerException npe){
        }
        return result;
    }

    public static String truncate(String s){

        return s.length() < 35 ? s : s.substring(0,31) + "...";

    }

    @Transactional
    public static Result jobDisplay(Long id){

        List<BuildMonitorConfig> configs = new ArrayList<BuildMonitorConfig>();
        try {
            configs = BuildMonitorConfig.find.all();

        } catch (RuntimeException re) {
           re.printStackTrace();
        }
        System.out.println("My id is: "  + id);
        BuildMonitorConfig config = BuildMonitorConfig.find.ref(id);
        System.out.println("Displaying jobs for " + config.name);
        List<BuildJobUtils.BuildJob> buildJobs;
                try {
                    buildJobs = JsonResolver.getAvailableBuildJobs(config.buildUrl);
                    System.out.println(buildJobs.size() + " found!");

                        List<models.BuildJob> listOfJobs = new ArrayList<models.BuildJob>();
                        for (BuildJobUtils.BuildJob buildJob : buildJobs) {
                            BuildMonitorJob monitorJob = new BuildMonitorJob(buildJob);
                               models.BuildJob job = new models.BuildJob();

                               job.name = buildJob.name;
                               job.url = buildJob.url;
                               job.color = buildJob.color;
                               job.inProgress = monitorJob.inProgress();
                               job.displayOrder = buildJobs.indexOf(buildJob);
                               job.buildNumber = monitorJob.getLatestBuildNumber();
                               job.inProgress = monitorJob.inProgress();
                               System.out.println("Befor an update");

                               models.BuildJob existingJob = BuildMonitorConfig.containsJob(config,job.name);
                               if(existingJob != null) {
                                   System.out.println("The job is already related to the config: " + config.name);
                                   job.id = existingJob.id;
                                   existingJob = job;
                                   job.update();
                                   config.update();
                                   config.refresh();
                               } else{
                                   job.save();
                                   System.out.println("The job isn't related to the config: " + config.name);
                                   config.jobs.add(job);
                                   config.saveManyToManyAssociations("jobs");
                                   config.update();

    //job.buildMonitorConfig.add(config);
                                       //job.update();
                                       //job.saveManyToManyAssociations("buildMonitorConfig");

                               }




                            }

                         //config.jobs.addAll(listOfJobs);
                         config.update();
                         config.refresh();

                } catch (Exception e) {
                     e.printStackTrace();
                }

        BuildMonitorConfig c = BuildMonitorConfig.find.byId(id);
        return c == null ? ok("nothing found") : ok(views.html.display.render(getColour(c),c,configs));

    }

    public static String getColour(BuildMonitorConfig config) {
        String backgroundColor;
        int red = 0;
        int yellow = 0;
        int blue = 0;

        for (models.BuildJob availableJob : config.jobs) {
            if (availableJob.color.equalsIgnoreCase("red")) {
                red++;
            }
            if (availableJob.color.equalsIgnoreCase("blue")) {
                blue++;
            }
            if (availableJob.color.equalsIgnoreCase("yellow")) {
                yellow++;
            }
        }


        backgroundColor = "grey";
        if (blue > 0) {
           backgroundColor = "blue";
        }

        System.out.println(backgroundColor);

        if (yellow > 0) {
            backgroundColor = "purple";
        }
        System.out.println(backgroundColor);

        if (red > 0) {
            backgroundColor = "red";
        }
        System.out.println(backgroundColor);
        return backgroundColor;
    }

}