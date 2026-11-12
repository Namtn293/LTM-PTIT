package ws;

import ws.object.EmployeeY;
import ws.object.ObjectService;
import ws.object.SoapObjectService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ObjectEmployeeYSort {
    public static void main(String[] args) {
        String studentCode = "B23DCCN592";
        String qCode = "bgModAbR"; // Thay bằng qCode trên đề

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        // a. Nhận danh sách EmployeeY từ server
        List<EmployeeY> list = port.requestListEmployeeY(studentCode, qCode);

        // b. Sắp xếp thâm niên từ cao đến thấp (startDate sớm nhất đứng đầu, giữ nguyên thứ tự nếu bằng nhau)
        if (list != null) {
            list.sort(Comparator.comparingLong(e -> e.getStartDate().toGregorianCalendar().getTimeInMillis()));
        }


        // c. Gửi danh sách đã sắp xếp trở lại server
        port.submitListEmployeeY(studentCode, qCode, list);
    }
}

