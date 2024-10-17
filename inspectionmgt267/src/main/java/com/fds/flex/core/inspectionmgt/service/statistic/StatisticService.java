package com.fds.flex.core.inspectionmgt.service.statistic;

import com.fds.flex.core.inspectionmgt.entity.Statistic_Model.HDTT_LichCongTacDoan_Statistic;

import java.util.List;

public interface StatisticService {
    List<HDTT_LichCongTacDoan_Statistic> thongKeLichCongTacDoan(String ngayCongTac);

    List<String> thongKeLichCongTacDoanTheoThang(String namThongKe, String tuNgay, String denNgay);
}
