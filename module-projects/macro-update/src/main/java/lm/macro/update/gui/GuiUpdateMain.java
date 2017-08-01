package lm.macro.update.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lm.macro.update.LmDownloader;
import lm.macro.update.LmVersionInfo;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GuiUpdateMain {
    private static final String SERVER_URL = "http://linm.ideapeople.co.kr";

    private static final String UPDATE_DIR = "macro-run";

    private static final String UPDATE_ZIP = "update.zip";

    private static boolean downloadComplete = false;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private Display display = Display.getDefault();

    private Shell shell;

    private StyledText logText;

    public void start() throws IOException {
        shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        shell.setText("LM-Macro Update");

        GridLayout gridLayout = new GridLayout(2, true);
        shell.setLayout(gridLayout);
        shell.setSize(WIDTH, HEIGHT);

        shell.addListener(SWT.Close, event -> {
            System.exit(0);
        });

        createMenu();

        Composite buttonComposite = new Composite(shell, SWT.BORDER);
        buttonComposite.setLayout(new GridLayout(1, false));
        buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        List<LmVersionInfo> versionList = getVersionList();
        for (LmVersionInfo version : versionList) {
            Button button = new Button(buttonComposite, SWT.NONE);
            button.setText("버전 : " + version.getVersion() + " 업데이트");

            button.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    super.widgetSelected(e);
                    MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
                    messageBox.setMessage(String.format("%s 버전으로 업데이트 하시겠습니까?", version.getVersion()));
                    messageBox.setText("버전 업데이트");
                    int response = messageBox.open();

                    if (response == SWT.YES) {
                        new Thread(() -> {
                            try {
                                updateMacro(version);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }).start();
                    }
                }
            });
        }

        Composite logComposite = new Composite(shell, SWT.BORDER);
        logComposite.setLayout(new GridLayout(1, false));
        logComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        logText = new StyledText(logComposite, SWT.NONE);
        logText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        logText.setEditable(false);

        shellOpen();
    }

    private void append(String str) {
        display.asyncExec(() -> logText.append(str + "\r\n"));
    }

    private void updateMacro(LmVersionInfo version) throws Exception {
        LmDownloader downloader = new LmDownloader();
        String downUrl = String.format("%s", SERVER_URL) + version.getLocation();

        System.out.println(downUrl);

        File updateFile = new File(UPDATE_ZIP);

        if (updateFile.exists()) {
            updateFile.delete();
        }

        append("다운로드를 시작합니다. 도중에 종료하지 마세요...\r\n");

        new Thread(() -> {
            while (!downloadComplete) {
                if (updateFile.length() > 0) {
                    File updateFile2 = new File(UPDATE_ZIP);
                    double percent = (double) updateFile2.length() / (double) version.getSize() * 100;
                    String p = String.format("%.2f", percent).trim();
                    append("진행 : " + p + "%");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        downloader.download(new URL(downUrl), updateFile);

        downloadComplete = true;

        append("다운로드 완료...");

        append("압축해제 진행...");

        String macroDir = macroDir();

        deleteAllFiles(macroDir + "/" + UPDATE_DIR);

        unzip(updateFile, new File(macroDir, UPDATE_DIR));
        updateFile.delete();
        append("업데이트 완료.");

        display.asyncExec(() -> {
            MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
            messageBox.setMessage("업데이트가 완료되었습니다. 감사합니다");
            messageBox.setText("알림");

            int response = messageBox.open();

            if (response == SWT.OK) {
                Runtime.getRuntime().exit(0);
            }
        });
    }

    private void createMenu() {
        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);

        MenuItem systemMenu = new MenuItem(menuBar, SWT.CASCADE);
        systemMenu.setText("메뉴");

        Menu systemMenuContainer = new Menu(systemMenu);
        systemMenu.setMenu(systemMenuContainer);

        MenuItem exitMenu = new MenuItem(systemMenuContainer, SWT.NONE);
        exitMenu.setText("종료");
        exitMenu.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                System.exit(0);
            }
        });
    }

    private void shellOpen() {
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

    private static String macroDir() throws IOException {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();

        String result = path + "/..";

        System.out.println(result);

        return result;
    }

    public static void deleteAllFiles(String path) {
        File file = new File(path);
        File[] tempFile = file.listFiles();

        if (tempFile != null && tempFile.length > 0) {
            for (File aTempFile : tempFile) {

                if (aTempFile.isFile()) {
                    aTempFile.delete();
                } else {
                    deleteAllFiles(aTempFile.getPath());
                }
                aTempFile.delete();

            }

            file.delete();
        }
    }

    private static void deleteDirectoryInner(File path) {
        File[] files = path.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectoryInner(file);
            } else {
                file.delete();
            }
        }
    }

    private static void unzip(File file, File dest) {
        try {
            ZipFile zipFile = new ZipFile(file);
            zipFile.extractAll(dest.getAbsolutePath());
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    private static List<LmVersionInfo> getVersionList() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(String.format("%s/linm/download/update/versions.php", SERVER_URL));
        CloseableHttpResponse response = client.execute(get);
        String body = IOUtils.toString(response.getEntity().getContent(), "utf-8");

        return new ObjectMapper().readValue(body, new TypeReference<List<LmVersionInfo>>() {
        });
    }
}
