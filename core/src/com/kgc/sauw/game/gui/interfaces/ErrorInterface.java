package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.AbstractTextView;
import com.kgc.sauw.core.gui.elements.TextView;
import com.kgc.sauw.core.registry.RegistryMetadata;

@RegistryMetadata(package_ = "sauw", id = "error_interface")
public class ErrorInterface extends Interface {
    private String msg;
    private final TextView[] errorView = new TextView[12];

    public void setErrorMsg(String errorMsg) {
        msg = errorMsg;
        String[] error = errorMsg.split("\n");
        for (int i = 0; i < 12; i++) {
            if (i < error.length)
                errorView[i].setText(error[i]);
        }
    }

    public ErrorInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/ErrorInterface.xml"), this);
        for (int i = 0; i < 12; i++) {
            errorView[i] = (TextView) getElement("text.error_log_" + i);
            errorView[i].setTextAlign(Align.left);
        }
        closeInterfaceButton.addEventListener(() -> Gdx.app.exit());

        ((AbstractTextView) getElement("button.exit_game")).setDefaultText();
        ((AbstractTextView) getElement("button.copy_log")).setDefaultText();
        ((AbstractTextView) getElement("button.github_issues")).setDefaultText();
        ((AbstractTextView) getElement("button.telegram_issues")).setDefaultText();

        getElement("button.exit_game").addEventListener(() -> Gdx.app.exit());
        getElement("button.copy_log").addEventListener(() -> Gdx.app.getClipboard().setContents(msg));
        getElement("button.github_issues").addEventListener(() -> Gdx.net.openURI("https://github.com/KirboGames/S.A.U.W.-/issues/new/choose"));
        getElement("button.telegram_issues").addEventListener(() -> Gdx.net.openURI("https://t.me/sauw_issues"));
    }
}
