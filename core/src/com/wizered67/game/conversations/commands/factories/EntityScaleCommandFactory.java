package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.commands.impl.scene.EntityScaleCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a EntityScaleCommand from an XML element.
 *
 * @author Adam Victor
 */
public enum EntityScaleCommandFactory implements ConversationCommandFactory<EntityScaleCommand> {

    INSTANCE;

    @Override
    public EntityScaleCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        return EntityScaleCommand.makeCommand(null, element);
    }
}