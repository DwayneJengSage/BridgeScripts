package org.sagebionetworks.bridge.scripts.misc;

import org.joda.time.DateTime;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.UserClient;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.holders.GuidVersionHolder;
import org.sagebionetworks.bridge.sdk.models.studies.Tracker;
import org.sagebionetworks.bridge.sdk.models.users.HealthDataRecord;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Exploratory {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, "https://api-develop.sagebridge.org");
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        
        UserClient client = session.getUserClient();
        
        Tracker tracker = client.getAllTrackers().get(0);
        
        /*
        HealthDataRecord record = new HealthDataRecord();
        record.setStartDate(DateTime.now());
        record.setEndDate(DateTime.now());
        ObjectNode data = JsonNodeFactory.instance.objectNode();
        data.put("diastolic", 180);
        data.put("systolic", 100);
        record.setData(data);
        
        client.addHealthDataRecords(tracker, Lists.newArrayList(record));
        */
        ResourceList<HealthDataRecord> records = client.getHealthDataRecordsInRange(tracker, DateTime.now().minus(7*24*60*60*1000), DateTime.now());
        
        HealthDataRecord record = records.get(0);
        System.out.println("record before update: " + record.getVersion());
        
        ((ObjectNode)record.getData()).put("systolic", 101);
        GuidVersionHolder keys = client.updateHealthDataRecord(tracker, record);
        
        System.out.println("keys: " + keys.getVersion());
        System.out.println("record after update: " + record.getVersion());
    }
}
