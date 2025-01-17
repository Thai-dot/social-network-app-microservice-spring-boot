package com.thaidot.file_service.enums;

public enum FileTypeUpload {
    ANY("any"),
    IMAGE("image"),
    DOCUMENT("document"),
    VIDEO("video"),
    AUDIO("audio");

    private final String type;

    FileTypeUpload(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
