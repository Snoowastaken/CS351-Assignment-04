import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;
import co.paralleluniverse.fibers.SuspendExecution;

public class Server extends SimProcess {

    public Server (Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public void lifeCycle() throws SuspendExecution {
        MachineShop model = (MachineShop)getModel();
    }
}
