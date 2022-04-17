package com.shramko.parser.dto;

public enum Extension {
    CSV("csv"), JSON("json"), UNKNOWN("");

    private String extension;

    Extension(String extension) {
        this.extension = extension;
    }

    Extension() {
    }

    public static Extension getFileExtension(final String filename){
        final String extension = filename.substring(filename.lastIndexOf(".") + 1);
        for (Extension value : Extension.values()) {
            if (value.extension.equalsIgnoreCase(extension)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
