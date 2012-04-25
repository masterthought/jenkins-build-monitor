package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: kostasmamalis
 * Date: 24/04/2012
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Job extends Model{

    @Id
    public String name;

    @Constraints.Required
    public String latestBuild;

    @Constraints.Required
    public String url;

    @Constraints.Required
    public String colour;

    public String latestBuildUrl;

    @Constraints.Required
    public boolean active;

    public static Finder<String,Job> find = new Finder<String,Job>(
    String.class, Job.class
  );
}
