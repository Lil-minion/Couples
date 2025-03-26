package com.github.lil_minion.client.data;

import com.github.lil_minion.model.mail.Inbox;

public class InboxVolatileData {

    private static Inbox clientInbox = null;

    public static Inbox getClientInbox() {
        return clientInbox;
    }

    public static void setClientInbox(Inbox clientInbox) {
        InboxVolatileData.clientInbox = clientInbox;
    }
}
