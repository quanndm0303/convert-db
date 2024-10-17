package com.fds.flex.core.inspectionmgt.rdbms.service.C_Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fds.flex.context.model.User;
import com.fds.flex.core.inspectionmgt.entity.Data_Model.PhanVungDuLieuModel;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_HoSoBaoCao;
import com.fds.flex.core.inspectionmgt.entity.Extra_Model.HoatDongThanhTra_NhiemVuCongViec;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface RDBMSGrpcClientService {

    void themTruyCapTaiNguyen(String data);

    void addDanhMucGiayTo_HSNV(String hoSoNghiepVu_MaDinhDanh, String ketLuanThanhKiemTra_JSON);

    HoatDongThanhTra_NhiemVuCongViec getDetailNhiemVuCongViec(String maDinhDanh) throws JsonProcessingException;

    void updateHSNV_CBTG_KTHS(String hsnv_MaDinhDanh, String thanhVienDoanJSON);

    void deleteHSNV_CBTG_KTHS(String hsnv_MaDinhDanh, String canBo_MaDinhDanh);

    List<HoatDongThanhTra_HoSoBaoCao> getDetailHoSoBaoCao(String maDinhDanh) throws JsonProcessingException;

    JSONObject getDanhTinhDienTuByMaSoId(String maSoId);

    JSONObject findHuongDanChuyenDeByNVTK(String nhiemVuTrienKhai_MaDinhDanh);

    JSONArray getAllTaiNguyenHeThong();

    User.DanhTinhDienTu findDanhTinhDienTuByDTXTHTDT(String doiTuongXacThuc_MaDinhDanh, String doiTuongXacThuc_Type,
                                                     String heThongDinhDanh_MaMuc);

    String getPhanLoaiCacCap_CQDV(String maDinhDanh);

    List<PhanVungDuLieuModel> getPhanVungDuLieuDeThongKe(String maMuc);
}
