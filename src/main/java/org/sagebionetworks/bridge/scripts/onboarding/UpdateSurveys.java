package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class UpdateSurveys {
    
    public static void main(String[] args) throws Exception {
        Config config = ClientProvider.getConfig();
        config.set(Environment.PROD);
        config.set(Props.STUDY_IDENTIFIER, "parkinson");
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        ResearcherClient client = session.getResearcherClient();
        
        Survey enrollment = new ParkinsonEnrollmentSurvey();
        Survey survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("parkinson-enrollment");
        enrollment.setGuidCreatedOnVersionHolder(survey);
        client.versionUpdateAndPublishSurvey(enrollment, true);
        
        Survey monthly = new ParkinsonMonthlySurvey();
        survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("parkinson-monthly");
        monthly.setGuidCreatedOnVersionHolder(survey);
        client.versionUpdateAndPublishSurvey(monthly, true);
        
        Survey weekly = new ParkinsonWeeklySurvey();
        survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("parkinson-weekly");
        weekly.setGuidCreatedOnVersionHolder(survey);
        client.versionUpdateAndPublishSurvey(weekly, true);
        
        session.signOut();
        config.set(Environment.PROD);
        config.set(Props.STUDY_IDENTIFIER, "breastcancer");
        session = ClientProvider.signIn(config.getAdminCredentials());
        client = session.getResearcherClient();
        
        enrollment = new BreastcancerEnrollmentSurvey();
        survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("bcs-enrollment");
        enrollment.setGuidCreatedOnVersionHolder(survey);
        client.versionUpdateAndPublishSurvey(enrollment, true);
        
        monthly = new BreastcancerMonthlySurvey();
        survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("bcs-monthly");
        monthly.setGuidCreatedOnVersionHolder(survey);
        client.versionUpdateAndPublishSurvey(monthly, true);
        
        weekly = new BreastcancerWeeklySurvey();
        survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("bcs-weekly");
        weekly.setGuidCreatedOnVersionHolder(survey);
        client.versionUpdateAndPublishSurvey(weekly, true);
        session.signOut();
    }
    
}
