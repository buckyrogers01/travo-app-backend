package com.application.travo.Utility;

import java.util.UUID;

public class FileUtil {
    public static String generateFileName(String originalName) {
        String cleanName = originalName.replaceAll("\\s+", "_");
        return UUID.randomUUID() + "_" + cleanName;
    }
}
