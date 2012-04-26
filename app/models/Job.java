package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Job extends Model{

    @Id
    public Long id;

    @Constraints.Required
    public String name;


    public String latestBuild;


    public String url;


    public String colour;

    public String latestBuildUrl;


    public boolean active;

    public static Finder<Long,Job> find = new Finder<Long,Job>(
    Long.class, Job.class
    );


}
