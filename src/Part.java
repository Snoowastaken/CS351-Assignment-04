import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;

public class Part extends SimProcess {

    public Part(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public void lifeCycle() throws SuspendExecution {
        MachineShop model = (MachineShop)getModel();
        model.numberOfParts.update();
        model.processingQueue.insert(this);
        //update tally of queue length
        model.processingQueueLength.update();
        if(model.machineIsBusy){
            passivate();
        } else {
            model.machineIsBusy = true;
            model.machineUtilization.update(1.0);
            model.processingQueue.removeFirst();
            double processingTime = model.processingTime.sample();
            hold(new TimeSpan(processingTime, TimeUnit.MINUTES));
            model.machineIsBusy = false;
            model.machineUtilization.update(0.0);
            model.inspectionQueue.insert(this);
            //update tally of queue length
            model.inspectionQueueLength.update();
            if(model.inspectorIsBusy){
                passivate();
            } else {
                model.inspectorIsBusy = true;
                //update accumulator of utilization
                model.inspectorUtilization.update(1.0);
                model.inspectionQueue.removeFirst();
                double inspectionTime = model.inspectionTime.sample();
                hold(new TimeSpan(inspectionTime, TimeUnit.MINUTES));
                model.inspectorIsBusy = false;
                model.inspectorUtilization.update(0.0);
                boolean needsRefining = model.needsRefining.sample();
                if(!needsRefining){
                    model.numberOfParts.update();
                } else {
                    model.processingQueue.insert(this);
                    if(model.machineIsBusy){
                        passivate();
                    } else {
                        model.machineIsBusy = true;
                        model.machineUtilization.update(1.0);
                        model.processingQueue.removeFirst();
                        double refiningTime = model.refiningTime.sample();
                        hold(new TimeSpan(refiningTime, TimeUnit.MINUTES));
                        model.machineIsBusy = false;
                        model.machineUtilization.update(0.0);
                        model.numberOfParts.update();
                    }
                }
            }

        }


    }
}
