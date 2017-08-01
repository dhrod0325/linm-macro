package lm.macro.gui.component.view.serverInfo;

import lm.macro.auto.common.LmCommon;
import lm.macro.gui.GuiMain;
import lm.macro.gui.component.view.GuiTabView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.IOException;

public class GuiView {
    private GuiTabView guiTabView;

    private StyledText chatting;

    public GuiView(GuiTabView guiTabView) {
        this.guiTabView = guiTabView;
    }

    public void init() {
        TabItem 로그탭 = new TabItem(guiTabView.getTabFolder(), SWT.NONE);
        로그탭.setText("로그");
        로그탭.setControl(로그탭_뷰());

        TabItem 매크로탭 = new TabItem(guiTabView.getTabFolder(), SWT.NONE);
        매크로탭.setText("매크로");
        매크로탭.setControl(매크로탭_뷰());
    }

    private Control 로그탭_뷰() {
        Composite composite = new Composite(guiTabView.getTabFolder(), SWT.NONE);
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;

        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        chatting = new StyledText(composite, SWT.V_SCROLL);
        chatting.setEditable(false);
        chatting.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
        chatting.addListener(SWT.Modify, e -> chatting.setTopIndex(chatting.getLineCount() - 1));
        return composite;
    }

    private Composite 매크로탭_뷰() {
        Composite composite = new Composite(guiTabView.getTabFolder(), SWT.NONE);

        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.verticalSpacing = 10;
        gridLayout.horizontalSpacing = 10;
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Label label = new Label(composite, SWT.NONE);
        label.setText(String.format("버전 %s\n\n이용해주셔 감사합니다.", LmCommon.VERSION));

        Button 크롬실행버튼 = new Button(composite, SWT.NONE);
        크롬실행버튼.setText("관리자 화면 열기");
        크롬실행버튼.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);

                try {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome http://localhost:8080"});
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Button 업데이트버튼 = new Button(composite, SWT.NONE);
        업데이트버튼.setText("업데이트");
        업데이트버튼.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);

                MessageBox messageBox = new MessageBox(GuiMain.getInstance().getShell(), SWT.ICON_QUESTION | SWT.YES);
                messageBox.setMessage("macro-update 폴더에 run 을 실행해주세요.");
                messageBox.setText("알림");
                messageBox.open();
            }
        });

        Link 홈페이지 = new Link(composite, SWT.NONE);
        홈페이지.setText("홈페이지 : <a>http://linm.ideapeople.co.kr</a>");
        홈페이지.addListener(SWT.Selection, event -> {
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + event.text});
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        return composite;
    }

    public StyledText getChatting() {
        return chatting;
    }

    public GuiTabView getGuiTabView() {
        return guiTabView;
    }
}