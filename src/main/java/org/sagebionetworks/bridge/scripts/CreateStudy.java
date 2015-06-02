package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.studies.Study;

public class CreateStudy {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Environment.DEV);
        
        String studyId = "parkinson";

        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient client = session.getAdminClient();
        
        Study oStudy = client.getStudy("parkinson");
        
        Study study = new Study();
        study.setName(oStudy.getName() + " v2");
        study.setIdentifier(studyId + "2");
        study.setMinAgeOfConsent(oStudy.getMinAgeOfConsent());
        study.setMaxNumOfParticipants(oStudy.getMaxNumOfParticipants());
        study.setConsentNotificationEmail(oStudy.getConsentNotificationEmail());
        study.setSupportEmail(oStudy.getSupportEmail());
        study.setUserProfileAttributes(oStudy.getUserProfileAttributes());
        client.createStudy(study);
        session.signOut();
        
    }
}
