package com.example.chatapplication.ultities;

public class Constant {

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;
    public static final String FORMAT_DATE_UNDERSCORE = "yyyy_MM_dd";
    public static final String COOKIE_NAME = "login_token";
    public static final String SLACK = "/";
    public static final int DEFAULT_SIZE_PAGE = 50;
    public static final String FORMAT_DATE_SAVE_CAPTURE = "yyyy_MM_dd/HH_mm_ss";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String PLUS = "+";
    public static final String BLANK = "";
    public static final String PERCENT = "%";
    public static final int ROLE_OWNER = 1;
    public static final int ROLE_SHARE = 2;


    public static class NameAttribute {

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String LIST_ROLE_ACCESS = "roleAccesses";
        public static final String LIST_FILE_ENABLE = "allFileViewAble";
        public static final String NAME_SEARCH = "nameSearch";
        public static final String ROLE_SEARCH = "roleSearch";
        public static final String FILE_UPLOAD = "myFile";
        public static final String FILE_ID = "fileId";
    }

    public enum EXTENSION_IMAGE {
        JPEG, PNG, GIF, BMP
    }

    public class Status {
        public static final int INACTIVE = 0;
        public static final int ACTIVE = 1;
    }

    public class Number {
        public static final int NEGATIVE_ONE = -1;
        public static final int ZERO = 0;
        public static final int ONE = 1;
    }
}
