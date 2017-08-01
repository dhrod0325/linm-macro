package lm.macro.auto.utils;

import lm.macro.auto.utils.cmd.Netstat;
import lm.macro.auto.utils.cmd.Process;
import lm.macro.auto.utils.cmd.ProcessUtils;
import lm.macro.auto.data.model.process.LmAdbProcess;
import org.apache.commons.beanutils.BeanUtils;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;

import java.util.ArrayList;
import java.util.List;

public class LmAdbProcessUtils {
    public static List<JadbDevice> devices() throws Exception {
        return new JadbConnection().getDevices();
    }

    public static LmAdbProcess getAdbProcessByHostPort(int port) {
        List<LmAdbProcess> k = getAdbProcessList();

        for (LmAdbProcess p : k) {
            if (p.getHostPort() == port) {
                return p;
            }
        }

        return null;
    }

    public static List<LmAdbProcess> getAdbProcessList() {
        List<LmAdbProcess> result = new ArrayList<>();
        List<Process> processList = ProcessUtils.processList(
                "/c:\"dnplayer.exe\" /c:\"Nox.exe\" /c:\"MEmu.exe\""
        );

        for (Process process : processList) {
            try {
                LmAdbProcess adbProcess = new LmAdbProcess();
                BeanUtils.copyProperties(adbProcess, process);
                result.add(adbProcess);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static Netstat getAdbHandleProcessNetstatByUUID(String uuid, int port) throws Exception {
        List<Netstat> netstatResults = ProcessUtils.getNetstatListByPort(port);

        for (Netstat stat : netstatResults) {
            Process process = ProcessUtils.taskByPid(String.valueOf(stat.getPid()));

            if (process != null
                    && process.getCommandLine() != null
                    && process.getCommandLine().contains(uuid)) {
                return stat;
            }
        }

        return null;
    }

    public static Process getAdbHandleProcessByUUID(String uuid, int port) throws Exception {
        List<Netstat> netstatResults = ProcessUtils.getNetstatListByPort(port);

        for (Netstat stat : netstatResults) {
            Process process = ProcessUtils.taskByPid(String.valueOf(stat.getPid()));

            if (process != null
                    && process.getCommandLine() != null
                    && process.getCommandLine().contains(uuid)) {
                return process;
            }
        }

        return null;
    }
}
