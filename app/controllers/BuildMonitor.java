package controllers;

import BuildJobUtils.BuildMonitorJob;
import BuildJobUtils.BuildMonitorManager;
import models.Job;
import models.MainUrl;
import play.api.templates.Html;
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

      String backgroundColour = BuildMonitorManager.getBackgroundColour();

      String message;



      if(alljobs == null || alljobs.isEmpty()){
          message = "NO PROJECTS FOUND FOR: " + getLinkText(url.url);
      } else {
          if(alljobs.size()==1){
             message = "1 Project found for " + getLinkText(url.url);
          }
          else {
              message = alljobs.size() +" Projects found for " + getLinkText(url.url);
          }
      }


      return ok(views.html.index.render(backgroundColour, url.url,alljobs, new Html(message)));
  }

  private static String getLinkText(String url){
      String text =  (url.length() > 50) ? url.substring(0,24) + "..." : url;
      return "<a href=\"" + url +"\">" + text + "</a>";
  }
  
  public static Result buildmonitor() {
        List<BuildMonitorJob> alljobs = getAllJobs();
      String message;
      if(alljobs == null || alljobs.isEmpty()){
          message = "NO PROJECTS FOUND";
      } else {
          if(alljobs.size()==1){
             message = "1 Project found";
          }
          else {
              message = alljobs.size() +" Projects found";
          }
      }
      String backgroundColour = BuildMonitorManager.getBackgroundColour();
      return ok(views.html.buildmonitor.render(backgroundColour, alljobs));
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