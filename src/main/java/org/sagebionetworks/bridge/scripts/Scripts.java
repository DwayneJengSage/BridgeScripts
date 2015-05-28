package org.sagebionetworks.bridge.scripts;

import static com.google.common.base.Preconditions.checkNotNull;

import org.joda.time.LocalDate;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.users.ConsentSignature;
import org.sagebionetworks.bridge.sdk.models.users.SharingScope;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

import com.google.common.collect.Lists;

public class Scripts {

    public static Session signIn(String url, String studyIdentifier) {
        return signIn(ClientProvider.getConfig().getAdminCredentials(), Environment.DEV, studyIdentifier);
    }
    
    public static Session signIn(SignInCredentials credentials, Environment env, String studyIdentifier) {
        Config config = ClientProvider.getConfig();
        config.set(env);
        config.set(Props.STUDY_IDENTIFIER, studyIdentifier);
        Session session = null;
        try {
            session = ClientProvider.signIn(credentials);
        } catch(ConsentRequiredException e) {
            session = e.getSession();
            session.getUserClient().consentToResearch(
                new ConsentSignature("[Test User]", LocalDate.parse("1970-12-30"), null, null),
                SharingScope.NO_SHARING);
        }
        return session;
    }

    /**
     * If you want to have a question that displays the choices "Yes" and "No", 
     * then it's not a boolean constraint because you have to specify the labels.
     * The return type can be the string "true"/"false" and therefore, a boolean.
     * @return
     */
    public static MultiValueConstraints booleanish() {
        MultiValueConstraints mvc = new MultiValueConstraints(DataType.BOOLEAN);
        mvc.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Yes", "true"),
            new SurveyQuestionOption("No", "false")
        ));
        return mvc;
    }
    public static void setMostRecentlyPublishedSurveyActivity(Schedule schedule, String guid) {
        checkNotNull(schedule);
        checkNotNull(guid);
        
        Config config = ClientProvider.getConfig();
        String url = config.getEnvironment().getUrl() + config.getRecentlyPublishedSurveyUserApi(guid);
        schedule.addActivity(new Activity("Take survey", url));
    }
    
    public static void setSurveyActivity(Schedule schedule, GuidCreatedOnVersionHolder keys) {
        checkNotNull(schedule);
        checkNotNull(keys);        
        
        schedule.addActivity(new Activity("Take survey", keys));
    }
    public static void setTaskActivity(Schedule schedule, String taskIdentifier) {
        checkNotNull(taskIdentifier);
        schedule.addActivity(new Activity("Do task", taskIdentifier));
    }
    public static SurveyReference getSurveyActivityKeys(Schedule schedule) {
        return schedule.getActivities().get(0).getSurvey();
    }

}
