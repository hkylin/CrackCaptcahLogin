package com.fuping;


import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.DialogParams;
import com.teamdev.jxbrowser.chromium.PromptDialogParams;
import com.teamdev.jxbrowser.chromium.javafx.DefaultDialogHandler;
import javafx.scene.Node;

public class MyDialogHandler extends DefaultDialogHandler
{
    public MyDialogHandler(Node paramNode)
    {
        super(paramNode);
    }

    public void onAlert(DialogParams paramDialogParams)
    {
    }

    public CloseStatus onConfirmation(DialogParams paramDialogParams)
    {
        return CloseStatus.CANCEL;
    }

    public CloseStatus onPrompt(PromptDialogParams paramPromptDialogParams)
    {
        return CloseStatus.CANCEL;
    }
}