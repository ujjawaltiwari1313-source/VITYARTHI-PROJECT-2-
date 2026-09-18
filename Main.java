
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<TrafficReport> history = new ArrayList<>();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println("\n==========================================");
            System.out.println("       DDOS TRAFFIC DETECTION SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Analyze Network Traffic");
            System.out.println("2. View Detection History");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    analyzeTraffic();
                    break;

                case "2":
                    showHistory();
                    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    public static void analyzeTraffic() {

        int packets = readInteger(
                "Enter packets received per second: ");

        int uniqueIPs = readInteger(
                "Enter number of unique IP addresses: ");

        int packetSize = readInteger(
                "Enter average packet size (bytes): ");

        if (packets < 0 || uniqueIPs < 0 || packetSize <= 0) {
            System.out.println("Invalid input! Values must be positive.");
            return;
        }

        long trafficVolume = (long) packets * packetSize;

        String status;
        String severity;
        String reason;

        if (packets > 1000) {

            status = "SUSPICIOUS TRAFFIC";
            severity = "HIGH";
            reason = "High packet rate detected.";

        } else if (uniqueIPs > 100 && packets > 500) {

            status = "SUSPICIOUS TRAFFIC";
            severity = "MEDIUM";
            reason = "High traffic from multiple IP addresses.";

        } else if (packetSize < 40 && packets > 800) {

            status = "SUSPICIOUS TRAFFIC";
            severity = "MEDIUM";
            reason = "High rate of unusually small packets.";

        } else {

            status = "NORMAL TRAFFIC";
            severity = "LOW";
            reason = "Traffic is within configured thresholds.";
        }

        TrafficReport report = new TrafficReport(
                packets, uniqueIPs, packetSize,
                trafficVolume, status, severity, reason
        );

        history.add(report);

        System.out.println("\n========== TRAFFIC REPORT ==========");
        System.out.println("Packets per second : " + packets);
        System.out.println("Unique IP addresses: " + uniqueIPs);
        System.out.println("Packet size        : " + packetSize + " bytes");
        System.out.println("Traffic volume     : " + trafficVolume + " bytes/sec");
        System.out.println("Status             : " + status);
        System.out.println("Severity           : " + severity);
        System.out.println("Reason             : " + reason);
        System.out.println("====================================");
    }

    public static void showHistory() {

        if (history.isEmpty()) {
            System.out.println("\nNo traffic analysis has been recorded yet.");
            return;
        }

        System.out.println("\n========== DETECTION HISTORY ==========");

        for (int i = 0; i < history.size(); i++) {

            System.out.println("\nReport #" + (i + 1));
            System.out.println(history.get(i));
        }

        System.out.println("=======================================");
    }

    public static int readInteger(String message) {

        while (true) {

            System.out.print(message);
            String input = sc.nextLine();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static class TrafficReport {

        int packets;
        int uniqueIPs;
        int packetSize;
        long trafficVolume;
        String status;
        String severity;
        String reason;

        TrafficReport(int packets, int uniqueIPs, int packetSize,
                      long trafficVolume, String status,
                      String severity, String reason) {

            this.packets = packets;
            this.uniqueIPs = uniqueIPs;
            this.packetSize = packetSize;
            this.trafficVolume = trafficVolume;
            this.status = status;
            this.severity = severity;
            this.reason = reason;
        }

        @Override
        public String toString() {

            return "Packets: " + packets
                    + ", Unique IPs: " + uniqueIPs
                    + ", Status: " + status
                    + ", Severity: " + severity
                    + ", Volume: " + trafficVolume + " bytes/sec";
        }
    }
}