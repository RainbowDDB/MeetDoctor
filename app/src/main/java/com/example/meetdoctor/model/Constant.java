package com.example.meetdoctor.model;

public class Constant {

    public static final String ADD_OR_EDIT = "add_or_edit";

    public class AskType {
        public static final int NORMAL_ASK = 1; // 正常输入
        public static final int LEVEL_ASK = 2; // 程度类型回答
        public static final int SELECTION_ASK = 3; // boolean类型回答问题
        public static final int RE_ASK = 4; // 申请重开问询页面
    }
}
