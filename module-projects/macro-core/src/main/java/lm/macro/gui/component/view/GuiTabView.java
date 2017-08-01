package lm.macro.gui.component.view;

import lm.macro.gui.GuiMain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;

public class GuiTabView {
    private GuiMain guiMain;

    private TabFolder tabFolder;

    private Composite composite;

    public GuiTabView(GuiMain guiMain) {
        this.guiMain = guiMain;
    }

    public void init() {
        initComposite();

        initTab();
    }

    private void initComposite() {
        composite = new Composite(guiMain.getShell(), SWT.NONE);

        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;

        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    }

    private void initTab() {
        tabFolder = new TabFolder(composite, SWT.NONE);
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    }

    public TabFolder getTabFolder() {
        return tabFolder;
    }
}
