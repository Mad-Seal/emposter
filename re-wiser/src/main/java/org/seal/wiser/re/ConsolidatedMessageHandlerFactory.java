package org.seal.wiser.re;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.helper.BasicMessageHandlerFactory;
import org.subethamail.smtp.helper.BasicMessageListener;

public class ConsolidatedMessageHandlerFactory implements MessageHandlerFactory {

    private BasicMessageListener listener;

    public ConsolidatedMessageHandlerFactory(BasicMessageListener listener) {
        this.listener = listener;
    }

    @Override
    public MessageHandler create(MessageContext context) {
        return new ConsolidatedMessageHandler(context, listener, 0);
    }

    public static class ConsolidatedMessageHandler extends BasicMessageHandlerFactory.BasicMessageHandler {

        private String allRecipients;

        public ConsolidatedMessageHandler(MessageContext context, BasicMessageListener listener, int maxMessageSize) {
            super(context, listener, maxMessageSize);
        }

        @Override
        public void recipient(String recipient) throws RejectException {
            if (allRecipients == null) {
                allRecipients = recipient;
            } else {
                allRecipients = allRecipients + "," + recipient;
            }
            super.recipient(allRecipients);
        }
    }
}
