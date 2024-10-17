package com.fds.flex.core.cadmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface TongHopKetQuaXuLyDonPhanAnhKienNghiService {

    //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
    Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //Tổng số đơn phải xử lý - cột Tiếp nhận trong kỳ (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //7.	Số đơn đã xem xét điều kiện xử lý, cột Kỳ trước chuyển qua - cột (5)
    Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //8.	Số đơn đã xem xét điều kiện xử lý, cột Tiếp nhận trong kỳ - cột (6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.		Số đơn tố cáo đủ điều kiện xử lý - cột (7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //11.	Số vụ việc đủ điều kiện xử lý - cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Chế độ chính sách) - cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Đất đai nhà cửa) - cột (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Phân loại vụ việc theo nội dung, Lĩnh vực Tư pháp - cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Vụ việc đã có kết quả giải quyết - cột (13)
    Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //18.	Vụ việc thuộc thẩm quyền giải quyết  - cột (15)
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //20.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Chuyển đơn - cột (17)
    Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //21.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Đôn đốc giải quyết - cột (18)
    Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //22.	Vụ việc thuộc thẩm quyền và đã giải quyết  - cột (19)
    Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to);


}
