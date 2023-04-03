import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.*;
import java.util.concurrent.TimeUnit;

public class MachineShop extends Model {
    /*State Variables for the model*/


    /* Structures used in our model. */


    /* Random number streams for the model. */
    protected ContDistExponential interArrivalTime;
    protected ContDistExponential processingTime;
    protected ContDistNormal inspectionTime;
    protected ContDistExponential refiningTime;
    protected BoolDistBernoulli needsRefining;


    /* Statistics for the model. */


    //-----------------------------------------------------------
    /* Constructor for the model. */
    public MachineShop(Model owner, String name, boolean showInReport, boolean showInTrace) {
        super(owner, name, showInReport, showInTrace);
    }

    //-----------------------------------------------------------
    /* Description of the model. */
    public String description() {
        return "This is a simple model of a machine shop.";
    }

    //-----------------------------------------------------------
    /* Do initial schedules. */
    public void doInitialSchedules() {
    }

    //-----------------------------------------------------------
    /* Initialize the model. */
    public void init() {
    }

    //-----------------------------------------------------------
    /* Run the model */
    public static void main(String[] args){







    }




}
