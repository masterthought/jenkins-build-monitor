package BuildJobUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonResolver {

    public static List<BuildJob> getAvailableBuildJobs(String url) throws Exception{
        String s = getOverviewJsonString(url).toString();
        List<BuildJob> result = new ArrayList<BuildJob>();
        if(s != null ){

            BuildServerInfo info = new Gson().fromJson(s, BuildServerInfo.class);
            if(info == null) return result;

            if(info.jobs != null && info.jobs.size() != 0 ) {
                for(BuildJob job : info.jobs){
                    System.out.println(job.name + " is identified with urL:" + job.url);
                    result.add(job);
                }
            }
        }
        return result;
    }


    public static StringBuffer getJobJsonString(BuildJob job) throws Exception{
        if(job == null) return null;
        return getJsonFromUrl(job.url + "api/json");
    }

    public static BuildJobVerbose getJobVerbose(BuildMonitorJob job) throws Exception{
        return getJobVerbose(job.getBuildJob());
    }

    public static BuildJobVerbose getJobVerbose(BuildJob job) throws Exception{
        String s = getJobJsonString(job).toString();
        Gson gson = new Gson();
        return s != null ? gson.fromJson(s, BuildJobVerbose.class) : null;
    }


    public static StringBuffer getOverviewJsonString(String url) throws Exception {
        return getJsonFromUrl(url + "/api/json");
    }

    public static StringBuffer getAllJsonString(String url) throws Exception{
        return getJsonFromUrl(url + "/api/json?depth=1");
    }

    private static StringBuffer getJsonFromUrl(String url) throws Exception{
       try{  URL buildServerUrl = new URL(url);
       URLConnection bc = buildServerUrl.openConnection();
        BufferedReader in;
       try{
        in = new BufferedReader(new InputStreamReader(bc.getInputStream()));
           String inputLine;
       StringBuffer s = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
           s.append(inputLine);
        in.close();
        return s;
       } catch(java.io.FileNotFoundException fileNotFound){
           System.out.println("Wrong URL");
           return new StringBuffer("");
       }
       } catch(MalformedURLException me){
          System.out.println("Wrong URL");
           return new StringBuffer("");
       } catch (IOException io){
           System.out.println("URL doesn't have any api json. Please reconfigure");
           return new StringBuffer("");
       }

    }


}

class BuildServerInfo{

    public BuildServerInfo(){

    }
    public List<BuildJob> jobs;


}

