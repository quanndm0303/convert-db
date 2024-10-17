package com.fds.flex.core.cadmgt.entity.Enum_Model;

import com.fds.flex.common.utility.string.StringPool;
import lombok.Getter;

public class StatisticModel {

    @Getter
    public enum XuLyTinBanDau {

        //Custom
        DeXuatThuLyVaGiaoXacMinh("08", "Đề xuất thụ lý và giao xác minh"),
        DeXuatKhongThuLy("09", "Đề xuất không thụ lý");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        XuLyTinBanDau(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public static String getTenMucByMaMuc(String maMuc) {
            for (XuLyTinBanDau xuLyTinBanDau : StatisticModel.XuLyTinBanDau.values()) {
                if (xuLyTinBanDau.maMuc.equals(maMuc)) {
                    return xuLyTinBanDau.tenMuc;
                }
            }
            return null; // Return null if no match is found
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum THGQKN_TrangThaiGiaiQuyetVuViec {

        //Custom
        DangXayDungThuTucPhapLyVaKeHoach("07", "Đang xây dựng thủ tục pháp lý và kế hoạch"),
        DangGiaiQuyet("08", "Đang giải quyết"),
        DaGiaiQuyet("09,10", "Đã giải quyết");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        THGQKN_TrangThaiGiaiQuyetVuViec(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum THGQTC_KetLuan {

        //Custom
        ToCaoDung("05", "Tố cáo đúng"),
        ToCaoSai("06", "Tố cáo sai"),
        ToCaoCoDungCoSai("07", "Tố cáo có đúng, có sai"),
        ToCaoKhongCoCoSoKetLuan("08", "Tố cáo không có cơ sở kết luận");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        THGQTC_KetLuan(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum THGQTC_KienNghiXuLy {

        //Custom
        ThuHoiChoNhaNuoc("05", "Thu hồi cho Nhà nước"),
        BoiThuongThietHaiChoNguoiToCaoCaNhanCoLienQuan("06", "Bồi thường thiệt hại cho người tố cáo, cá nhân có liên quan"),
        XyLyHanhChinh("07", "Xử lý hành chính"),
        ChuyenCoQuanDieuTra("08", "Chuyển cơ quan điều tra");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        THGQTC_KienNghiXuLy(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum THGQTC_TrangThaiGiaiQuyetVuViec {

        //Custom
        DangXayDungThuTucPhapLyVaKeHoach("07", "Chờ giải quyết"),
        DangGiaiQuyet("08", "Đang giải quyết"),
        DaGiaiQuyet("09,10", "Đã giải quyết");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        THGQTC_TrangThaiGiaiQuyetVuViec(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum THGQTC_TrangThaiXuLyGiaiQuyet {

        //Custom
        DangDeXuatLanhDaoBoGiaoKiemTraThuLy("01", "Đang đề xuất lãnh đạo Bộ giao kiểm tra điều kiện thụ lý"),
        DangChoDeKiemTraDieuKien("02", "Đang chờ để kiểm tra điều kiện"),
        DangKiemTraDieuKienThuLy("03", "Đang kiểm tra điều kiện thụ lý");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        THGQTC_TrangThaiXuLyGiaiQuyet(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum THGQTC_ThongKeSoLuongToCao {

        //Custom
        ToCaoSaiKhongXacDinhDuocHanhViViPham("01", "Tố cáo sai, không xác định được hành vi vi phạm"),
        ToCaoNacDanh("02", "Tố cáo nặc danh"),
        KhongXacDinhDuocNguoiVietDonToCao("03", "Không xác định được người viết đơn tố cáo");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        THGQTC_ThongKeSoLuongToCao(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }

    @Getter
    public enum KetQuaKiemTraDieuKienThuLy {

        //Custom
        KhongDuDieuKienThuLy("09", "Không đủ điều kiện thụ lý"),
        DuDieuKienThuLy("08", "Đủ điều kiện thụ lý");

        public String maMuc = StringPool.BLANK;
        public String tenMuc = StringPool.BLANK;

        KetQuaKiemTraDieuKienThuLy(String maMuc, String tenMuc) {
            setMaMuc(maMuc);
            setTenMuc(tenMuc);
        }

        public static String getTenMucByMaMuc(String maMuc) {
            for (KetQuaKiemTraDieuKienThuLy model : StatisticModel.KetQuaKiemTraDieuKienThuLy.values()) {
                if (model.maMuc.equals(maMuc)) {
                    return model.tenMuc;
                }
            }
            return null; // Return null if no match is found
        }

        public void setMaMuc(String maMuc) {
            this.maMuc = maMuc;
        }

        public void setTenMuc(String tenMuc) {
            this.tenMuc = tenMuc;
        }

    }
}
