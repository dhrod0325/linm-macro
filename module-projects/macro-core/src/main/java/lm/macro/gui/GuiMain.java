package lm.macro.gui;

import lm.macro.gui.component.menu.system.GuiSystemMenu;
import lm.macro.gui.component.view.GuiTabView;
import lm.macro.gui.component.view.serverInfo.GuiView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class GuiMain {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    private GuiSystemMenu systemMenu = new GuiSystemMenu(this);

    private GuiTabView guiTabView = new GuiTabView(this);

    private GuiView guiView = new GuiView(guiTabView);

    private Display display = Display.getDefault();

    private Shell shell;

    public void start() {
        shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        shell.setSize(WIDTH, HEIGHT);
        shell.setText("LM-Macro");

        GridLayout layout = new GridLayout(2, false);
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;

        shell.setLayout(layout);
        shell.addListener(SWT.Close, event -> {
            GuiUtils.shutDown();
        });

        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);

        systemMenu.create();

        initComponents();

        display.asyncExec(() -> {
            guiView.init();
            GuiUtils.start(() -> {
                display.asyncExec(() -> guiTabView.getTabFolder().setSelection(1));
            });
        });

        shell.open();
        shell.layout();

        while (!shell.isDisposed()) {
            try {
                if (!shell.getDisplay().readAndDispatch())
                    shell.getDisplay().sleep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initComponents() {
        guiTabView.init();
    }

    public Display getDisplay() {
        return display;
    }

    public Shell getShell() {
        return shell;
    }

    public GuiView getGuiView() {
        return guiView;
    }

    private static GuiMain guiMain = new GuiMain();

    public static GuiMain getInstance() {
        return guiMain;
    }

    public GuiTabView getGuiTabView() {
        return guiTabView;
    }
}

