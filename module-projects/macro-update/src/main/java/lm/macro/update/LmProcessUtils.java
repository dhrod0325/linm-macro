package lm.macro.update;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LmProcessUtils {
    public static void killByPid(int pid) throws Exception {
        execCommand(String.format("taskkill /f /pid %d", pid));
    }


    public static List<LmProcess> generate(List<String[]> taskList) throws Exception {
        List<LmProcess> results = new ArrayList<>();

        try {
            for (String[] strings : taskList) {
                if (strings[0].isEmpty())
                    continue;

                LmProcess process = new LmProcess();
                process.setName(strings[0]);
                process.setPid(Integer.valueOf(strings[1]));
                process.setTitle(strings[strings.length - 1]);

                if (process.getPid() != 0) {
                    String[] values = wmicInfo(process.getPid());

                    process.setCommandLine(values[1]);
                    process.setParentProcessId(values[3]);
                    process.setExecutablePath(values[2]);
                }

                results.add(process);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public static List<LmProcess> processList(String find) {
        List<LmProcess> result = new ArrayList<>();
        try {
            List<String[]> taskList = taskListByProcessNameInfo(find);
            result.addAll(generate(taskList));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String[]> taskListByProcessNameInfo(String find) throws Exception {
        List<String[]> results = new ArrayList<>();
        String command = String.format("tasklist /fo csv | findstr %s", find);
        String result = IOUtils.toString(execCommand(command), "EUC-KR");
        result = result.replaceAll("\"", "");
        String[] strings = result.split("\r\n");

        for (String string : strings) {
            if (string.isEmpty()) {
                continue;
            }
            result = taskInfoFromCsv(string);
            results.add(result.trim().split(","));
        }

        return results;
    }

    public static String[] wmicInfo(int pid) throws Exception {
        String command2 = String.format("wmic process where processId=%d get commandline,parentProcessId,ExecutablePath /format:csv", pid);
        String result2 = IOUtils.toString(execCommand(command2), "EUC-KR");
        String row = result2.trim().split("\r\n")[1];

        return row.replaceAll("\"", "").replaceAll("\\|", "").split(",");
    }

    public static LmProcess taskByPid(String pid) throws Exception {
        List<String[]> results = new ArrayList<>();

        String command = String.format("tasklist /v /fi \"pid eq %s\" /fo csv", pid);
        String result = IOUtils.toString(execCommand(command), "EUC-KR");
        result = result.replaceAll("\"", "");
        String[] strings = result.split("\r\n");

        for (String string : strings) {
            if (string.isEmpty()) {
                continue;
            }

            result = taskInfoFromCsv(string);
            results.add(result.trim().split(","));
        }

        List<LmProcess> processes = generate(results);

        return processes.isEmpty() ? null : processes.get(0);
    }

    public static String taskInfoFromCsv(String string) throws Exception {
        String[] v = string.split(",");
        String command2 = String.format("tasklist /v /fi \"pid eq %s\" /fo csv /nh", v[1]);
        String result = IOUtils.toString(execCommand(command2), "EUC-KR");
        result = result.replaceAll("\"", "");

        return result;
    }

    public static InputStream execCommand(String... command) throws Exception {
        List<String> commands = Lists.newArrayList("cmd", "/c");
        commands.addAll(Lists.newArrayList(command));
        String[] cmds = commands.toArray(new String[commands.size()]);

        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        Process process = processBuilder.start();
        process.waitFor();
        InputStream is = process.getInputStream();
        process.destroy();
        return is;
    }
}
