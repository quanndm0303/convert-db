package com.fds.flex.core.inspectionmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface CongTacThanhTraChuyenNganhStatisticService {
    //4.	Số cuộc thanh tra thực hiện trong kỳ - cột Theo Kế hoạch (2)
    Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //5.	Số cuộc thanh tra thực hiện trong kỳ - cột Đột xuất (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //6.	Số cuộc thanh tra đã ban hành kết luận(4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //7.	Số tổ chức được thanh tra theo Kết luận(5)
    Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //8.	Số cá nhân được thanh tra theo Kết luận(6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //9.	Số tổ chức vi phạm cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Số cá nhân vi phạm cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Số Tiền (Tr.đ) vi phạm của Tổ chức cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Số Tiền (Tr.đ) vi phạm của Cá nhân cột (12)
    Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //15.	Số Tiền (Tr.đ) kiến nghị thu hồi về NSNN cột (14)
    Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Số Tiền (Tr.đ) kiến nghị thu hồi về tổ chức, đơn vị cột (15)
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //19.	Số Quyết định XPHC với Tổ chức cột (18)
    Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //20.	Số Quyết định XPHC với Cá nhân cột (19)
    Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //22.	Xử phạt VPHC bằng tiền với Tổ chức cột (21)
    Map<String, Long> queryValue21(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //23.	Xử phạt VPHC bằng tiền với Cá nhân cột (22)
    Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //25.	Xử phạt VPHC bằng hình thức khác với Tổ chức cột (24)
    Map<String, Long> queryValue24(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //26.	Xử phạt VPHC bằng hình thức khác với Cá nhân cột (25)
    Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //28.	Kiến nghị xử lý Chuyển cơ quan điều tra số vụ việc cột (26)
    Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //29.	Kiến nghị xử lý Chuyển cơ quan điều tra cột (27) Đối tượng
    Map<String, Long> queryValue27(List<String> coQuanQuanLy, long ca_from, long ca_to);
}
