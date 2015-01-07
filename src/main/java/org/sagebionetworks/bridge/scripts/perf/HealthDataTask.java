package org.sagebionetworks.bridge.scripts.perf;

import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.studies.Tracker;
import org.sagebionetworks.bridge.sdk.models.users.HealthDataRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;

/**
 * Submit a request with 30 records, then retrieve all records just submitted. 
 *
 */
public class HealthDataTask extends PerfTask {

    private Tracker tracker;
    private Random rand = new Random();
    
    public HealthDataTask(Session session) {
        super(session);
        tracker = getBloodPressureTracker();
    }
    
    @Override
    public void command() {
        DateTime startDate = DateTime.now();
        submitTrackerRecord();
        DateTime endDate = DateTime.now();
        ResourceList<HealthDataRecord> records = client.getHealthDataRecordsInRange(tracker, startDate, endDate);
        System.out.println("Current count: " + records.getTotal());
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
    
    private void submitTrackerRecord() {
        List<HealthDataRecord> list = Lists.newArrayList();
        
        for (int i=0; i < 30; i++) {
            HealthDataRecord record = new HealthDataRecord();
            DateTime now = DateTime.now();
            record.setStartDate(now);
            record.setEndDate(now);
            
            ObjectNode data = JsonNodeFactory.instance.objectNode();
            data.put("systolic", 90 + rand.nextInt(11));
            data.put("diastolic", 70 + rand.nextInt(11));
            record.setData((JsonNode)data);
            list.add(record);
        }
        client.addHealthDataRecords(tracker, list);
    }

}
