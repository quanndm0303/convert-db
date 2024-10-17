package com.fds.flex.core.cadmgt.service.statistic;

import java.util.List;
import java.util.Map;

public interface KetQuaTiepCongDanThuongXuyenDinhKyDotXuatService {

    //4.	Tiếp thường xuyên  - Số lượt tiếp  cột (4)
    Map<String, Long> queryValue4(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //5.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số lượt tiếp cột (13)
    Map<String, Long> queryValue13(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //6.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số lượt tiếp cột (22)
    Map<String, Long> queryValue22(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //8.	Tiếp thường xuyên  - Số người được tiếp  cột (5)
    Map<String, Long> queryValue5(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //9.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số người được tiếp cột (14)
    Map<String, Long> queryValue14(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //10.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số người được tiếp cột (23)
    Map<String, Long> queryValue23(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //12.	Tiếp thường xuyên  - Số vụ việc tiếp lần đầu - cột (6)
    Map<String, Long> queryValue6(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //13.	Tiếp thường xuyên  - Số vụ việc tiếp nhiều lần - cột (7)
    Map<String, Long> queryValue7(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //14.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số vụ việc tiếp lần đầu -  cột (15)
    Map<String, Long> queryValue15(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //15.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số vụ việc tiếp nhiều lần -  cột (16)
    Map<String, Long> queryValue16(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //16.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số vụ việc tiếp lần đầu - cột (24)
    Map<String, Long> queryValue24(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //17.	Tiếp định kỳ và đột xuất - Thủ trưởng ỦY QUYỀN – Số vụ việc tiếp nhiều lần - cột (25)
    Map<String, Long> queryValue25(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //19.	Tiếp thường xuyên  - Đoàn đông người - Số đoàn tiếp  cột (8)
    Map<String, Long> queryValue8(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //20.	Tiếp thường xuyên  - Đoàn đông người - Số người được tiếp  cột (9)
    Map<String, Long> queryValue9(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //21.	Tiếp thường xuyên  - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (10)
    Map<String, Long> queryValue10(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //22.	Tiếp thường xuyên  - Đoàn đông người - Số vụ việc tiếp nhiều lần - cột (11)
    Map<String, Long> queryValue11(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //23.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp – Số kỳ tiếp  - cột (12)
    Map<String, Long> queryValue12(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //24.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp – Số kỳ tiếp  - cột (21)
    Map<String, Long> queryValue21(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //25.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp  - Đoàn đông người - Số đoàn tiếp  cột (17)
    Map<String, Long> queryValue17(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //26.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp - Đoàn đông người - Số người được tiếp  cột (18)
    Map<String, Long> queryValue18(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //27.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp  - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (19)
    Map<String, Long> queryValue19(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //28.	Tiếp định kỳ và đột xuất - Thủ trưởng tiếp  - Đoàn đông người - Số vụ việc tiếp nhiều lần - cột (20)
    Map<String, Long> queryValue20(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //29.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp - Đoàn đông người - Số đoàn tiếp  cột (26)
    Map<String, Long> queryValue26(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //30.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp  - Đoàn đông người - Số người được tiếp  cột (27)
    Map<String, Long> queryValue27(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //31.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp - Đoàn đông người - Số vụ việc tiếp lần đầu - cột (28)
    Map<String, Long> queryValue28(List<String> coQuanQuanLy, long ca_from, long ca_to);

    //32.	Tiếp định kỳ và đột xuất - Thủ trưởng ủy quyền tiếp  - Đoàn đông người - Số vụ việc tiếp nhiều lần - cột (29)
    Map<String, Long> queryValue29(List<String> coQuanQuanLy, long ca_from, long ca_to);
}
