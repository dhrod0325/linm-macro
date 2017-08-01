package lm.macro.auto.data.model.netstat;

import lm.macro.auto.utils.cmd.Netstat;
import lm.macro.auto.utils.cmd.Process;

import java.util.List;

/**
 * Created by dhrod0325 on 2017-07-09.
 */
public class LmNetstatProcessHolder {
    private Process process;
    private List<Netstat> netstatList;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public List<Netstat> getNetstatList() {
        return netstatList;
    }

    public void setNetstatList(List<Netstat> netstatList) {
        this.netstatList = netstatList;
    }
}
