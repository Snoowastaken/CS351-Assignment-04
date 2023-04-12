//William Schauberger
import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;
import co.paralleluniverse.fibers.SuspendExecution;

public class Generator extends SimProcess {

    public Generator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public void lifeCycle() throws SuspendExecution {
        MachineShop model = (MachineShop)getModel();
        while(true){
            double interArrivalTime = model.interArrivalTime.sample();
            hold(new TimeSpan(interArrivalTime, TimeUnit.MINUTES));
            Part part = new Part(model, "Part", true);
            part.activate();
        }
    }
}
