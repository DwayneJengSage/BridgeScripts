package org.sagebionetworks.bridge.scripts.perf;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.UserClient;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

public abstract class PerfTask implements Runnable {
    
    protected Session session;
    protected UserClient client;
    
    public PerfTask(String host, String username, String password) {
        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, host);

        SignInCredentials credentials = new SignInCredentials(username, password);
        this.session = ClientProvider.signIn(credentials);
        client = this.session.getUserClient();
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                command();
                System.out.println("Thread " + Thread.currentThread().getId() + " sleeping");
                Thread.sleep(500);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public abstract void command();
    
}
