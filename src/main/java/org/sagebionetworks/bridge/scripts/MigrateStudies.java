package org.sagebionetworks.bridge.scripts;

import java.util.Properties;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.DeveloperClient;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

public class MigrateStudies {

    public static void main(String[] args) {
        new MigrateStudies().run();
        
        
    }
    
    private void run() {
        DeveloperClient client = signIn(Environment.STAGING, "breastcancer");

        for (SchedulePlan plan : client.getSchedulePlans()) {
            Activity act = ((SimpleScheduleStrategy)plan.getStrategy()).getSchedule().getActivities().get(0);
            System.out.println(act);
        }
        for (Survey survey : client.getAllSurveysMostRecent()) {
            System.out.println(survey.getName() + " " + survey.getGuid());
        }
    }
    
    private DeveloperClient signIn(Environment env, String studyId) {
        Config config = ClientProvider.getConfig();
        config.set(env);
        
        Properties props = Scripts.loadProperties();
        SignInCredentials signIn = new SignInCredentials(
            studyId, props.getProperty("signin.username"), props.getProperty("signin.password"));
        
        Session session = ClientProvider.signIn(signIn);
        
        return session.getDeveloperClient();
    }

}
