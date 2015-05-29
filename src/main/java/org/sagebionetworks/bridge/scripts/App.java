package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.BCSBackgroundSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PHQ8GAD7Survey;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.Utilities;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class App {

    public static void main(String[] args) throws Exception {
        
        Survey survey = BCSBackgroundSurvey.create();
        String string = Utilities.getMapper().writeValueAsString(survey);
        System.out.println(string);
        /*
        // Testing that this all works; it does.
        Config config = ClientProvider.getConfig();
        config.set(Environment.DEV);
        Session session = ClientProvider.signIn(config.getAdminCredentials());

        ResearcherClient client = session.getResearcherClient();
        ResourceList<SchedulePlan> plans = client.getSchedulePlans();
        
        for (SchedulePlan plan : plans) {
            System.out.println(plan);
        }
        */
    }

}
