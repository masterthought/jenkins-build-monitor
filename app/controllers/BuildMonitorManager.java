package controllers;

import java.util.*;

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
        this.url = url;
        refreshAllJobs();
    }


      public static void refreshAllJobs(){
         try {
            List<BuildJob> buildJobs = JsonResolver.getAvailableBuildJobs(INSTANCE.getUrl());
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


    public List<BuildMonitorJob> getMonitorJobs(){
        refreshAllJobs();
        return existingJobs;
    }


}
