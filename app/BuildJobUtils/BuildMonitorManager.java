package BuildJobUtils;

import models.MainUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


      public void refreshAllJobs(MainUrl url){
         try {
             System.out.println("Refreshing for: " + url.url);
            url.refresh();
            List<BuildJob> buildJobs = JsonResolver.getAvailableBuildJobs(url.url);
            existingJobs.clear();
            colourStatistics.clear();
            int total = buildJobs.size();
            int red = 0;
            int blue = 0;
            int green = 0;
            if(buildJobs != null && buildJobs.size() != 0){
                for(BuildJob buildJob : buildJobs){
                    existingJobs.add(new BuildMonitorJob(buildJob));
                    if(buildJob.color.equals("blue")) blue++;
                    if(buildJob.color.equals("red")) red++;
                    if(buildJob.color.equals("yellow")) green++;
                }
                System.out.println("Blue builds: " + blue);
                System.out.println("Red builds: " + red);
                System.out.println("Yellow builds: " + green);
                colourStatistics.put("red", Math.round(red*255/total));
                colourStatistics.put("blue", Math.round(blue*255/total));
                colourStatistics.put("green", Math.round(green*255/total));
            }
            else  {
                colourStatistics.put("red", 0);
                colourStatistics.put("blue", 0);
                colourStatistics.put("green", 0);
            }
            System.out.println(getBackgroundColour());
        } catch (Exception e) {
            e.printStackTrace();
        }
      }

    public static Map<String,Integer> colourStatistics = new HashMap<String,Integer>();

    public static String getBackgroundColour(){
        return "rgb(" + colourStatistics.get("red") + "," + colourStatistics.get("green") + "," + colourStatistics.get("blue")+")";
    }

    public List<BuildMonitorJob> getMonitorJobs(MainUrl url){
        refreshAllJobs(url);
        return existingJobs;
    }


}
