package org.sagebionetworks.bridge.scripts.perf;

public class PerfRunner {

    public static void main(String[] args) {
        HealthDataTask task = new HealthDataTask("http://localhost:9000", "alxdark", "P4ssword");
        task.command();
    }

}
