package BuildJobUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildMonitorManager {

    private String url;
    private Integer refreshRateInSeconds = 10;

    public String getUrl() {
        return url;
    }


    private static List<BuildMonitorJob> existingJobs = Collections.synchronizedList(new ArrayList());
    public static List<String> hiddenJobs = new ArrayList<String>();
    public static List<String> highlightedJobs = new ArrayList<String>();

    public static BuildMonitorManager getInstance(String url){
          if(INSTANCE==null){
              INSTANCE = new BuildMonitorManager(url);
          }
          return INSTANCE;
    }

    private static BuildMonitorManager INSTANCE;

    protected BuildMonitorManager(String url){
//        MainUrl mainUrl = new MainUrl();
//        mainUrl.url = url;
//        mainUrl.save();
//        mainUrl.refresh();
//        this.url = url;
        refreshAllJobs(url);
    }


      public void refreshAllJobs(String url){
        synchronized (existingJobs){
         try {
             System.out.println("Refreshing for: " + url);
//            url.refresh();
            List<BuildJob> buildJobs = JsonResolver.getAvailableBuildJobs(url);
            existingJobs.clear();
            int red = 0;
            int blue = 0;
            int green = 0;
            backgroundColor = "grey";
            if(buildJobs != null && buildJobs.size() != 0){
                for(BuildJob buildJob : buildJobs){
                    if(buildJob.name.length()>25){
                        String name = buildJob.name.substring(0,22)+"...";
                        buildJob.name = name;
                    }
                    existingJobs.add(new BuildMonitorJob(buildJob));
                    if(buildJob.color.equals("blue")) blue++;
                    if(buildJob.color.equals("red")) red++;
                    if(buildJob.color.equals("yellow")) green++;
                }
                System.out.println("Blue builds: " + blue);
                System.out.println("Red builds: " + red);
                System.out.println("Yellow builds: " + green);

            }

            if(buildJobs.size() == 0 ) backgroundColor = "grey";
            else if(blue == buildJobs.size())  backgroundColor = "blue";
            else if (red > 0 ) backgroundColor = "red";
            else if(green > 0 ) backgroundColor = "purple";

             System.out.println(getBackgroundColour());
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
      }

    public static String backgroundColor;

    public static String getBackgroundColour(){
        return backgroundColor;
    }

    public List<BuildMonitorJob> getMonitorJobs(String url){
        refreshAllJobs(url);
        return existingJobs;
    }


}
