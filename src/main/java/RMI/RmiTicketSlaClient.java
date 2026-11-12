package RMI;

import java.rmi.Naming;

public class RmiTicketSlaClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN592";
        String qCode = "doL8Bv1b";

        ObjectService service = (ObjectService) Naming.lookup("rmi://36.50.135.242/RMIObjectService");

        // b. Nhận đối tượng RMI.TicketSla từ server
        TicketSla ticket = (TicketSla) service.requestObject(studentCode, qCode);
        if (ticket == null) {
            System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
            return;
        }

        // c. Kiểm tra vi phạm SLA và gán action
        String priority = ticket.getPriority() == null ? "" : ticket.getPriority().trim().toUpperCase();
        int hours = ticket.getOpenedHoursAgo();

        boolean breached = isBreached(priority, hours);
        ticket.setBreached(breached);

        if (!breached) {
            ticket.setAction("MONITOR");
        } else if ("CRITICAL".equals(priority) || hours > 96) {
            ticket.setAction("ESCALATE_L2");
        } else {
            ticket.setAction("ESCALATE_L1");
        }

        // d. Gửi đối tượng đã cập nhật trở lại server
        service.submitObject(studentCode, qCode, ticket);
    }

    private static boolean isBreached(String priority, int hours) {
        switch (priority) {
            case "CRITICAL":
                return hours > 2;
            case "HIGH":
                return hours > 8;
            case "MEDIUM":
                return hours > 24;
            case "LOW":
                return hours > 72;
            default:
                return false;
        }
    }
}

