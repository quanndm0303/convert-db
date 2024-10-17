package com.fds.flex.core.inspectionmgt.rdbms.service.Statistic_Service;

import java.util.List;
import java.util.Map;

public interface RDBMSCongTacThanhTraHanhChinhStatisticService {
    //4.	Số cuộc thanh tra thực hiện trong kỳ - cột Triển khai từ kỳ trước chuyển sang (2)
    Map<String, Long> queryValue2(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //5.	Số cuộc thanh tra thực hiện trong kỳ - cột Triển khai trong kỳ (3)
    Map<String, Long> queryValue3(List<String> coQuanQuanLy, long ca_from, long ca_to);


    //6.	Số cuộc thanh tra thực hiện trong kỳ - cột Theo Kế hoạch (4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //7.	Số cuộc thanh tra thực hiện trong kỳ - cột Đột xuất (5)
    Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //8.	Số cuộc thanh tra đã ban hành kết luận(6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //9.	Số đơn vị được thanh tra theo Kết luận(7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Kiến nghị xử lý Thu hồi về NSNN cột Tiền (Tr.đ) (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //11.	Kiến nghị xử lý Xử lý khác về kinh tế cột Tiền (Tr.đ) (12)
    Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Kiến nghị xử lý Thu hồi về NSNN cột Đất (m2) (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Kiến nghị xử lý Xử lý khác về kinh tế cột Đất (m2) (13)
    Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Kiến nghị xử lý Hành chính cột (14) Tổ chức
    Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //17.	Kiến nghị xử lý Hành chính cột (15) Cá nhân
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //18.	Kiến nghị xử lý Chuyển cơ quan điều tra số vụ việc cột (16)
    Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //19.	Kiến nghị xử lý Chuyển cơ quan điều tra cột (17)Đối tượng
    Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //20.	Hoàn thiện cơ chế chính sách cột (18) (số văn bản)
    Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to);
}
