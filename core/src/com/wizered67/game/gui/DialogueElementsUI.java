package com.wizered67.game.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;
import com.wizered67.game.Constants;

/**
 * Creates and contains elements necessary for the main dialogue UI, including textboxes and choice buttons.
 * @author Adam Victor
 */
public class DialogueElementsUI {
    private GUIManager guiManager;
    private Table mainTable;
    private Table buttonTable;
    private TypingLabel textboxLabel;
    private Label speakerLabel;
    private TextButton[] choiceButtons;

    public DialogueElementsUI(GUIManager manager, Skin skin) {
        guiManager = manager;
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setDebug(Constants.DEBUG);
        buttonTable = new Table();
        buttonTable.setDebug(Constants.DEBUG);
        mainTable.add(buttonTable).expandX().fillX().padBottom(Value.percentHeight(0.1f, mainTable));//.height(Value.percentHeight(0.5f, mainTable));
        mainTable.row();
        choiceButtons = new TextButton[4];
        for (int i = 0; i < choiceButtons.length; i += 1) {
            TextButton tb = new TextButton("", skin);
            tb.setUserObject(i);
            tb.addListener(new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                    if (Constants.DEBUG) {
                        System.out.println("Clicked button " + actor.getUserObject());
                    }
                    guiManager.conversationController().processChoice((Integer) actor.getUserObject());
                    event.cancel();
                    ((Button) actor).setProgrammaticChangeEvents(false);
                    ((Button) actor).setChecked(false);
                    ((Button) actor).setProgrammaticChangeEvents(true);
                }
            });
            tb.setVisible(false);
            buttonTable.add(tb).growX().padLeft(20).padRight(20).padBottom(20).height(Value.percentHeight(0.08f, mainTable));
            if (i != choiceButtons.length - 1) {
                buttonTable.row();
            }
            choiceButtons[i] = tb;
        }

        textboxLabel = new TypingLabel("", skin);
        textboxLabel.setAlignment(Align.topLeft);
        textboxLabel.setWrap(true);
        textboxLabel.toFront();

        speakerLabel = new Label("", skin, "speakerStyle");
        speakerLabel.toBack();
        speakerLabel.setAlignment(Align.center);

        mainTable.add(speakerLabel).width(Value.percentWidth(0.2f, textboxLabel)).padLeft(20).left();
        mainTable.row();
        mainTable.bottom().add(textboxLabel).
                expandX().fillX().height(Value.percentHeight(0.24f, mainTable))
                .padLeft(20).padRight(20).padBottom(20).colspan(10);

    }

    public Table getMainTable() {
        return mainTable;
    }

    public void act(float delta) {

    }

    public TypingLabel getTextboxLabel() {
        return textboxLabel;
    }

    public Label getSpeakerLabel() {
        return speakerLabel;
    }

    public TextButton[] getChoiceButtons() {
        return choiceButtons;
    }

    public void resize(int newWidth, int newHeight) {
        //necessary to call this directly. It seems resizing the screen resizes the main table, but doesn't
        //resize the buttonTable. The problem is that the buttons don't recalculate their size even though the thing they're
        //based on has been changed. Calling invalidate on the button table automatically forces all button sizes to be recomputed.
        buttonTable.invalidate();
    }

}
