package test.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import test.cmd.util.Log4jUtils;

public class App {

    public void runB() {
        Logger logger = Log4jUtils.getLogger("runB");
        logger.info("runB");
    }

    public void runA() {
        Logger logger = Log4jUtils.getLogger("runA");
        logger.info("runA");
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[] { "-a" };
        }

        Option runAOption = Option.builder("a").hasArg(false).desc("run a").build();
        Option runBOption = Option.builder("b").hasArg(false).desc("run b").build();
        Option helpOption = Option.builder("h").hasArg(false).desc("help").build();

        Options options = new Options();
        options.addOption(runAOption);
        options.addOption(runBOption);
        options.addOption(helpOption);

        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            if (cmd.hasOption(helpOption.getOpt())) {
                new HelpFormatter().printHelp("App", options, true);
            } else {
                App app = new App();
                if (cmd.hasOption(runAOption.getOpt())) {
                    app.runA();
                }
                if (cmd.hasOption(runBOption.getOpt())) {
                    app.runB();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            new HelpFormatter().printHelp("App", options, true);
        }
    }
}
