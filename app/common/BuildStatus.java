package common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kostasmamalis
 * Date: 23/04/2012
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public enum BuildStatus {

    IN_PROGRESS("flashing"),FAILED("red"),PASSED("blue"),WITH_ERRORS("yellow"),NOT_CONFIGURED("grey");

    private String colour;

     BuildStatus(String colour) {
        this.colour = colour;
    }

    public String getColour(){
        return this.colour;
    }

    private static final Map<String, BuildStatus> lookup = new HashMap<String, BuildStatus>();
        static {
            for (BuildStatus d : BuildStatus.values())
                lookup.put(d.getColour(), d);

       }

    public static BuildStatus get(String s){
        return lookup.get(s);
    }
}
