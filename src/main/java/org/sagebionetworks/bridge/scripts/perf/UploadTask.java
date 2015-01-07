package org.sagebionetworks.bridge.scripts.perf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.exceptions.BridgeSDKException;
import org.sagebionetworks.bridge.sdk.models.UploadRequest;
import org.sagebionetworks.bridge.sdk.models.UploadSession;

public class UploadTask extends PerfTask {
    
    // TODO: This is a tiny file, what about something bigger?
    String filePath = "src/main/resources/test.json";
    UploadRequest request = new UploadRequest()
        .setContentLength((int) new File(filePath).length())
        .setContentMd5(createMd5(filePath))
        .setContentType("text/plain")
        .setName(filePath);

    public UploadTask(Session session) {
        super(session);
    }
    
    public void command() {
        UploadSession session = client.requestUploadSession(request);
        client.upload(session, request, filePath);
    }

    private String createMd5(String filePath) {
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.encodeBase64String(DigestUtils.md5(b));
        } catch (IOException e) {
            throw new BridgeSDKException(e);
        }
    }

}
