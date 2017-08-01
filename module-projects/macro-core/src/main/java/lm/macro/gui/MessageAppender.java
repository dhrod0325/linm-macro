package lm.macro.gui;


import ch.qos.logback.core.OutputStreamAppender;
import org.eclipse.swt.custom.StyledText;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class MessageAppender extends OutputStreamAppender {
    @Override
    public void start() {
        setOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }

            @Override
            public void write(byte[] b) throws IOException {
                GuiMain.getInstance().getDisplay().asyncExec(() -> {
                    StyledText t = GuiMain.getInstance().getGuiView().getChatting();
                    if (t != null) {
                        try {
                            t.append(new String(b, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
            }
        });

        super.start();
    }
}