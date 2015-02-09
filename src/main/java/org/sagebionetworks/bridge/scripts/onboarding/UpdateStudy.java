package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.studies.Study;

public class UpdateStudy extends BaseSignIn {
    public static void main(String[] args) {
        
        Session session = signIn("https://webservices.sagebridge.org", "api");
        
        ResearcherClient client = session.getResearcherClient();
        
        Study study = client.getStudy();
        study.setSupportEmail(null);
        study.setConsentNotificationEmail("bridge-testing+consent@sagebridge.org");
        client.updateStudy(study);
        //System.out.println(study);
    }
}
