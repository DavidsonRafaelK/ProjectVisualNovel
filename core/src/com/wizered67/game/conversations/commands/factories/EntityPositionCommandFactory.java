package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.commands.impl.scene.EntityPositionCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a EntityPositionCommand from an XML element.
 *
 * @author Adam Victor
 */
public enum EntityPositionCommandFactory implements ConversationCommandFactory<EntityPositionCommand> {

    INSTANCE;

    @Override
    public EntityPositionCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        return EntityPositionCommand.makeCommand(null, element);
    }
}