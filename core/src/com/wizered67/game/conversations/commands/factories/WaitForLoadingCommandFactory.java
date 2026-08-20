package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.commands.impl.loading.WaitForLoadingCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a WaitForLoadingCommand from an XML element.
 *
 * @author Adam Victor
 */
public enum WaitForLoadingCommandFactory implements ConversationCommandFactory<WaitForLoadingCommand> {

    INSTANCE;

    @Override
    public WaitForLoadingCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        return new WaitForLoadingCommand();
    }
}