package controllers;

import BuildJobUtils.BuildMonitorJob;
import BuildJobUtils.BuildMonitorManager;
import models.Job;
import models.MainUrl;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class BuildMonitor extends Controller {

  public static Result index(){
      List<BuildMonitorJob> alljobs = getAllJobs();
      List<MainUrl> mainUrls = MainUrl.find.all();
      MainUrl url = new MainUrl();
      if(mainUrls.size() != 0 ){

          for(MainUrl mainUrl : mainUrls){
              System.out.println("MainURL("+mainUrls.indexOf(mainUrl) + "): " + mainUrl.getUrl());
              url = mainUrl;
          }

      }  else {
          url.url = "http://localhost:8080";
          url.save();
          url.refresh();
      }

      return ok(views.html.index.render(url.url,alljobs));
  }
  
  public static Result buildmonitor() {
        List<BuildMonitorJob> jobs = getAllJobs();
      return ok(views.html.buildmonitor.render(jobs));
  }

  public static List<BuildMonitorJob> getAllJobs(){
      List<MainUrl> mainUrls = MainUrl.find.all();
      MainUrl url= new MainUrl();
      if(mainUrls.size() != 0 ){

          for(MainUrl mainUrl : mainUrls){
              System.out.println("MainURL("+mainUrls.indexOf(mainUrl) + "): " + mainUrl.getUrl());
              url = mainUrl;
          }

      } else {
          url.url = "http://localhost:8080";
          url.save();
          url.refresh();
      }

      BuildMonitorManager manager = BuildMonitorManager.getInstance(url.url);
      return manager.getMonitorJobs(url);
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