package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;


@Entity
public class JobGroup extends Model{

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Constraints.Required
    @Column(name = "JOB_GROUP_NAME", nullable = false)
    public String name;

    @Constraints.Required
    public String groupName;

    @OneToMany(mappedBy="jobGroup")
    private List<Job> jobs;

    public static Finder<Long,JobGroup> find = new Finder<Long,JobGroup>(
    Long.class, JobGroup.class
    );


    public static List<JobGroup> findByName(String jobGroupName) {
        return find.where().contains("name", jobGroupName).findList();
    }

}
