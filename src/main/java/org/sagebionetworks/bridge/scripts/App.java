package org.sagebionetworks.bridge.scripts;

import java.util.List;
import java.util.Properties;

import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.BCPTSymptomsSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.BCSBackgroundSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.ParQPlusSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PAOFISurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PHQ8GAD7Survey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PSQISurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.optional.FeedbackSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.optional.MyThoughtsSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.trimonthly.SF36Survey;
import org.sagebionetworks.bridge.scripts.breastcancer.weekly.WeeklySurvey;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

import com.google.common.collect.Lists;

public class App {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Config config = ClientProvider.getConfig();
        config.set(Environment.STAGING);
        
        Properties props = Scripts.loadProperties();
        SignInCredentials signIn = new SignInCredentials(props.getProperty("signin.study"),
                        props.getProperty("signin.username"), props.getProperty("signin.password"));
        
        Session session = ClientProvider.signIn(signIn);
        
        ResearcherClient client = session.getResearcherClient();
        
        List<Class<? extends SurveyProvider>> providers = Lists.newArrayList(
            BCPTSymptomsSurvey.class, 
            BCSBackgroundSurvey.class,
            ParQPlusSurvey.class,
            PAOFISurvey.class,
            PHQ8GAD7Survey.class, 
            PSQISurvey.class,
            SF36Survey.class,
            WeeklySurvey.class,
            FeedbackSurvey.class, 
            MyThoughtsSurvey.class
        );
        
        for (Class<? extends SurveyProvider> providerClass : providers) {
            SurveyProvider provider = providerClass.newInstance();
            Survey survey = provider.createSurvey();
            GuidCreatedOnVersionHolder keys = client.createSurvey(survey);
            keys = client.publishSurvey(survey);
            SchedulePlan plan = provider.createSchedulePlan(keys.getGuid());
            client.createSchedulePlan(plan);
        }
    }

}
