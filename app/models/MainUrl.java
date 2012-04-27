package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class MainUrl extends Model{

    @Id
    public Long id;

    @Constraints.Required
    public String url;

    public static Finder<Long,MainUrl> find = new Finder<Long,MainUrl>(
    Long.class, MainUrl.class
    );

    public String getUrl(){
        return this.url;
    }
}
