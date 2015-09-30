package org.sagebionetworks.bridge.scripts.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/** Helper class to wrap some Dynamo DB queries we make. */
// TODO: Consolidate this into shared package
public class DynamoHelper {
    private Table ddbHealthIdTable;

    /** Health ID table. */
    public final void setDdbHealthIdTable(Table ddbHealthIdTable) {
        this.ddbHealthIdTable = ddbHealthIdTable;
    }

    /**
     * Gets the user's health code from their health ID.
     *
     * @param healthId
     *         user's health ID
     * @return user's health code
     */
    public String getHealthCodeFromHealthId(String healthId) {
        // convert healthId to healthCode
        Item healthIdItem = ddbHealthIdTable.getItem("id", healthId);
        return healthIdItem.getString("code");
    }
}
