package BuildJobUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonResolver {

    public static void parse() throws Exception{
        String s = getOverviewJsonString().toString();
        Gson gson = new Gson();

        if(s != null ){

            BuildServerInfo info = gson.fromJson(s, BuildServerInfo.class);

            for(BuildJob job : info.jobs){
                System.out.println(job.name + " is " + job.color);
            }
        }
    }

    public static List<BuildJob> getAvailableBuildJobs(String url) throws Exception{
        String s = getOverviewJsonString().toString();
        List<BuildJob> result = new ArrayList<BuildJob>();
        if(s != null ){

            BuildServerInfo info = new Gson().fromJson(s, BuildServerInfo.class);

            for(BuildJob job : info.jobs){
                System.out.println(job.name + " is identified with urL:" + job.url);
                result.add(job);
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

    public static void printAll() throws Exception{
        System.out.println(getAllJsonString());
    }

    public static StringBuffer getOverviewJsonString() throws Exception {
        return getJsonFromUrl("http://localhost:8080/api/json");
    }

    public static StringBuffer getAllJsonString() throws Exception{
        return getJsonFromUrl("http://localhost:8080/api/json?depth=1");
    }

    private static StringBuffer getJsonFromUrl(String url) throws Exception{
        URL buildServerUrl = new URL(url);
       URLConnection bc = buildServerUrl.openConnection();

       BufferedReader in = new BufferedReader(new InputStreamReader(bc.getInputStream()));
       String inputLine;
       StringBuffer s = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
           s.append(inputLine);
        in.close();
        return s;
    }

    public static void main(String[] args) throws Exception {
        parse();
        printAll();
    }


}

class BuildServerInfo{

    public BuildServerInfo(){

    }
    public List<BuildJob> jobs;


}

