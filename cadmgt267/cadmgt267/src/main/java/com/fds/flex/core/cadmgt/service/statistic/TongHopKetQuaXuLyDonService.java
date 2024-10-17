package com.fds.flex.core.cadmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface TongHopKetQuaXuLyDonService {

    //4.	Tổng số đơn phải xử lý - cột Kỳ trước chuyển sang (2)
    Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //Tổng số đơn phải xử lý - cột Tiếp nhận trong kỳ (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //6.	Số đơn đã xử lý - cột (4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //9.	Số đơn đủ điều kiện xử lý - cột (6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Số đơn đủ điều kiện xử lý – nội dung khiếu nại - cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //11.	Số đơn đủ điều kiện xử lý – nội dung tố cáo - cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Số đơn đủ điều kiện xử lý – nội dung kiến nghị, phản ánh - cột (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Số đơn đã giải quyết, lần đầu - cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Số đơn đã giải quyết, nhiều lần - cột (12)
    Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Số đơn thuộc thẩm quyền giải quyết khiếu nại - cột (15)
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //17.	Số đơn thuộc thẩm quyền giải quyết tố cáo - cột (16)
    Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //18.	Số đơn thuộc thẩm quyền giải quyết kiến nghị, phản ánh - cột (17)
    Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to);


    //20.	Số đơn không thuộc thẩm quyền giải quyết - cột (18)
    Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //21.	Số đơn không thuộc thẩm quyền giải quyết, Hướng dẫn - cột (19)
    Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //22.	Số đơn không thuộc thẩm quyền giải quyết, Chuyển đơn - cột (20)
    Map<String, Long> queryValue20(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //23.	Số đơn không thuộc thẩm quyền, Đôn đốc giải quyết - cột (21)
    Map<String, Long> queryValue21(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //24.	Số văn bản phúc đáp nhận được do chuyển đơn  - cột (22)
    Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //25.	Số vụ việc đủ điều kiện xử lý - cột (7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);


}
