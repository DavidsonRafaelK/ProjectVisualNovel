package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.conversations.ConversationController;
import com.wizered67.game.conversations.commands.impl.scripting.ExecuteScriptCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;

/**
 * Factory for creating a ExecuteScriptCommand from an XML element.
 *
 * @author Adam Victor
 */
public enum ExecuteScriptCommandFactory implements ConversationCommandFactory<ExecuteScriptCommand> {

    INSTANCE;

    @Override
    public ExecuteScriptCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        XmlReader.Element textElement = element.getChild(0);
        String script = textElement != null ? textElement.getText() : "";
        boolean isFile = element.getBoolean("isfile", false);
        String language = element.getAttribute("language", ConversationController.defaultScriptingLanguage());
        return new ExecuteScriptCommand(script, isFile, language);
    }
}