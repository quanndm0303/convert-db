package com.fds.flex.core.cadmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface TongHopKetQuaXuLyDonKhieuNaiService {

    //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
    Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //Tổng số đơn phải xử lý - cột Tiếp nhận trong kỳ (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //6.	Số đơn đã xử lý - cột (4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //9.	Số đơn đủ điều kiện xử lý - cột (6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Số đơn đủ điều kiện xử lý – nội dung khiếu nại - cột (7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //11.	Số vụ việc đủ điều kiện xử lý - cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Vụ việc đã giải quyết lần đầu - cột (16)
    Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Vụ việc đã giải quyết lần 2 - cột (17)
    Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Vụ việc đã có bản án TAND - cột (18)
    Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Vụ việc thuộc thẩm quyền - cột (20)
    Map<String, Long> queryValue20(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //17.	Vụ việc thuộc thẩm quyền, giải quyết lần 2 - cột (22)
    Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //19.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Hướng dẫn - cột (24)
    Map<String, Long> queryValue24(List<String> coQuanQuanLy, long ca_from, long ca_to);


    //20.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Đôn đốc giải quyết - cột (25)
    Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //22.	Số văn bản phúc đáp nhận được do chuyển đơn  - cột (26)
    Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //23.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính  - cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //24.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Chế độ chính sách) - cột (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //25.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Đất đai nhà cửa) - cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //27.	Phân loại vụ việc theo nội dung, Lĩnh vực tư pháp - cột (13)
    Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //28.	Phân loại vụ việc theo nội dung, Lĩnh vực Đảng, đoàn thể - cột (14)
    Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to);


}
