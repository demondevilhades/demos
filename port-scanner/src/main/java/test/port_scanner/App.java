package test.port_scanner;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.util.Config;

public class App {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String[] ips;
    private final int portStart;
    private final int portEnd;

    public App() {
        this.portStart = Config.getInt("portStart");
        this.portEnd = Config.getInt("portEnd");
        ips = Config.get("ip").split(",");
    }

    public void run() throws InterruptedException {
        Map<String, Set<Integer>> ipPortMap = new HashMap<>();
        Set<PortTest> portTestSet = new HashSet<>();
        for (String ip : ips) {
            TreeSet<Integer> vailedPortSet = new TreeSet<>();
            ipPortMap.put(ip, vailedPortSet);
            for (int i = portStart; i <= portEnd; i++) {
                PortTest portTest = new PortTest(ip, i, vailedPortSet);
                portTest.start();
                portTestSet.add(portTest);
            }
        }
        for (PortTest portTest : portTestSet) {
            portTest.join();
        }
        for (Map.Entry<String, Set<Integer>> entry : ipPortMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getKey()).append(":").append(StringUtils.join(entry.getValue(), ","));
            logger.info(sb.toString());
        }
    }

    class PortTest extends Thread {
        String ip;
        int port;
        Set<Integer> vailedPortSet;

        public PortTest(String ip, int port, Set<Integer> vailedPortSet) {
            this.ip = ip;
            this.port = port;
            this.vailedPortSet = vailedPortSet;
        }

        @Override
        public void run() {
            try (Socket socket = new Socket(ip, port);) {
                if (socket.isConnected()) {
                    vailedPortSet.add(port);
                }
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Config.init();
        new App().run();
    }
}
