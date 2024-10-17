package com.fds.flex.core.cadmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface TongHopKetQuaXuLyDonToCaoService {

    //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
    Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //Tổng số đơn phải xử lý - cột Tiếp nhận trong kỳ (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //7.	Số đơn đã xử lý - cột (4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //8.	Số đơn tiếp nhận trong kỳ và đã xử lý - cột (6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Số đơn tố cáo đủ điều kiện xử lý - cột (7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //11.	Số vụ việc đủ điều kiện xử lý - cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính  - cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Chế độ chính sách) - cột (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Đất đai nhà cửa) - cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //15.	Phân loại vụ việc theo nội dung, Lĩnh vực hành chính (Công chức, công vụ) - cột (12)
    Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //17.	Phân loại vụ việc theo nội dung, Tham nhũng - cột (14)
    Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //18.	Phân loại vụ việc theo nội dung, Lĩnh vực tư pháp - cột (15)
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //19.	Phân loại vụ việc theo nội dung, Lĩnh vực Đảng, đoàn thể - cột (16)
    Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //21.	Vụ việc đã có kết quả giải quyết - cột (19)
    Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //22.	Vụ việc chưa giải quyết - cột (20)
    Map<String, Long> queryValue20(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //24.	Vụ việc thuộc thẩm quyền giải quyết, lần đầu - cột (22)
    Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //25.	Vụ việc thuộc thẩm quyền giải quyết, tố cáo tiếp - cột (17)
    Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //27.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Chuyển đơn - cột (25)
    Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //28.	Vụ việc KHÔNG thuộc thẩm quyền giải quyết, Đôn đốc giải quyết - cột (26)
    Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //30.	Số văn bản phúc đáp nhận được do chuyển đơn  - cột (27)
    Map<String, Long> queryValue27(List<String> coQuanQuanLy, long ca_from, long ca_to);
}
