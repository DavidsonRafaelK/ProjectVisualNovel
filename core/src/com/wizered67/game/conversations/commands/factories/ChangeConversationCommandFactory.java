package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.commands.impl.base.ChangeConversationCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a ChangeConversationCommand from an XML element.
 * @author Adam Victor
 */
public enum ChangeConversationCommandFactory implements ConversationCommandFactory<ChangeConversationCommand> {

    INSTANCE;

    @Override
    public ChangeConversationCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        String newConv = element.getAttribute("conv", null);
        String newBranch = element.getAttribute("branch", "default");
        if (newConv == null) {
            throw new GdxRuntimeException("No new conversation specified for new conversation command.");
        }
        return new ChangeConversationCommand(newConv, newBranch);
    }
}
