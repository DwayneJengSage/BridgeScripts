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
        
        Study study = client.getStudy("api");
        study.getUserProfileAttributes().add("phone");
        study.getUserProfileAttributes().add("can_be_recontacted");
        client.updateStudy(study);
        session.signOut();
    }
}
