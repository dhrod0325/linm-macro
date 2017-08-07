package lm.macro.auto.data.model.process;

import lm.macro.auto.data.model.netstat.LmNetstatProcessHolder;
import lm.macro.auto.utils.LmAdbProcessUtils;
import lm.macro.auto.utils.cmd.Netstat;
import lm.macro.auto.utils.cmd.Process;
import lm.macro.auto.utils.cmd.ProcessUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LmAdbProcess extends Process {
    @Transient
    private boolean isInitialize = false;

    @Id
    private String uuid;

    private String forwardName;

    private int hostPort;

    private int guestPort;

    private String emulatorName;

    public LmAdbProcess() {
    }

    public String getForwardName() {
        initializingWithXml();

        return forwardName;
    }

    public void setForwardName(String forwardName) {
        this.forwardName = forwardName;
    }

    public int getHostPort() {
        initializingWithXml();

        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public int getGuestPort() {
        initializingWithXml();

        return guestPort;
    }

    public void setGuestPort(int guestPort) {
        this.guestPort = guestPort;
    }

    public String getEmulatorName() {
        initializingWithXml();

        return emulatorName;
    }

    public void setEmulatorName(String emulatorName) {
        this.emulatorName = emulatorName;
    }

    public String getUuid() {
        initializingWithXml();
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void initializingWithXml() {
        if (isInitialize) {
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            XPathFactory xpathFactory = XPathFactory.newInstance();

            if (executablePath == null)
                return;

            File dir = new File(executablePath).getParentFile();

            Document doc = null;

            Node forwardingNode = null;
            Node machineNode;

            XPathExpression forwardingExpr = xpathFactory.newXPath().compile("//Forwarding");
            XPathExpression machineExpr = xpathFactory.newXPath().compile("//Machine");

            if (name == null)
                return;

            if (name.contains("dnplayer")) {
                String index = commandLine.substring(commandLine.indexOf("index=") + 6, commandLine.length());
                String vmsPath = dir.getAbsolutePath() + "/vms/leidian" + index;
                String fileName = vmsPath + "/leidian.vbox";
                doc = builder.parse(new File(fileName));
                setEmulatorName("MOMO");

                NodeList nodeList = (NodeList) forwardingExpr.evaluate(doc, XPathConstants.NODESET);

                forwardingNode = nodeList.item(0);

            } else if (name.contains("Nox")) {
                String index = commandLine.substring(commandLine.indexOf(":Nox_") + 5, commandLine.length());
                String vmsPath = dir.getAbsolutePath() + "/BignoxVMS/";
                String v_title = "";
                if ("0".equals(index)) {
                    v_title = "nox";
                } else {
                    v_title = "Nox_" + index;
                }
                vmsPath += v_title;
                String fileName = vmsPath + "/" + v_title + ".vbox";

                doc = builder.parse(new File(fileName));
                setEmulatorName("Nox");
                NodeList nodeList = (NodeList) forwardingExpr.evaluate(doc, XPathConstants.NODESET);
                forwardingNode = nodeList.item(2);
            } else if (name.contains("MEmu")) {
                setEmulatorName("MEmu");
            }

            if (doc != null && forwardingNode != null) {
                machineNode = (Node) machineExpr.evaluate(doc, XPathConstants.NODE);

                setHostPort(Integer.valueOf(forwardingNode.getAttributes().getNamedItem("hostport").getTextContent()));
                setGuestPort(Integer.valueOf(forwardingNode.getAttributes().getNamedItem("guestport").getTextContent()));
                setForwardName(forwardingNode.getAttributes().getNamedItem("name").getTextContent());


                String uuid = machineNode.getAttributes().getNamedItem("uuid").getTextContent();
                setUuid(uuid.replace("{", "").replace("}", ""));
            }

            isInitialize = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LmNetstatProcessHolder findNetstatProcess(int port) {
        StopWatch s = StopWatch.createStarted();

        LmNetstatProcessHolder netstatProcessHolder = new LmNetstatProcessHolder();

        try {
            initializingWithXml();

            List<Netstat> netstatList = new ArrayList<>();

            Process process = LmAdbProcessUtils.getAdbHandleProcessByUUID(getUuid(), port);
            netstatProcessHolder.setProcess(process);

            if (process != null) {
                netstatList.addAll(ProcessUtils.getNetstatByPidAndPort(process.getPid(), port));
            }

            netstatProcessHolder.setNetstatList(netstatList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return netstatProcessHolder;
    }


    @Override
    public String toString() {
        initializingWithXml();

        return "LmAdbProcess{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", commandLine='" + commandLine + '\'' +
                ", parentProcessId='" + parentProcessId + '\'' +
                ", executablePath='" + executablePath + '\'' +
                ", forwardName='" + forwardName + '\'' +
                ", hostPort=" + hostPort +
                ", guestPort=" + guestPort +
                ", emulatorName='" + emulatorName + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
