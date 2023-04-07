import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class Part extends SimProcess {

    public Part(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public void lifeCycle() throws SuspendExecution {
        MachineShop model = (MachineShop)getModel();



    }
}
