package org.sagebionetworks.bridge.scripts.perf;

import org.joda.time.DateTime;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.studies.Tracker;
import org.sagebionetworks.bridge.sdk.models.users.HealthDataRecord;

public class HealthDataTask extends PerfTask {

    
    public HealthDataTask(String host, String username, String password) {
        super(host, username, password);
    }
    
    @Override
    public void command() {
        Tracker tracker = getBloodPressureTracker();

        DateTime startDate = DateTime.now().minusDays(14);
        DateTime endDate = DateTime.now();
        
        ResourceList<HealthDataRecord> records = client.getHealthDataRecordsInRange(tracker, startDate, endDate);
        for (HealthDataRecord record : records) {
            System.out.println(record.toString());
        }
    }
    
    private Tracker getBloodPressureTracker() {
        ResourceList<Tracker> trackers = client.getAllTrackers(); // this is awkward.
        for (Tracker tracker : trackers) {
            if (tracker.getIdentifier().equals("pb-tracker")) {
                return tracker;
            }
        }
        return null;
    }

}
