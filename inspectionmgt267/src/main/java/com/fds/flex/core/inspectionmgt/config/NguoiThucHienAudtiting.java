package com.fds.flex.core.inspectionmgt.config;

import com.fds.flex.context.model.User;
import com.fds.flex.modelbuilder.entity.Common_Model.NguoiTaoLap;
import com.fds.flex.user.context.UserContextHolder;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NguoiThucHienAudtiting implements AuditorAware<NguoiTaoLap> {

    @Override
    public Optional<NguoiTaoLap> getCurrentAuditor() {
        NguoiTaoLap nguoiTaoLap = new NguoiTaoLap();
        try {
            User.DanhTinhDienTu danhTinhDienTu = UserContextHolder.getContext().getUser().getDanhTinhDienTu();
            nguoiTaoLap.setMaDinhDanh(danhTinhDienTu.getMaDinhDanh());
            nguoiTaoLap.setTenDinhDanh(danhTinhDienTu.getTenDinhDanh());
            nguoiTaoLap.setTenGoi(danhTinhDienTu.getTenGoi());
        } catch (Exception ex) {
            nguoiTaoLap.setMaDinhDanh("guest");
            nguoiTaoLap.setTenGoi("Không xác định");
            nguoiTaoLap.setTenDinhDanh("N/A");
        }

        return Optional.of(nguoiTaoLap);
    }

}
