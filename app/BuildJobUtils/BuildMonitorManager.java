package BuildJobUtils;

import models.MainUrl;

import java.util.ArrayList;
import java.util.List;

public class BuildMonitorManager {

    private String url;
    private Integer refreshRateInSeconds = 10;

    public String getUrl() {
        return url;
    }


    private static List<BuildMonitorJob> existingJobs = new ArrayList<BuildMonitorJob>();

    public static BuildMonitorManager getInstance(String url){
          if(INSTANCE==null){
              INSTANCE = new BuildMonitorManager(url);
          }
          return INSTANCE;
    }

    private static BuildMonitorManager INSTANCE;

    protected BuildMonitorManager(String url){
        MainUrl mainUrl = new MainUrl();
        mainUrl.url = url;
        mainUrl.save();
        mainUrl.refresh();
        this.url = url;
        refreshAllJobs(mainUrl);
    }


      public static void refreshAllJobs(MainUrl url){
         try {
             System.out.println("Refreshing for: " + url.url);
            url.refresh();
            List<BuildJob> buildJobs = JsonResolver.getAvailableBuildJobs(url.url);
            existingJobs.clear();
            if(buildJobs != null && buildJobs.size() != 0){
                for(BuildJob buildJob : buildJobs){
                    existingJobs.add(new BuildMonitorJob(buildJob));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      }


    public List<BuildMonitorJob> getMonitorJobs(MainUrl url){
        refreshAllJobs(url);
        return existingJobs;
    }


}
