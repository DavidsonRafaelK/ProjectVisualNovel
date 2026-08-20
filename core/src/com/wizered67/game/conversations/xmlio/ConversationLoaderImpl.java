package com.wizered67.game.conversations.xmlio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.wizered67.game.conversations.Conversation;
import com.wizered67.game.conversations.commands.ConversationCommand;
import com.wizered67.game.conversations.commands.factories.AssignCommandFactory;
import com.wizered67.game.conversations.commands.factories.BackgroundSetFactory;
import com.wizered67.game.conversations.commands.factories.CameraCommandFactory;
import com.wizered67.game.conversations.commands.factories.ChangeBranchCommandFactory;
import com.wizered67.game.conversations.commands.factories.ChangeConversationCommandFactory;
import com.wizered67.game.conversations.commands.factories.CharacterAnimationCommandFactory;
import com.wizered67.game.conversations.commands.factories.CharacterNameCommandFactory;
import com.wizered67.game.conversations.commands.factories.CharacterVisibleCommandFactory;
import com.wizered67.game.conversations.commands.factories.ClearSceneCommandFactory;
import com.wizered67.game.conversations.commands.factories.CommandSequenceFactory;
import com.wizered67.game.conversations.commands.factories.ConversationCommandFactory;
import com.wizered67.game.conversations.commands.factories.DebugCommandFactory;
import com.wizered67.game.conversations.commands.factories.DelayCommandFactory;
import com.wizered67.game.conversations.commands.factories.EntityPositionCommandFactory;
import com.wizered67.game.conversations.commands.factories.EntityScaleCommandFactory;
import com.wizered67.game.conversations.commands.factories.ExecuteScriptCommandFactory;
import com.wizered67.game.conversations.commands.factories.ExitCommandFactory;
import com.wizered67.game.conversations.commands.factories.LoadUnloadCommandFactory;
import com.wizered67.game.conversations.commands.factories.MessageCommandFactory;
import com.wizered67.game.conversations.commands.factories.ModifyImageCommandCreatorFactory;
import com.wizered67.game.conversations.commands.factories.PlayMusicCommandFactory;
import com.wizered67.game.conversations.commands.factories.PlaySoundCommandFactory;
import com.wizered67.game.conversations.commands.factories.ScreenFadeCommandFactory;
import com.wizered67.game.conversations.commands.factories.ScreenShakeCommandFactory;
import com.wizered67.game.conversations.commands.factories.SetSceneCommandFactory;
import com.wizered67.game.conversations.commands.factories.ShowChoicesCommandFactory;
import com.wizered67.game.conversations.commands.factories.VariableConditionCommandFactory;
import com.wizered67.game.conversations.commands.factories.VariableInitializeCommandFactory;
import com.wizered67.game.conversations.commands.factories.VariableWhileCommandFactory;
import com.wizered67.game.conversations.commands.factories.WaitForLoadingCommandFactory;
import com.wizered67.game.conversations.commands.factories.WaitUntilVariableCommandFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A class used to load and parse XML files into Conversations with
 * the commands in the XML file.
 * @author Adam Victor
 */
public class ConversationLoaderImpl implements ConversationLoader {
    /** Used to parse Xml. The MixedXmlReader separates text into its own elements to maintain order. */
    private MixedXmlReader xml = new MixedXmlReader();
    /** Mapping of xml element names to corresponding ConversationCommand class. */
    private Map<String, ConversationCommandFactory> commandTagToFactory = new HashMap<>();

