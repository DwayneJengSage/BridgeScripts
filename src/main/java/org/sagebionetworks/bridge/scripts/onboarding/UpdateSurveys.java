package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class UpdateSurveys {
    
    public static void main(String[] args) throws Exception {
        Config config = ClientProvider.getConfig();
        config.set(Environment.PRODUCTION);
        config.set(Props.STUDY_IDENTIFIER, "breastcancer");
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        ResearcherClient client = session.getResearcherClient();
        
        Survey survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("bcs-background");
        GuidCreatedOnVersionHolder keys = client.versionSurvey(survey);
        
        Survey update = BreastcancerBackgroundSurvey.create();
        update.setGuidCreatedOnVersionHolder(keys);
        client.updateSurvey(update);
        client.publishSurvey(update);
/*
        for (SchedulePlan plan2 : client.getSchedulePlans()) {
            System.out.println(plan2);
        }*/
    }
    
}
