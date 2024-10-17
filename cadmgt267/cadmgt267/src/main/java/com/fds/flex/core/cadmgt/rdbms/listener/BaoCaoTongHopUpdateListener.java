//package com.fds.flex.core.cadmgt.rdbms.listener;
//
//import com.fds.flex.common.ultility.Validator;
//import com.fds.flex.core.cadmgt.entity.Extra_Model.HoatDongThanhTra_DoiTuongThanhTra;
//import com.fds.flex.core.cadmgt.entity.Extra_Model.ThongBaoKetLuan_XuLyVPHC;
//import com.fds.flex.core.cadmgt.entity.S_Model.DeXuatKienNghi;
//import com.fds.flex.core.cadmgt.publisher.PublisherEvent;
//import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSBaoCaoTongHop;
//import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSHoatDongThanhTra;
//import com.fds.flex.core.cadmgt.rdbms.entity.T_Model.RDBMSThongBaoKetLuan;
//import com.fds.flex.core.cadmgt.rdbms.repository.T_Repository.RDBMSBaoCaoTongHopRepository;
//import com.fds.flex.core.cadmgt.rdbms.repository.T_Repository.RDBMSHoatDongThanhTraRepository;
//import com.fds.flex.modelbuilder.constant.DBConstant;
//import com.fds.flex.modelbuilder.entity.Common_Model.TrangThaiDuLieu;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class BaoCaoTongHopUpdateListener {
//
//    //@Autowired
//    private RDBMSBaoCaoTongHopRepository rdbmsBaoCaoTongHopRepository;
//    //@Autowired
//    private RDBMSHoatDongThanhTraRepository rdbmsHoatDongThanhTraRepository;
//
//    @Async
//    @EventListener(condition = "#event.eventType.equals('deleteBaoCao')")
//    public void deleteBaoCaoListener(PublisherEvent event) {
//        if (event.getSource() instanceof RDBMSThongBaoKetLuan) {
//            RDBMSThongBaoKetLuan rdbmsThongBaoKetLuan = (RDBMSThongBaoKetLuan) event.getSource();
//
//            //Xóa bản ghi cũ
//            List<RDBMSBaoCaoTongHop> rdbmsBaoCaoTongHopList = rdbmsBaoCaoTongHopRepository.findByMaLopDuLieu_LopDuLieu(rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName());
//            if (!rdbmsBaoCaoTongHopList.isEmpty()) {
//                rdbmsBaoCaoTongHopRepository.deleteAll(rdbmsBaoCaoTongHopList);
//            }
//        } else if (event.getSource() instanceof RDBMSHoatDongThanhTra) {
//            RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra = (RDBMSHoatDongThanhTra) event.getSource();
//
//            //Xóa bản ghi cũ
//            List<RDBMSBaoCaoTongHop> rdbmsBaoCaoTongHopList = rdbmsBaoCaoTongHopRepository.findByMaLopDuLieu_LopDuLieu(rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName());
//            if (!rdbmsBaoCaoTongHopList.isEmpty()) {
//                rdbmsBaoCaoTongHopRepository.deleteAll(rdbmsBaoCaoTongHopList);
//            }
//        }
//    }
//
//    @Async
//    @EventListener(condition = "#event.eventType.equals('syncBaoCao')")
//    public void syncBaoCaoListener(PublisherEvent event) {
//        // Use the injected service
//        if (event.getSource() instanceof RDBMSThongBaoKetLuan) {
//            RDBMSThongBaoKetLuan rdbmsThongBaoKetLuan = (RDBMSThongBaoKetLuan) event.getSource();
//
//            //Xóa bản ghi cũ
//            List<RDBMSBaoCaoTongHop> rdbmsBaoCaoTongHopList = rdbmsBaoCaoTongHopRepository.findByMaLopDuLieu_LopDuLieu(rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName());
//            if (!rdbmsBaoCaoTongHopList.isEmpty()) {
//                rdbmsBaoCaoTongHopRepository.deleteAll(rdbmsBaoCaoTongHopList);
//            }
//            // Tạo dữ liệu cần thiết
//            if (!rdbmsThongBaoKetLuan.getTrangThaiDuLieu().getMaMuc().equals(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())) {
//                return;
//            }
//            List<RDBMSBaoCaoTongHop> baoCaoTongHopList = new ArrayList<>();
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                    "LoaiThongBaoKetLuan.MaMuc", "LoaiThongBaoKetLuan.MaMuc", null, rdbmsThongBaoKetLuan.getLoaiThongBaoKetLuan().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                    "CoQuanQuanLy.MaDinhDanh", "CoQuanQuanLy.MaDinhDanh", null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                    "NgayVanBan", "NgayVanBan", rdbmsThongBaoKetLuan.getNgayVanBan(), null, null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                    "TrangThaiDuLieu.MaMuc", "TrangThaiDuLieu.MaMuc", null, rdbmsThongBaoKetLuan.getTrangThaiDuLieu().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//            if (!rdbmsThongBaoKetLuan.getDeXuatKienNghi().isEmpty()) {
//                int index = 0;
//                for (DeXuatKienNghi deXuatKienNghi : rdbmsThongBaoKetLuan.getDeXuatKienNghi()) {
//
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "DeXuatKienNghi[" + index + "].LoaiDeXuatKienNghi.MaMuc", "DeXuatKienNghi.LoaiDeXuatKienNghi.MaMuc", null, deXuatKienNghi.getLoaiDeXuatKienNghi().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "DeXuatKienNghi[" + index + "].DienTichThuHoi", "DeXuatKienNghi.DienTichThuHoi", null, null, deXuatKienNghi.getDienTichThuHoi(), rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "DeXuatKienNghi[" + index + "].SoTienThuHoi", "DeXuatKienNghi.SoTienThuHoi", deXuatKienNghi.getSoTienThuHoi(), null, null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "DeXuatKienNghi[" + index + "].IDKienNghi", "DeXuatKienNghi.IDKienNghi", null, deXuatKienNghi.getIdKienNghi(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                    if (!deXuatKienNghi.getDoiTuongXuLy().isEmpty()) {
//                        int index2 = 0;
//                        for (DeXuatKienNghi.DoiTuongXuLy doiTuongXuLy : deXuatKienNghi.getDoiTuongXuLy()) {
//                            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                                    "DeXuatKienNghi[" + index + "].DoiTuongXuLy.[" + index2 + "].MaDinhDanh", "DeXuatKienNghi.DoiTuongXuLy.MaDinhDanh", null, doiTuongXuLy.getMaDinhDanh(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                                    "DeXuatKienNghi[" + index + "].DoiTuongXuLy.[" + index2 + "].LoaiDoiTuongCNTC.MaMuc", "DeXuatKienNghi.DoiTuongXuLy.LoaiDoiTuongCNTC.MaMuc", null, doiTuongXuLy.getLoaiDoiTuongCNTC().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                            index2++;
//                        }
//                    }
//
//                    index++;
//                }
//            }
//            if (!rdbmsThongBaoKetLuan.getXuLyVPHC().isEmpty()) {
//                int index = 0;
//                for (ThongBaoKetLuan_XuLyVPHC xuLyVPHC : rdbmsThongBaoKetLuan.getXuLyVPHC()) {
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "XuLyVPHC[" + index + "].DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc", "XuLyVPHC.DoiTuongVPHC.LoaiDoiTuongCNTC.MaMuc", null, xuLyVPHC.getDoiTuongVPHC().getLoaiDoiTuongCNTC().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "XuLyVPHC[" + index + "].MaDinhDanh", "XuLyVPHC.MaDinhDanh", null, xuLyVPHC.getMaDinhDanh(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                    baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                            rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                            "XuLyVPHC[" + index + "].HinhThucXuLyChinh.MaMuc", "XuLyVPHC.HinhThucXuLyChinh.MaMuc", null, xuLyVPHC.getHinhThucXuLyChinh().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//                    index++;
//                }
//            }
//            if (Validator.isNotNull(rdbmsThongBaoKetLuan.getDoiTuongKetLuan()) && rdbmsThongBaoKetLuan.getDoiTuongKetLuan().getType().equals(DBConstant.T_HOAT_DONG_THANH_TRA)) {
//                baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                        rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                        null, "DoiTuongKetLuan.@type", null, rdbmsThongBaoKetLuan.getDoiTuongKetLuan().getType(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//                baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                        rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                        null, "DoiTuongKetLuan.MaDinhDanh", null, rdbmsThongBaoKetLuan.getDoiTuongKetLuan().getMaDinhDanh(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//                Optional<RDBMSHoatDongThanhTra> hoatDongThanhTraOptional = rdbmsHoatDongThanhTraRepository.findByMaDinhDanhAndTrangThaiDuLieu(rdbmsThongBaoKetLuan.getDoiTuongKetLuan().getMaDinhDanh(), TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc());
//                if (hoatDongThanhTraOptional.isPresent()) {
//                    RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra = hoatDongThanhTraOptional.get();
//                    if (!rdbmsHoatDongThanhTra.getDoiTuongThanhTra().isEmpty()) {
//                        int index = 0;
//                        for (HoatDongThanhTra_DoiTuongThanhTra doiTuongThanhTra : rdbmsHoatDongThanhTra.getDoiTuongThanhTra()) {
//                            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                                    "DoiTuongKetLuan.DoiTuongThanhTra["+index+"].LoaiDoiTuongCNTC.MaMuc", "DoiTuongKetLuan.DoiTuongThanhTra.LoaiDoiTuongCNTC.MaMuc", null, doiTuongThanhTra.getLoaiDoiTuongCNTC().getMaMuc(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                                    rdbmsThongBaoKetLuan.getId(), rdbmsThongBaoKetLuan.getClass().getName(), rdbmsThongBaoKetLuan.getThoiGianTao(),
//                                    "DoiTuongKetLuan.DoiTuongThanhTra["+index+"].MaDinhDanh", "DoiTuongKetLuan.DoiTuongThanhTra.MaDinhDanh", null, doiTuongThanhTra.getMaDinhDanh(), null, rdbmsThongBaoKetLuan.getCoQuanQuanLy().getMaDinhDanh()));
//
//                            index++;
//                        }
//                    }
//                }
//            }
//
//
//            rdbmsBaoCaoTongHopRepository.saveAll(rdbmsBaoCaoTongHopList);
//        } else if (event.getSource() instanceof RDBMSHoatDongThanhTra) {
//            RDBMSHoatDongThanhTra rdbmsHoatDongThanhTra = (RDBMSHoatDongThanhTra) event.getSource();
//
//            List<RDBMSBaoCaoTongHop> rdbmsBaoCaoTongHopList = rdbmsBaoCaoTongHopRepository.findByMaLopDuLieu_LopDuLieu(rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName());
//            if (!rdbmsBaoCaoTongHopList.isEmpty()) {
//                rdbmsBaoCaoTongHopRepository.deleteAll(rdbmsBaoCaoTongHopList);
//            }
//
//            if (!rdbmsHoatDongThanhTra.getTrangThaiDuLieu().getMaMuc().equals(TrangThaiDuLieu.TrangThai.ChinhThuc.getMaMuc())) {
//                return;
//            }
//            List<RDBMSBaoCaoTongHop> baoCaoTongHopList = new ArrayList<>();
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "CoQuanQuanLy.MaDinhDanh", "CoQuanQuanLy.MaDinhDanh", null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh(), null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "TrangThaiDuLieu.MaMuc", "TrangThaiDuLieu.MaMuc", null, rdbmsHoatDongThanhTra.getTrangThaiDuLieu().getMaMuc(), null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "LoaiHoatDongThanhTra.MaMuc", "LoaiHoatDongThanhTra.MaMuc", null, rdbmsHoatDongThanhTra.getLoaiHoatDongThanhTra().getMaMuc(), null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "ThoiHanThucHien", "ThoiHanThucHien", rdbmsHoatDongThanhTra.getThoiHanThucHien(), null, null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "NgayKetThuc", "NgayKetThuc", rdbmsHoatDongThanhTra.getNgayKetThuc(), null, null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "GiaHanThucHien", "GiaHanThucHien", rdbmsHoatDongThanhTra.getGiaHanThucHien(), null, null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "TrangThaiHoatDongThanhTra.MaMuc", "TrangThaiHoatDongThanhTra.MaMuc", null, rdbmsHoatDongThanhTra.getTrangThaiHoatDongThanhTra().getMaMuc(), null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//
//            baoCaoTongHopList.add(new RDBMSBaoCaoTongHop(
//                    rdbmsHoatDongThanhTra.getId(), rdbmsHoatDongThanhTra.getClass().getName(), rdbmsHoatDongThanhTra.getThoiGianTao(),
//                    "LoaiCheDoThanhTra.MaMuc", "LoaiCheDoThanhTra.MaMuc", null, rdbmsHoatDongThanhTra.getLoaiCheDoThanhTra().getMaMuc(), null, rdbmsHoatDongThanhTra.getCoQuanQuanLy().getMaDinhDanh()));
//
//            rdbmsBaoCaoTongHopRepository.saveAll(rdbmsBaoCaoTongHopList);
//        }
//    }
//}
