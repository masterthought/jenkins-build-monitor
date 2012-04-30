package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;


@Entity
public class Job extends Model{

    @Id
    @Column(name = "ID", nullable = false)
    public Long id;

    @Constraints.Required
    @Column(name="NAME", nullable = false)
    public String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="JOBGRP")
    public JobGroup jobGroup;

    public String latestBuild;

    @Constraints.Required
    @Column(name="URL", nullable = false)
    public String url;


    public String colour;

    public String latestBuildUrl;


    public boolean active;

    public static Finder<Long,Job> find = new Finder<Long,Job>(
    Long.class, Job.class
    );


}
