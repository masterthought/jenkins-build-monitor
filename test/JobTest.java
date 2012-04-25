import models.Job;
import org.junit.*;

public class JobTest {

    @Test
    public void it_should_persist(){
        Job job = new Job();
        job.name = "test";
        job.colour = "red";
        job.latestBuild = "111";
        job.save();


        Assert.assertNotNull(job.find.byId("test"));
    }
}
