package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.studies.PasswordPolicy;
import org.sagebionetworks.bridge.sdk.models.studies.Study;

public class CreateStudy {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Environment.STAGING);
        
        String studyId = "parkinson";

        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient client = session.getAdminClient();
        
        Study study = new Study();
        study.setIdentifier(studyId);
        study.setName("mPower");
        study.setConsentNotificationEmail("PDConsent@sagebase.org");
        study.setSupportEmail("PDapp@sagebase.org");
        study.setTechnicalEmail("bridgeit@sagebase.org");
        study.setMinAgeOfConsent(18);
        study.setPasswordPolicy(new PasswordPolicy(2, false, false, false, false));
        study.setSponsorName("The mPower Team");
        client.createStudy(study);
        session.signOut();
    }
}
