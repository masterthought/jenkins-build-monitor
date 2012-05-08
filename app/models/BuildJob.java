package models;

import BuildJobUtils.BuildJobVerbose;
import BuildJobUtils.JsonResolver;
import play.db.ebean.Model;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public class BuildJob extends Model{


    @Id
    public Long id;

    @Column(length=1024)
    public String name;

    @Column(length=1024)
    public String url;

    public String color;

    public String buildNumber;

    public boolean hidden;

    public boolean highlight;

    public boolean inProgress;

    public Integer displayOrder;

    @ManyToMany(mappedBy="jobs")
    public List<BuildMonitorConfig> buildMonitorConfig;

    public static Finder<Long,BuildJob> find = new Finder<Long,BuildJob>(
    Long.class, BuildJob.class
    );

    public String getLatestBuildUrl(){
        return this.url+ "/" + buildNumber;
    }

    public String getName(){
        return this.name.length() < 35 ? name : name.substring(0,31) + "...";

    }

    public static void create(BuildJob job){ job.save();}

    public static BuildJob findByNameAndUrl(String name, String url){
        return find.where().eq("name",name).findUnique().find.where().eq("url",url).findUnique();
    }





}
