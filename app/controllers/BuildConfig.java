package controllers;

import models.MainUrl;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

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
      return redirect("/");
  }


  
}