//William Schauberger
import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.*;
import java.util.concurrent.TimeUnit;

public class MachineShop extends Model {
    /*State Variables for the model*/
    protected boolean machineIsBusy;
    protected boolean inspectorIsBusy;


    /* Structures used in our model. */
    protected ProcessQueue<Part> processingQueue;
    protected ProcessQueue<Part> inspectionQueue;


    /* Random number streams for the model. */
    protected ContDistExponential interArrivalTime;
    protected ContDistExponential processingTime;
    protected ContDistNormal inspectionTime;
    protected ContDistExponential refiningTime;
    protected BoolDistBernoulli needsRefining;


    /* Statistics for the model. */
    protected Tally responseTime;
    protected Count numberOfParts;
    protected Accumulate machineUtilization;
    protected Accumulate inspectorUtilization;


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
        Generator generator = new Generator(this, "Generator", true);
        generator.activate(new TimeSpan(0, TimeUnit.MINUTES));
    }

    //-----------------------------------------------------------
    /* Initialize the model. */
    public void init() {
        //state variables
        machineIsBusy = false;
        inspectorIsBusy = false;
        //queues
        processingQueue = new ProcessQueue<>(this, "Processing Queue", true, true);
        inspectionQueue = new ProcessQueue<>(this, "Inspection Queue", true, true);
        //random number streams
        interArrivalTime = new ContDistExponential(this, "Inter Arrival Time", 7.0, true, true);
        processingTime = new ContDistExponential(this, "Processing Time", 4.5, true, true);
        inspectionTime = new ContDistNormal(this, "Inspection Time", 5.0, 1.0, true, true);
        refiningTime = new ContDistExponential(this, "Refining Time", 3.0, true, true);
        needsRefining = new BoolDistBernoulli(this, "Needs Refining", 0.25, true, true);
        //statistics
        responseTime = new Tally(this, "Response Time", true, true);
        numberOfParts = new Count(this, "Number of Parts", true, true);
        machineUtilization = new Accumulate(this, "Machine Utilization", true, true);
        inspectorUtilization = new Accumulate(this, "Inspector Utilization", true, true);

    }

    //-----------------------------------------------------------
    /* Run the model */
    public static void main(String[] args){
        Experiment.setReferenceUnit(TimeUnit.MINUTES);

        MachineShop model = new MachineShop(null, "Machine Shop", true, true);
        Experiment exp = new Experiment("Machine Shop Model");
        model.connectToExperiment(exp);
        exp.setShowProgressBar(false);
        exp.stop(new TimeInstant(24, TimeUnit.HOURS));
        exp.tracePeriod(new TimeInstant(0, TimeUnit.MINUTES),
                new TimeInstant(360, TimeUnit.MINUTES));
        exp.debugPeriod(new TimeInstant(0, TimeUnit.MINUTES),
                new TimeInstant(360, TimeUnit.MINUTES));
        exp.start();
        exp.report();
        exp.finish();


    }
}
