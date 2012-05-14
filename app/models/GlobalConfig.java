package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class GlobalConfig extends Model{


    @Id
    public Long id;

    public int refreshRateInSeconds;

    public boolean soundsActivated;

    public String pathToSounds;

    public boolean playSoundOnFailure;

    public boolean playSoundOnSuccess;

    public boolean playSoundOnUnstable;

    public boolean displayAvatars;

    public boolean displayLatestSuccessful;

    public String description;

    public static Finder<Long,GlobalConfig> find = new Finder<Long,GlobalConfig>(
    Long.class, GlobalConfig.class
    );



}
