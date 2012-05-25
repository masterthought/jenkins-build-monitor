package controllers;

import BuildJobUtils.BuildMonitorJob;
import BuildJobUtils.JsonResolver;
import Utils.AudioPlayer;
import models.BuildMonitorConfig;
import play.*;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildMonitorController extends Controller {

    public static Result index() {
        return redirect("/config");
    }

    public static Result newmonitor() {
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        return ok(views.html.newmonitor.render(configForm));

    }

    public static Result create() {
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        BuildMonitorConfig config = configForm.bindFromRequest().get();
        config.save();
        return redirect("/config");
    }

    public static Result avatar() {
       return ok(views.html.avatar.render());
    }

    public static Result upload() {
          MultipartFormData body = request().body().asMultipartFormData();
          FilePart picture = body.getFile("picture");
          if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();
            return ok("File uploaded");
          } else {
            flash("error", "Missing file");
            return redirect(routes.BuildMonitorController.config());
          }
    }

    public static Result updateOrder(Long id, String order){
       BuildMonitorConfig config = BuildMonitorConfig.find.byId(id);
        System.out.println("Received a request to update order for: "  + id + " order: " + order);
        String[] jobNames = order.split(",");
        List<String> listOfNames = Arrays.asList(jobNames);
        int i = 0;
        for(String jobName : listOfNames ){
            jobName = jobName.replaceAll("%20"," ").trim();
            System.out.print("JobName: " + jobName);
            System.out.println(" with index: " + i );
            models.BuildJob existingJob = BuildMonitorConfig.containsJob(config, jobName);
            System.out.println("Existing job found with name: " + existingJob.name);
            existingJob.displayOrder = i;
            existingJob.update();
            config.update();
            config.refresh();
            i++;
        }

        return ok(id + " order " + order + " is updated");
    }

    public static Result display(Long id) {
        return jobDisplay(id);

    }

    public static Result delete(Long id){
        BuildMonitorConfig config = BuildMonitorConfig.find.byId(id);
        config.delete();
        return redirect("/config");
    }

    public static Result edit(Long id) {
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        List<Form<models.BuildJob>> jobFormList = new ArrayList<Form<models.BuildJob>>();
        BuildMonitorConfig config = BuildMonitorConfig.find.byId(id);
        configForm = configForm.fill(config);

        if(config.jobs != null && !config.jobs.isEmpty() ){
            for(models.BuildJob j : config.getJobs()){
                Form<models.BuildJob> jobForm = form(models.BuildJob.class);
                jobForm = jobForm.fill(j);
                jobFormList.add(jobForm);
            }
        }

        return ok(views.html.edit.render(configForm, jobFormList, config));
    }

    public static Result config() {
        List<BuildMonitorConfig> configs = new ArrayList<BuildMonitorConfig>();
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        try {
            configs = BuildMonitorConfig.find.all();

        } catch (RuntimeException re) {

        } finally {
            return ok(views.html.config.render(configs, configForm));
        }
    }

    public static Result update(Long id){
        Form<BuildMonitorConfig> configForm = form(BuildMonitorConfig.class);
        BuildMonitorConfig buildMonitorConfig = configForm.bindFromRequest().get();
        System.out.println("Attempt to update build monitor configuration:" + id);
        BuildMonitorConfig buildMonitorConfigToUpdate = BuildMonitorConfig.find.byId(id);
        buildMonitorConfigToUpdate.name = buildMonitorConfig.name;
        buildMonitorConfigToUpdate.buildUrl =  buildMonitorConfig.buildUrl;

        buildMonitorConfigToUpdate.update();
        return ok("Successful edit of Build Monitor Config: "+id);
    }

    public static Result updateJob(Long id){
        Form<models.BuildJob> configForm = form(models.BuildJob.class);
        models.BuildJob buildJob = configForm.bindFromRequest().get();
        System.out.println("Attempt to update job:" + id);
        models.BuildJob buildJobToUpdate = models.BuildJob.find.byId(id);
        buildJobToUpdate.hidden = buildJob.hidden;
        buildJobToUpdate.highlight = buildJob.highlight;
        buildJobToUpdate.update();
        return ok("Successful edit of Job Config: "+id);
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

    private static List<String> thingsToSay = new ArrayList<String>();

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
                         thingsToSay.clear();
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

                               models.BuildJob existingJob = BuildMonitorConfig.containsJob(config,job.name);
                               if(existingJob != null) {
                                   if(existingJob.inProgress && !monitorJob.inProgress()){
                                       if(job.color.equalsIgnoreCase("red")) {
                                           thingsToSay.add("Build " + job.buildNumber + " of " + job.name + " project just failed.");

                                       }
                                   }
                                   job.id = existingJob.id;
                                   job.hidden = existingJob.hidden;
                                   job.highlight = existingJob.highlight;
                                   job.displayOrder = existingJob.displayOrder;
                                   existingJob = job;
                                   job.update();
                                   config.update();
                                   config.refresh();
                               } else{
                                   job.save();
                                   config.jobs.add(job);
                                   config.saveManyToManyAssociations("jobs");
                                   config.update();
                               }
                        }

                         //config.jobs.addAll(listOfJobs);
                         config.update();
                         config.refresh();

                         if(!thingsToSay.isEmpty()){
                             for(String thingToSay : thingsToSay){
                                 AudioPlayer.speak(thingToSay);
                                 Thread.sleep(1000);
                             }
                         }

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