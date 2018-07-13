package test.cmd.util;

import java.io.File;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;

public class Log4jUtils {

    public static final Logger DEF_LOGGER = Logger.getLogger("infoFile");
    private static final DailyRollingFileAppender DEF_APPENDER = getDefAppender();
    private static final Map<String, Logger> LOGGER_MAP = new ConcurrentHashMap<String, Logger>();

    private static DailyRollingFileAppender getDefAppender() {
        @SuppressWarnings("unchecked")
        Enumeration<Appender> appenders = DEF_LOGGER.getAllAppenders();
        while (appenders.hasMoreElements()) {
            Appender appender = appenders.nextElement();
            if (appender instanceof DailyRollingFileAppender) {
                return (DailyRollingFileAppender) appender;
            }
        }
        return null;
    }

    public static Logger getLogger(String name) {
        name = name.toLowerCase();
        if (!LOGGER_MAP.containsKey(name)) {
            synchronized (LOGGER_MAP) {
                if (!LOGGER_MAP.containsKey(name)) {
                    Logger logger = Logger.getLogger(name);
                    logger.removeAllAppenders();
                    logger.setAdditivity(true);

                    DailyRollingFileAppender appender = new DailyRollingFileAppender();
                    appender.setLayout(DEF_APPENDER.getLayout());
                    appender.setDatePattern(DEF_APPENDER.getDatePattern());
                    appender.setBufferedIO(DEF_APPENDER.getBufferedIO());
                    appender.setBufferSize(DEF_APPENDER.getBufferSize());

                    appender.setName(name);
                    appender.setFile(new File(new File(DEF_APPENDER.getFile()).getParent(), name + ".log").getPath());
                    appender.setEncoding("UTF-8");
                    appender.setAppend(true);
                    appender.activateOptions();

                    logger.addAppender(appender);
                    LOGGER_MAP.put(name, logger);
                }
            }
        }
        return LOGGER_MAP.get(name);
    }
}
