package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.BCPTSymptomsSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.BCSBackgroundSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PAOFISurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PHQ8GAD7Survey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PSQISurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.optional.FeedbackSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.optional.MyThoughtsSurvey;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class App {

    public static void main(String[] args) throws Exception {
        Config config = ClientProvider.getConfig();
        config.set(Environment.LOCAL);
        Session session = ClientProvider.signIn(config.getAccountCredentials());
        
        ResearcherClient client = session.getResearcherClient();
        
        // Share The Journey
        Survey survey = BCPTSymptomsSurvey.create();
        GuidCreatedOnVersionHolder keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        SchedulePlan plan = BCPTSymptomsSurvey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
        
        survey = BCSBackgroundSurvey.create();        
        keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        plan = BCSBackgroundSurvey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
        
        survey = PAOFISurvey.create();        
        keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        plan = PAOFISurvey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
        
        survey = PHQ8GAD7Survey.create();        
        keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        plan = PHQ8GAD7Survey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
        
        survey = PSQISurvey.create();        
        keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        plan = PSQISurvey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
        
        survey = FeedbackSurvey.create();        
        keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        plan = FeedbackSurvey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
        
        survey = MyThoughtsSurvey.create();        
        keys = client.createSurvey(survey);
        keys = client.publishSurvey(survey);
        plan = MyThoughtsSurvey.createSchedulePlan(keys.getGuid());
        client.createSchedulePlan(plan);
    }

}
