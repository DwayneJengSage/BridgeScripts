package org.sagebionetworks.bridge.scripts.misc;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.studies.Study;

public class Exploratory {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, "https://api-staging.sagebridge.org");
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        
        AdminClient client = session.getAdminClient();
        
        ResourceList<Study> studies = client.getAllStudies();
        for (Study study : studies) {
            if (study.getIdentifier().startsWith("sdk-")) {
                client.deleteStudy(study.getIdentifier());
                System.out.println("Deleting study: " + study.getIdentifier());
            }
        }
        
    }
}
