package hades.bg;

import hades.bg.bean.ColumnInfo;
import hades.bg.util.CamelCaseUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassWriter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Charset charset = Charset.forName("utf-8");

    public void write(Map<String, List<ColumnInfo>> tableInfoMap, String basePackage) {
        if (basePackage == null) {
            basePackage = "";
        }
        String basePath = "temp/" + basePackage.replaceAll("\\.", "/");
        File basePathFile = new File(basePath);
        basePathFile.mkdirs();

        for (Map.Entry<String, List<ColumnInfo>> entry : tableInfoMap.entrySet()) {
            String className = CamelCaseUtil.getClassName(entry.getKey());
            List<ColumnInfo> columnInfoList = entry.getValue();

            File javaFile = new File(basePath, className + ".java");
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try {
                fos = new FileOutputStream(javaFile);
                osw = new OutputStreamWriter(fos, charset);
                bw = new BufferedWriter(osw);

                // package
                bw.write("package " + basePackage + ";\r\n\r\n");

                // import
                boolean dateImport = false;
                boolean bigdecimalImport = false;
                for (ColumnInfo columnInfo : columnInfoList) {
                    if (Date.class.equals(columnInfo.getFieldType())) {
                        dateImport = true;
                    } else if (BigDecimal.class.equals(columnInfo.getFieldType())) {
                        bigdecimalImport = true;
                    }
                }
                if (bigdecimalImport) {
                    bw.write("import java.math.BigDecimal;\r\n");
                }
                if (dateImport) {
                    bw.write("import java.util.Date;\r\n");
                }
                if (bigdecimalImport || dateImport) {
                    bw.newLine();
                }

                // class
                bw.write("public class " + className + " {\r\n\r\n");

                // field
                for (ColumnInfo columnInfo : columnInfoList) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("    private ").append(columnInfo.getFieldType().getSimpleName()).append(" ")
                            .append(CamelCaseUtil.getFieldName(columnInfo.getColumnName())).append(";\r\n");
                    bw.write(sb.toString());
                }

                bw.newLine();

                // get set
                for (ColumnInfo columnInfo : columnInfoList) {
                    String fieldName = CamelCaseUtil.getFieldName(columnInfo.getColumnName());
                    String fieldClassName = CamelCaseUtil.getClassName(columnInfo.getColumnName());
                    String fieldType = columnInfo.getFieldType().getSimpleName();
                    StringBuilder sb = new StringBuilder();
                    sb.append("    public ").append(fieldType).append(" get").append(fieldClassName)
                            .append("() {\r\n        return ").append(fieldName)
                            .append(";\r\n    }\r\n\r\n    public void set").append(fieldClassName).append("(")
                            .append(fieldType).append(" ").append(fieldName).append(") {\r\n        this.")
                            .append(fieldName).append(" = ").append(fieldName).append(";\r\n    }\r\n\r\n");
                    bw.write(sb.toString());
                }

                bw.write("}");

            } catch (IOException e) {
                logger.error("", e);
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (osw != null) {
                        osw.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
    }
}
