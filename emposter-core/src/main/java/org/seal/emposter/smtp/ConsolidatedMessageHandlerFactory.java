package org.seal.emposter.smtp;

/*-
 * #%L
 * emposter
 * %%
 * Copyright (C) 2022 authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.helper.BasicMessageHandlerFactory;
import org.subethamail.smtp.helper.BasicMessageListener;

/**
 * By design subethasmtp handles each recipient in email separately.
 * This is not desired behaviour as it will lead to multiple email copies stored for each email.
 * Thus, aggregating recipients into one comma separated list to handle them all in one go.
 */
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
