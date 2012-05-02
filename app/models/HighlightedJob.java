package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class HighlightedJob extends Model{

    @Id
    @Column(name = "ID", nullable = false)
    public Long id;

    @Constraints.Required
    @Column(name="NAME", nullable = false)
    public String name;

    public static Finder<Long,HighlightedJob> find = new Finder<Long,HighlightedJob>(
    Long.class, HighlightedJob.class
    );


}
