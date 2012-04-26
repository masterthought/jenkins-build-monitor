package controllers;

import common.BuildMonitorJob;
import common.BuildMonitorManager;
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

  
}