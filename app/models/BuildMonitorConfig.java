package models;

import BuildJobUtils.BuildMonitorJob;
import BuildJobUtils.JsonResolver;
import play.db.ebean.Model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import play.data.validation.Constraints;

@Entity
public class BuildMonitorConfig extends Model {

    @Id
    public Long id;

    public String name;

    public String buildUrl;

    public String backgroundColor;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<BuildJob> jobs;

    public static Finder<Long, BuildMonitorConfig> find = new Finder<Long, BuildMonitorConfig>(
            Long.class, BuildMonitorConfig.class
    );

    public static void create(BuildMonitorConfig config){
        config.save();
    }

    public static void update(BuildMonitorConfig config){
        BuildMonitorConfig existing = find.where().eq("name", config.name).findUnique();
        if(existing != null) {
            config.update();
        }
        else {
            config.save();
        }
    }

    public static BuildJob containsJob(BuildMonitorConfig config, String name){
        if(config.jobs != null && config.jobs.size() != 0 ){
            for(BuildJob job : config.jobs){
                if(job.name.equalsIgnoreCase(name)) return job;
            }
        }
        return null;
    }




}
