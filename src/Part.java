//William Schauberger
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

public class Part extends SimProcess {

    public Part(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public void lifeCycle() throws SuspendExecution {
        MachineShop model = (MachineShop) getModel();
        //get current time
        double startTime = presentTime().getTimeAsDouble(TimeUnit.MINUTES);
        model.processingQueue.insert(this);
        model.numberOfParts.update();
        if (model.machineIsBusy) {
            passivate();
        } else {
            model.machineIsBusy = true;
            model.machineUtilization.update(100.0);
            model.processingQueue.removeFirst();
            double processingTime = model.processingTime.sample();
            hold(new TimeSpan(processingTime, TimeUnit.MINUTES));
            model.machineIsBusy = false;
            model.machineUtilization.update(0.0);
            model.sendTraceNote("Part " + this.getName() + " has been processed");
            model.inspectionQueue.insert(this);
            if (model.inspectorIsBusy) {
                passivate();
            } else {
                model.inspectorIsBusy = true;
                //update accumulator of utilization
                model.inspectorUtilization.update(100.0);
                model.inspectionQueue.removeFirst();
                double inspectionTime = model.inspectionTime.sample();
                hold(new TimeSpan(inspectionTime, TimeUnit.MINUTES));
                model.inspectorIsBusy = false;
                model.inspectorUtilization.update(0.0);
                boolean needsRefining = model.needsRefining.sample();
                if (!needsRefining) {
                    model.numberOfParts.update(-1);
                    //get current time
                    double endTime = presentTime().getTimeAsDouble(TimeUnit.MINUTES);
                    model.responseTime.update(endTime - startTime);
                    model.sendTraceNote("Part " + this.getName() + " has been inspected");
                } else {
                    model.processingQueue.insert(this);
                    if (model.machineIsBusy) {
                        passivate();
                    } else {
                        model.machineIsBusy = true;
                        model.machineUtilization.update(100.0);
                        model.processingQueue.removeFirst();
                        double refiningTime = model.refiningTime.sample();
                        hold(new TimeSpan(refiningTime, TimeUnit.MINUTES));
                        model.machineIsBusy = false;
                        model.machineUtilization.update(0.0);
                        model.numberOfParts.update(-1);
                        //get current time
                        double endTime = presentTime().getTimeAsDouble(TimeUnit.MINUTES);
                        model.responseTime.update(endTime - startTime);
                        model.sendTraceNote("Part " + this.getName() + " has been refined");
                    }
                }
            }

        }


    }
}
