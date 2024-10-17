package com.fds.flex.core.cadmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface PhanLoaiXuLyDonThuQuaTiepCongDanService {

    //4.	Số đơn - nội dung Khiếu nại cột (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //5.	Số đơn - nội dung Tố cáo cột (5)
    Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //6.	Số đơn - nội dung Phản ánh, kiến nghị cột (7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //7.	Số đơn - nội dung Thuộc thẩm quyền cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //8.	Số đơn - nội dung KHÔNG thuộc thẩm quyền cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //9.	Số vụ việc - nội dung Khiếu nại cột (4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Số vụ việc - nội dung Tố cáo cột (6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //11.	Số vụ việc - nội dung Phản ánh, kiến nghị cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Số vụ việc thuộc thẩm quyền - cột (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Số vụ việc KHÔNG thuộc thẩm quyền - cột (12)
    Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Số vụ việc KHÔNG thuộc thẩm quyền, hướng dẫn - cột (13)
    Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //15.	Số vụ việc KHÔNG thuộc thẩm quyền, Chuyển đơn - cột (14)
    Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Số vụ việc KHÔNG thuộc thẩm quyền, Đôn đốc giải quyết - cột (15)
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //17.	Số vụ việc KHÔNG thuộc thẩm quyền, nhận được văn bản phúc đáp do chuyển đơn - cột (16)
    Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to);

}
