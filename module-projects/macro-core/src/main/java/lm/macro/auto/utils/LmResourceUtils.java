package lm.macro.auto.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LmResourceUtils {
    public static File getConfigFile(String path, String fileName) throws IOException {
        List<File> files = getConfigFiles(path);
        for (File f : files) {
            if (fileName.equals(f.getName())) {
                return f;
            }
        }

        return null;
    }

    public static Map<String, List<Resource>> getConfigResourceAll() throws IOException {
        List<String> dirNames = new ArrayList<>();
        Map<String, List<Resource>> result = new TreeMap<>();
        result.computeIfAbsent("conf", k -> new ArrayList<>());

        File confDir = LmResourceUtils.getFile("conf");
        File[] dirs = confDir != null ? confDir.listFiles(pathname -> !pathname.isFile() && pathname.isDirectory()) : new File[0];

        if (dirs != null) {
            for (File directory : dirs) {
                dirNames.add(directory.getName());
            }
        }

        try {
            result.get("conf").addAll(listResources("conf"));

            for (String dirName : dirNames) {
                String key = "conf/" + dirName;

                result.computeIfAbsent(key, k -> new ArrayList<>());
                result.get(key).addAll(listResources(key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<File> getConfigFiles(String dir) throws IOException {
        List<File> result = new ArrayList<>();
        Map<String, List<Resource>> listMap = getConfigResourceAll();
        List<Resource> resources = listMap.get(dir);

        for (Resource s : resources) {
            try {
                result.add(s.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static List<Resource> getConfigResources(String dir) throws IOException {
        Map<String, List<Resource>> listMap = getConfigResourceAll();
        return listMap.get(dir);
    }

    public static List<Resource> listResources(String dirName) throws IOException {
        List<Resource> result = new ArrayList<>();
        File dir = LmResourceUtils.getFile(dirName);

        if (dir != null) {
            File[] files = dir.listFiles(pathname -> pathname.isFile() && !pathname.isDirectory());
            if (files != null) {
                for (File file : files) {
                    result.add(new FileSystemResource(file));
                }
            }
        }
        return result;
    }

    public static Resource getResource(String fileName) {
        //파일에서 먼저 검사
        FileSystemResource fileSystemResource = new FileSystemResource(fileName);

        if (fileSystemResource.exists()) {
            return fileSystemResource;
        }

        //파일에서 찾지 못했다면 클레스패스에서 검사
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        if (classPathResource.exists()) {
            try {
                return classPathResource;
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    public static File getFile(String fileName) throws IOException {
        Resource resource = getResource(fileName);

        if (resource != null && resource.exists()) {
            return resource.getFile();
        }

        return null;
    }
}
