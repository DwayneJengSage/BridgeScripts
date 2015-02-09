package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;

public class App {

    public static void main(String[] args) {
        // Testing that this all works; it does.
        Config config = ClientProvider.getConfig();
        config.set(Environment.DEV);
        Session session = ClientProvider.signIn(config.getAdminCredentials());

        ResearcherClient client = session.getResearcherClient();
        ResourceList<SchedulePlan> plans = client.getSchedulePlans();
        
        for (SchedulePlan plan : plans) {
            System.out.println(plan);
        }        
    }

}
