package com.xmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author
 * @date 2018/1/22
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public static final String EMPTY ="";

    public static final String NULL_STR="null";

    public static final String DEFAULT_PARENT_ID="0";

    public static final String PAGE_NUM = "1";

    public static final String PAGE_SIZE = "10";

    public interface Role{
        /**
         * 普通用户
         */
        int ROLE_CUSTOMER = 0;

        /**
         * 管理员
         */
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc","price_desc");

    }

    public enum ProductStatusEnum{

        ON_SALE(1,"在线");

        private String value;

        private int code;

        ProductStatusEnum(int code,String value){
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
