import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;

public class Part extends SimProcess {

    public Part(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public void lifeCycle() throws SuspendExecution {
        MachineShop model = (MachineShop)getModel();
        boolean needsRefining = false;
        model.numberOfParts.update();
        model.processingQueue.insert(this);
        if(model.machineIsBusy){
            passivate();
        } else {
            model.machineIsBusy = true;
            model.processingQueue.removeFirst();
            if(!needsRefining){
                //sample processing time
                double processingTime = model.processingTime.sample();
                hold(new TimeSpan(processingTime, TimeUnit.MINUTES));
                model.machineIsBusy = false;
                model.inspectionQueue.insert(this);
                if(model.inspectorIsBusy){
                    passivate();
                } else {
                    model.inspectorIsBusy = true;
                    model.inspectionQueue.removeFirst();
                    //sample inspection time
                    double inspectionTime = model.inspectionTime.sample();
                    hold(new TimeSpan(inspectionTime, TimeUnit.MINUTES));
                    needsRefining = model.needsRefining.sample();
                    model.inspectorIsBusy = false;
                    if(needsRefining){

                    }
                }

            }



        }









    }
}
