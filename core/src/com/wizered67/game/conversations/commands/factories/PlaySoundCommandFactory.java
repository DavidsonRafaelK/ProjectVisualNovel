package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.commands.impl.audio.PlaySoundCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a PlaySoundCommand from an XML element.
 *
 * @author Adam Victor
 */
public enum PlaySoundCommandFactory implements ConversationCommandFactory<PlaySoundCommand> {

    INSTANCE;

    @Override
    public PlaySoundCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        String id = element.getAttribute("id");
        return new PlaySoundCommand(id);
    }
}