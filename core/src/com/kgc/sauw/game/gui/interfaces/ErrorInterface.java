package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.AbstractTextView;
import com.kgc.sauw.core.gui.elements.MultilineTextView;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.utils.Application;

@RegistryMetadata(package_ = "sauw", id = "error_interface")
public class ErrorInterface extends Interface {
    private String msg;
    private final MultilineTextView errorView;

    public void setErrorMsg(String errorMsg) {
        msg = errorMsg;
        errorView.setText(errorMsg);
    }

    public ErrorInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/ErrorInterface.xml"), this);
        errorView = (MultilineTextView) getElement("text.error_log");
        closeInterfaceButton.addEventListener(() -> Gdx.app.exit());

        ((AbstractTextView) getElement("button.exit_game")).setDefaultText();
        ((AbstractTextView) getElement("button.copy_log")).setDefaultText();
        ((AbstractTextView) getElement("button.github_issues")).setDefaultText();
        ((AbstractTextView) getElement("button.telegram_issues")).setDefaultText();

        getElement("button.exit_game").addEventListener(() -> Gdx.app.exit());
        getElement("button.copy_log").addEventListener(() -> Gdx.app.getClipboard().setContents(msg));
        getElement("button.github_issues").addEventListener(() -> Application.openURI("https://github.com/KirboGames/S.A.U.W.-/issues/new/choose"));
        getElement("button.telegram_issues").addEventListener(() -> Application.openURI("https://t.me/sauw_issues"));
    }
}
