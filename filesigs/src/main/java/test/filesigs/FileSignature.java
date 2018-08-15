package test.filesigs;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FileSignature implements Serializable {

    private final String[] hexSignature;
    private final String offset;
    private final String[] fileExtension;
    private final String description;

    public FileSignature(String[] hexSignature, String offset, String[] fileExtension, String description) {
        this.hexSignature = hexSignature;
        this.offset = offset;
        this.fileExtension = fileExtension;
        this.description = description;
    }

    public String[] getHexSignature() {
        return hexSignature;
    }

    public String getOffset() {
        return offset;
    }

    public String[] getFileExtension() {
        return fileExtension;
    }

    public String getDescription() {
        return description;
    }
}
