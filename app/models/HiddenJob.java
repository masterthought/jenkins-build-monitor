package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class HiddenJob extends Model{

    @Id
    @Column(name = "ID", nullable = false)
    public Long id;

    @Constraints.Required
    @Column(name="NAME", nullable = false)
    public String name;

    public static Finder<Long,HiddenJob> find = new Finder<Long,HiddenJob>(
    Long.class, HiddenJob.class
    );


}
