package controllers;

/**
 * Created by IntelliJ IDEA.
 * User: kostasmamalis
 * Date: 23/04/2012
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class BuildMonitorJob {

    private BuildStatus status;
    private String latestBuild;
    private Build[] previousBuilds;
    private boolean active = false;
    private BuildJob buildJob;

    public BuildMonitorJob(BuildJob buildJob){
        this.buildJob = buildJob;
        this.active = true;
        setVerbose();

    }

    public boolean inProgress(){

        return this.verbose.builds == null ||
                this.verbose.builds.size() == 0 ?
                        false :
                            !this.verbose.builds.get(0).number.equals(this.verbose.lastCompletedBuild.number);
    }

    public BuildStatus getBuildStatus(){
        return this.status;
    }

    public String getColour(){
        return this.buildJob.color;
    }

    public String getLatestBuildUrl(){
       return verbose.builds.size() != 0 ? verbose.builds.get(0).url : "#";
    }

    public String getLatestBuildNumber(){
       return verbose.builds.size() != 0 ? verbose.builds.get(0).number : "<not available>";
    }

    private BuildJobVerbose verbose;

    public void setVerbose(){
        try {
            this.verbose = JsonResolver.getJobVerbose(this);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }



    public String getName(){
        return this.buildJob.name;
    }

    public String getUrl(){
        return this.buildJob.url;
    }


    public void setBuildStatus(BuildStatus status){
        this.status = status;
    }

    public BuildJob getBuildJob(){
        return this.buildJob;
    }

    public void setLatestBuild(String latestBuild){
         this.latestBuild = latestBuild;
    }

    public void setPreviousBuilds(Build[] previousBuilds){
        this.previousBuilds = previousBuilds;
    }

    public Build[] getPreviousBuilds(){
        return this.previousBuilds;
    }


    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    public String getLatestBuild(){
         return this.latestBuild;
    }

    public boolean isActive(){
        return this.active;
    }


}
