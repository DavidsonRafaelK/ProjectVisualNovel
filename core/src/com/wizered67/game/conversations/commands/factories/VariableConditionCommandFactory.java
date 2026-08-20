package com.wizered67.game.conversations.commands.factories;

import com.badlogic.gdx.utils.XmlReader;
import com.wizered67.game.GameManager;
import com.wizered67.game.conversations.ConversationController;
import com.wizered67.game.conversations.commands.ConversationCommand;
import com.wizered67.game.conversations.commands.impl.scripting.VariableConditionCommand;
import com.wizered67.game.conversations.xmlio.ConversationLoader;
import com.wizered67.game.conversations.xmlio.ConversationParsingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating a VariableConditionCommand from an XML element.
 *
 * @author Adam Victor
 */
public enum VariableConditionCommandFactory implements ConversationCommandFactory<VariableConditionCommand> {

    INSTANCE;

    @Override
    public VariableConditionCommand makeCommand(ConversationLoader loader, XmlReader.Element element) {
        String language = element.getAttribute("language", ConversationController.defaultScriptingLanguage());
        boolean isFile = element.getBoolean("isfile", false);
        List<ConversationCommand> commands = new ArrayList<ConversationCommand>();
        List<ConversationCommand> elseCommands = new ArrayList<>();
        String script = element.getAttribute("cond");
        for (int i = 0; i < element.getChildCount(); i += 1) {
            XmlReader.Element c = element.getChild(i);
            if (i == element.getChildCount() - 1 && c.getName().equals("else")) {
                for (int j = 0; j < c.getChildCount(); j += 1) {
                    XmlReader.Element elseC = c.getChild(j);
                    try {
                        elseCommands.add(loader.getCommand(elseC));
                    } catch (ConversationParsingException e) {
                        GameManager.error("Failed to parse else-branch command in variable condition.", e);
                    }
                }
            } else {
                try {
                    ConversationCommand command = loader.getCommand(c);
                    commands.add(command);
                } catch (ConversationParsingException e) {
                    GameManager.error("Failed to parse command in variable condition.", e);
                }
            }
        }
        return new VariableConditionCommand(script, isFile, language, commands, elseCommands);
    }
}