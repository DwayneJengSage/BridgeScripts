package org.sagebionetworks.bridge.scripts.perf;

import java.util.concurrent.Callable;

import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.UserClient;

public abstract class PerfTask implements Callable<Boolean> {
    
    protected Session session;
    protected UserClient client;
    protected ResearcherClient researcherClient;
    
    public PerfTask(Session session) {
        this.session = session;
        client = this.session.getUserClient();
        researcherClient = this.session.getResearcherClient();
    }
    
    @Override
    public Boolean call() {
        command();
        return Boolean.TRUE;
    }
    
    public abstract void command();
    
}
