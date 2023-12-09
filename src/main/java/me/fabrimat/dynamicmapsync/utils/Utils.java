package me.fabrimat.dynamicmapsync.utils;

import java.util.Optional;

import static java.lang.Integer.parseInt;

public class Utils {
    public static boolean isRequiredJDKVersion(int targetMajor, int targetMinor, int targetBuild){
        String javaVersion = System.getProperty("java.version");
        String[] versionParts = javaVersion.split("\\.");
        if(versionParts.length < 3){
            return versionParts.length == 1
                    && targetMinor == -1
                    && targetBuild == -1
                    && parseInt(versionParts[0], 10) >= targetMajor;//we only have a major version and thats ok
//can not evaluate
        }
        int major = parseInt(versionParts[0], 10);
        int minor = parseInt(versionParts[1], 10);
        int build = parseInt(versionParts[2], 10);
        return major != -1 && major >= targetMajor &&
                minor != -1 && minor >= targetMinor &&
                build != -1 && build >= targetBuild;
    }

    public static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
