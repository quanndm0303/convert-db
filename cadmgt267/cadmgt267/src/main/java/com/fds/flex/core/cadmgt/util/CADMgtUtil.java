package com.fds.flex.core.cadmgt.util;

import com.fds.flex.common.exception.InternalServerException;
import com.fds.flex.common.ultility.CompareObjectUtil;
import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.common.utility.string.StringUtil;
import com.fds.flex.core.cadmgt.constant.Constant;
import com.fds.flex.modelbuilder.constant.DBConstant;
import com.fds.flex.modelbuilder.entity.Common_Model.NhatKiSuaDoi;
import com.fds.flex.user.context.UserContext;
import com.fds.flex.user.context.UserContextHolder;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vietdd
 */
@Slf4j
public class CADMgtUtil {

    public static Map<String, String> ngayNghiLe;
    public static Map<String, List<String>> gioiHanVuViecDonThu_LVVDT_TTXLVV;

    public static boolean checkSuperAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_superadmin"));
    }

    public static boolean checkAdmin() {
        return Validator.isNotNull(UserContextHolder.getContext().getUser()) && UserContextHolder.getContext().getUser().getDanhTinhDienTu().isTaiKhoanQuanTri();
    }

    public static <T> List<NhatKiSuaDoi> generateNKSD(List<NhatKiSuaDoi> nhatKyOld, T modelOld,
                                                      T modelNew, String maPhienBanNew) {
        List<NhatKiSuaDoi> nhatKiSuaDoiList = Validator.isNull(nhatKyOld) ? new ArrayList<>() : nhatKyOld;
        List<String> removeAttributes = new ArrayList<>();
        removeAttributes.add(DBConstant.TYPE);
        removeAttributes.add(Constant.PRIMKEY);
        removeAttributes.add(Constant.THOIGIANCAPNHAT);
        removeAttributes.add(Constant.THOIGIANTAO);
        removeAttributes.add(Constant.NHATKYSUADOI);
        JSONObject diff_content = CompareObjectUtil.generateContent(modelOld, modelNew, removeAttributes);
        NhatKiSuaDoi nhatKiSuaDoi = new NhatKiSuaDoi();
        nhatKiSuaDoi.setNoiDungSuaDoi(JsonUnflattener.unflatten(diff_content.toString()));
        nhatKiSuaDoi.setMaPhienBan(maPhienBanNew);
        nhatKiSuaDoi.setThoiGian(System.currentTimeMillis());
        UserContext userContext = UserContextHolder.getContext();
        nhatKiSuaDoi.setTacGia(userContext.getUser().getDanhTinhDienTu().getMaDinhDanh());
        nhatKiSuaDoi.setMaSoBanTin(null);
        nhatKiSuaDoiList.add(nhatKiSuaDoi);
        return nhatKiSuaDoiList;
    }


    public static Map<String, ?> convertListToMap(List<?> list) {
        if (Validator.isNull(list) || list.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Object> map = new HashMap<>();
        try {
            for (Object obj : list) {
                Object trangThaiDuLieu = obj.getClass().getField("trangThaiDuLieu").get(obj);
                String key = (String) trangThaiDuLieu.getClass().getField("maMuc").get(trangThaiDuLieu);
                map.put(key, obj);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerException(null, "cannot update data", StringPool.BLANK);
        }

        return map;
    }

    public static Sort sortBuilder(String orderFields, String orderTypes, Class<?> clazz) {

        if (Validator.isNull(orderFields) || orderFields.isEmpty()) {
            return null;
        }

        List<Order> orders = new ArrayList<>();

        String[] fields = StringUtil.split(orderFields);
        String[] types = StringUtil.split(orderTypes);

        List<Boolean> orderLst = new ArrayList<>();

        if (types != null) {
            for (String type : types) {
                if (Validator.isNull(type)) {
                    orderLst.add(true);
                } else {
                    if (type.equalsIgnoreCase("desc")) {
                        orderLst.add(false);
                    } else {
                        orderLst.add(true);
                    }
                }
            }
        }

        if (Validator.isNotNull(orderFields)) {
            int index = 0;
            for (String fieldName : fields) {
                try {
                    fieldName =
                            Character.toLowerCase(fieldName.charAt(0)) + (fieldName.length() > 1 ? fieldName.substring(
                                    1) : "");
                    Field field = clazz.getField(fieldName);
                    org.springframework.data.mongodb.core.mapping.Field anotation = field.getAnnotation(
                            org.springframework.data.mongodb.core.mapping.Field.class);
                    if (anotation != null) {
                        String name = anotation.value();

                        if (Validator.isNull(name)) {
                            continue;
                        }
                        if (orderLst.size() - 1 >= index) {
                            boolean isAsc = orderLst.get(index);
                            Order order = isAsc ? (Order.asc(name)) : (Order.desc(name));
                            orders.add(order);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getStackTrace()[0].getClassName() + "|" + e.getStackTrace()[0].getMethodName() + "|"
                            + "can not get field by name:{}", fieldName);
                    // e.printStackTrace();
                }

                index++;
            }
        }
        return Sort.by(orders);
    }
}
