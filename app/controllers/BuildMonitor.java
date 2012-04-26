package controllers;

import BuildJobUtils.BuildMonitorJob;
import BuildJobUtils.BuildMonitorManager;
import models.Job;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class BuildMonitor extends Controller {

  public static Result index(){
      List<BuildMonitorJob> alljobs = getAllJobs();
      return ok(views.html.index.render(alljobs));
  }
  
  public static Result buildmonitor() {
        List<BuildMonitorJob> jobs = getAllJobs();
      return ok(views.html.buildmonitor.render(jobs));
  }

  public static List<BuildMonitorJob> getAllJobs(){
      BuildMonitorManager manager = BuildMonitorManager.getInstance("http://localhost:8080");
      return manager.getMonitorJobs();
  }


  public static Result newJob() {
     Form<Job> jobForm = form(Job.class);
     return ok(views.html.newjob.render(jobForm));
  }

  public static Result persist() {
      Form<Job> jobForm = form(Job.class);
      Job job = jobForm.bindFromRequest().get();
      job.save();
      Job result = Job.find.where().contains("name",job.name).findUnique();
      return ok("job is: " + result.name);
  }


  
}