    public ConversationLoaderImpl() {
        commandTagToFactory.put("assign", AssignCommandFactory.INSTANCE);
        commandTagToFactory.put("bg", BackgroundSetFactory.INSTANCE);
        commandTagToFactory.put("camera", CameraCommandFactory.INSTANCE);
        commandTagToFactory.put("changebranch", ChangeBranchCommandFactory.INSTANCE);
        commandTagToFactory.put("changeconv", ChangeConversationCommandFactory.INSTANCE);
        commandTagToFactory.put("animation", CharacterAnimationCommandFactory.INSTANCE);
        commandTagToFactory.put("name", CharacterNameCommandFactory.INSTANCE);
        commandTagToFactory.put("position", EntityPositionCommandFactory.INSTANCE);
        commandTagToFactory.put("visible", CharacterVisibleCommandFactory.INSTANCE);
        commandTagToFactory.put("clearScene", ClearSceneCommandFactory.INSTANCE);
        commandTagToFactory.put("sequence", CommandSequenceFactory.INSTANCE);
        commandTagToFactory.put("debug", DebugCommandFactory.INSTANCE);
        commandTagToFactory.put("delay", DelayCommandFactory.INSTANCE);
        commandTagToFactory.put("script", ExecuteScriptCommandFactory.INSTANCE);
        commandTagToFactory.put("exit", ExitCommandFactory.INSTANCE);
        commandTagToFactory.put("load", LoadUnloadCommandFactory.INSTANCE);
        commandTagToFactory.put("unload", LoadUnloadCommandFactory.INSTANCE);
        commandTagToFactory.put("loadGroup", LoadUnloadCommandFactory.INSTANCE);
        commandTagToFactory.put("unloadGroup", LoadUnloadCommandFactory.INSTANCE);
        commandTagToFactory.put("text", MessageCommandFactory.INSTANCE);
        commandTagToFactory.put("music", PlayMusicCommandFactory.INSTANCE);
        commandTagToFactory.put("resumemusic", PlayMusicCommandFactory.INSTANCE);
        commandTagToFactory.put("pausemusic", PlayMusicCommandFactory.INSTANCE);
        commandTagToFactory.put("screenshake", ScreenShakeCommandFactory.INSTANCE);
        commandTagToFactory.put("scale", EntityScaleCommandFactory.INSTANCE);
        commandTagToFactory.put("sound", PlaySoundCommandFactory.INSTANCE);
        commandTagToFactory.put("fade", ScreenFadeCommandFactory.INSTANCE);
        commandTagToFactory.put("setScene", SetSceneCommandFactory.INSTANCE);
        commandTagToFactory.put("choices", ShowChoicesCommandFactory.INSTANCE);
        commandTagToFactory.put("if", VariableConditionCommandFactory.INSTANCE);
        commandTagToFactory.put("image", ModifyImageCommandCreatorFactory.INSTANCE);
        commandTagToFactory.put("init", VariableInitializeCommandFactory.INSTANCE);
        commandTagToFactory.put("waitForLoading", WaitForLoadingCommandFactory.INSTANCE);
        commandTagToFactory.put("waitUntil", WaitUntilVariableCommandFactory.INSTANCE);
        commandTagToFactory.put("while", VariableWhileCommandFactory.INSTANCE);
    }

    /** Returns the Conversation created by parsing the XML file
     * with the name NAME. */
    @Override
    public Conversation loadConversation(String name) throws ConversationParsingException {
        FileHandle file = Gdx.files.internal("Conversations/" + name);
        return loadConversation(file);
    }

    /** Returns the Conversation created by parsing the XML file with FileHandle FILE. */
    @Override
    public Conversation loadConversation(FileHandle file) throws ConversationParsingException {
        if (file == null) {
            return null;
        }
        try {
            Conversation conversation = new Conversation(file.name());
            Element root = xml.parse(file);
            for (int i = 0; i < root.getChildCount(); i += 1) {
                Element c = root.getChild(i);
                String n = c.getName();
                if (n.equals("branch")) {
                    addBranch(conversation, c);
                }
            }
            return conversation;
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't load Conversation " + file.name(), e);
        }
    }

    /** Adds the branch contained in the XML Element ROOT to CONVERSATION by
     * parsing each command's XML tag.
     */
    private void addBranch(Conversation conversation, Element root) throws ConversationParsingException {
        String name = root.getAttribute("name");
        LinkedList<ConversationCommand> commands = new LinkedList<ConversationCommand>();
        for (int i = 0; i < root.getChildCount(); i += 1) {
            commands.add(getCommand(root.getChild(i)));
        }
        conversation.addBranch(name, commands);
    }

    /** Adds a new mapping between element name NAME
     * and a factory to create that command. */
    @Override
    public void setCommandTagMapping(String name, ConversationCommandFactory type) {
        commandTagToFactory.put(name, type);
    }

    /** Creates a ConversationCommand for an XML element using the specified factory.
     * Throws a ConversationParsingException if no factory has been set. */
    @Override
    public ConversationCommand getCommand(Element root) throws ConversationParsingException {
        String name = root.getName();
        ConversationCommandFactory factory = commandTagToFactory.get(name);
        if (factory == null) {
            throw new ConversationParsingException("No factory available to create command with tag: " + name);
        }
        return factory.makeCommand(this, root);
    }
}
