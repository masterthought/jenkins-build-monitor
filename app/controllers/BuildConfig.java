package controllers;

import BuildJobUtils.BuildMonitorJob;
import models.HiddenJob;
import models.HighlightedJob;
import models.MainUrl;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

public class BuildConfig extends Controller {

  public static Result config() {
     Form<MainUrl> mainUrlForm = form(MainUrl.class);
     return ok(views.html.config.render(mainUrlForm));
  }

  public static Result persist() {
      Form<MainUrl> urlForm = form(MainUrl.class);
      MainUrl url = urlForm.bindFromRequest().get();
      List<MainUrl> existingUrls = MainUrl.find.all();
      if(existingUrls != null && existingUrls.size() != 0){
          for(MainUrl existingUrl : existingUrls){
              existingUrl.delete();
          }
      }

      url.save();
      clearAllFiltering();
      return redirect("/");
  }

  public static Result filter() {
      List<MainUrl> urls = MainUrl.find.all();
      if(urls == null || urls.size() == 0) return ok("Nothing to configure");
      MainUrl url = urls.get(urls.size()-1);
      List<BuildMonitorJob> allJobs = BuildMonitor.getAllJobs();
      Form<HiddenJob> hiddenJobForm = form(HiddenJob.class);
      Form<HighlightedJob> highlightedJobForm = form(HighlightedJob.class);
      List<String> names = new ArrayList<String>();
      for(BuildMonitorJob job: allJobs){
         if(!isItHighlighted(job.getName()) && !isItHidden(job.getName())){
             names.add(job.getName());
         }
      }

      return ok(views.html.filtering.render(url.url,names,allJobs,hiddenJobForm,highlightedJobForm,HiddenJob.find.all(),HighlightedJob.find.all()));
  }

  public static Result hidden() {
      Form<HiddenJob> hiddenJobForm = form(HiddenJob.class);
      HiddenJob hiddenJob = hiddenJobForm.bindFromRequest().get();

      List<HiddenJob> existingHiddenJobs = HiddenJob.find.where().eq("name",hiddenJob.name).findList();
      List<HighlightedJob> existingHighlightedJobs = HighlightedJob.find.where().eq("name",hiddenJob.name).findList();
      if(
              (existingHiddenJobs == null      || existingHiddenJobs.size() == 0)
                       &&
              (existingHighlightedJobs == null || existingHighlightedJobs.size() == 0)
        ){

           hiddenJob.save();
           hiddenJob.refresh();
      }


      return redirect("/filtering");
  }

  public static Result highlight() {
      Form<HighlightedJob> highlightedJobForm = form(HighlightedJob.class);
      HighlightedJob highlightedJob = highlightedJobForm.bindFromRequest().get();
      List<HiddenJob> existingHiddenJobs = HiddenJob.find.where().eq("name",highlightedJob.name).findList();
      List<HighlightedJob> existingHighlightedJobs = HighlightedJob.find.where().eq("name",highlightedJob.name).findList();
      if(
              (existingHiddenJobs == null      || existingHiddenJobs.size() == 0)
                       &&
              (existingHighlightedJobs == null || existingHighlightedJobs.size() == 0)
        ){
          highlightedJob.save();
          highlightedJob.refresh();
      }
      return redirect("/filtering");
  }

  public static Result clear() {
      clearAllFiltering();
      return redirect("/filtering");
  }

  public static void clearAllFiltering(){
      clearHighlightedJobs();
      clearHiddenJobs();
  }

  public static void clearHighlightedJobs(){
       List<HighlightedJob> highLightedJobs = HighlightedJob.find.all();
      if(highLightedJobs != null && highLightedJobs.size() != 0){
          for(HighlightedJob h : highLightedJobs){
              h.delete();
          }
      }
  }

  public static void clearHiddenJobs(){
     List<HiddenJob> hiddenJobs = HiddenJob.find.all();
      if(hiddenJobs != null && hiddenJobs.size() != 0){
          for(HiddenJob h : hiddenJobs){
              h.delete();
          }
      }
  }

    public static boolean isItHighlighted(String name){
      List<HighlightedJob> existingHighlightedJobs = HighlightedJob.find.where().eq("name",name).findList();
      return existingHighlightedJobs != null && existingHighlightedJobs.size() != 0;
    }

    public static boolean isItHidden(String name){
       List<HiddenJob> existingHiddenJobs = HiddenJob.find.where().eq("name",name).findList();
       return existingHiddenJobs != null  && existingHiddenJobs.size() != 0;
    }

    public static Result removeHidden(String name){
        List<HiddenJob> existingHiddenJobs = HiddenJob.find.where().eq("name",name).findList();
        if(isItHidden(name)){
            for(HiddenJob existingHiddenJob : existingHiddenJobs){
                existingHiddenJob.delete();
            }
        }
        return redirect("/filtering");
    }

    public static Result removeHighlighted(String name){
        List<HighlightedJob> existingHighlightedJobs = HighlightedJob.find.where().eq("name",name).findList();
        if(isItHidden(name)){
            for(HighlightedJob existingHighlightedJob : existingHighlightedJobs){
                existingHighlightedJob.delete();
            }
        }
        return redirect("/filtering");
    }
  
}