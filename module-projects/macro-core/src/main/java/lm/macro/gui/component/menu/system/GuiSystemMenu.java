package lm.macro.gui.component.menu.system;

import lm.macro.gui.GuiMain;
import lm.macro.gui.GuiUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class GuiSystemMenu {
    private GuiMain guiMain;

    public GuiSystemMenu(GuiMain guiMain) {
        this.guiMain = guiMain;
    }

    private MenuItem systemMenu;
    private Menu systemMenuContainer;

    public void create() {
        initSystemMenu();

        initSystemMenuContainer();

        initServerStartMenu();
    }

    private void initSystemMenu() {
        systemMenu = new MenuItem(guiMain.getShell().getMenuBar(), SWT.CASCADE);
        systemMenu.setText("시스템");
    }

    private void initSystemMenuContainer() {
        systemMenuContainer = new Menu(systemMenu);
        systemMenu.setMenu(systemMenuContainer);
    }

    private void initServerStartMenu() {
//        MenuItem 매크로시작 = new MenuItem(systemMenuContainer, SWT.NONE);
//        매크로시작.setText("시작");
//        매크로시작.addSelectionListener(new SelectionAdapter() {
//            @Override
//            public void widgetSelected(SelectionEvent e) {
//                super.widgetSelected(e);
//
//            }
//        });

        MenuItem 매크로종료 = new MenuItem(systemMenuContainer, SWT.NONE);
        매크로종료.setText("종료");
        매크로종료.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                guiMain.getDisplay().asyncExec(GuiUtils::shutDown);
            }
        });
    }
}
