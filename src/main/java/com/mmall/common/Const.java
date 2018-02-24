package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * create by YuWen
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public interface ProductListOrderBy {
        //set的contains的时间复杂度是  O(1)  List的是O(n)
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc", "price_desc");
    }

    public interface Cart {
        int CHECKED = 1;    //即购物车中选中状态
        int UN_CHECKED = 0;     //即购物车未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role {
        int ROLE_CUSTOMER = 0;   //普通用户
        int ROLE_ADMIN = 1;    //管理员
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");

        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStautsEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSED(60, "订单关闭");

        private String value;
        private int code;

        OrderStautsEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }
        public String getValue() {
            return value;
        }
        public int getCode() {
            return code;
        }
        public static OrderStautsEnum codeOf(int code) {
            for (OrderStautsEnum orderStautsEnum : values()) {
                if (orderStautsEnum.getCode() == code) {
                    return orderStautsEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public interface AlipayCallBack {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum payPlatformEnum {
        ALIPAY(1, "支付宝");
        private String value;
        private int code;

        payPlatformEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");
        private String value;
        private int code;

        PaymentTypeEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }

    }
}
