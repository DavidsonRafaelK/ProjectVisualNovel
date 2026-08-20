package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.commands.impl.base.ExitCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a ExitCommand from an XML element.
 * @author Adam Victor
 */
public enum ExitCommandFactory implements ConversationCommandFactory<ExitCommand> {

    INSTANCE;

    @Override
    public ExitCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        return new ExitCommand();
    }
}