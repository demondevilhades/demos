package test.filesigs;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import test.filesigs.util.ResourcesUtils;

public class FileSignatureUtils {
    public static final String RESOURCE_CSV_STR = ResourcesUtils.getResourceFile("filesigs.csv");
    public static final String RESOURCE_MAP_STR = ResourcesUtils.getResourceFile("filesigs");

    private static final int MAX_FILE_SIGNATURE_LEN = 24;
    public static Map<String, FileSignature> FS_MAP = null;

    @SuppressWarnings("unchecked")
    public static void init() throws Exception {
        if (FS_MAP == null) {
            synchronized (FileSignatureUtils.class) {
                if (FS_MAP == null) {
                    try (FileInputStream fis = new FileInputStream(RESOURCE_MAP_STR);
                            ObjectInputStream ois = new ObjectInputStream(fis);) {
                        FS_MAP = (Map<String, FileSignature>) ois.readObject();
                    }
                }
            }
        }
    }

    public static byte[] getHexSignatureBs(String hexSignature) {
        String[] split = hexSignature.split(" ");
        byte[] bs = new byte[split.length];
        int len = Math.min(MAX_FILE_SIGNATURE_LEN, split.length);
        for (int i = 0; i < len; i++) {
            bs[i] = Byte.parseByte(split[i], 16);
        }
        return bs;
    }

    public static String getFileSignature(byte[] bs) {
        if (bs == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int len = Math.min(MAX_FILE_SIGNATURE_LEN, bs.length);
        for (int i = 0; i < len; i++) {
            int v = bs[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    public static String[] getFileExtension(byte[] bs) {
        return FS_MAP.get(getFileSignature(bs)).getFileExtension();
    }

    public static String[] getFileExtension(String fileSignature) {
        return FS_MAP.get(fileSignature).getFileExtension();
    }
}
