package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.studies.Study;

public class CreateStudy {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Environment.STAGING);
        config.set(Props.STUDY_IDENTIFIER, "api");
        
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient client = session.getAdminClient();
        
        // Password!1
        Study study = new Study();
        study.setIdentifier("ohsu-molemapper");
        study.setName("Mole Mapper");
        study.setSponsorName("OHSU & Sage Bionetworks");
        study.setSupportEmail("molemapper@gmail.com");
        study.setConsentNotificationEmail("WarOnMelanoma@ohsu.edu");
        study.setTechnicalEmail("DanWebster13@gmail.com");
        client.createStudy(study);
        session.signOut();
    }
}
