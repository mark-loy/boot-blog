package com.mark.base.enums;

/**
 * 自定义异常枚举
 *
 * @author 木可
 * @version 1.0
 * @date 2020/12/20 12:12
 */
public enum CustomExceptionEnum {
    /**
     * 文章相关
     */
    NO_ARTICLE(20001, "文章不存在"),
    NO_ARTICLE_CONTENT(20001, "文章内容不存在"),

    /**
     * 服务调用相关
     */
    REQUEST_SERVICE_FAIL(20002, "服务调用失败"),

    /**
     * OSS服务相关
     */
    FILE_UPLOAD_ERROR(20003, "文件上传失败"),

    /**
     * 访客相关
     */
    VISITOR_REGISTER_ERROR(20004, "注册参数不完整"),
    VISITOR_LOGIN_ERROR(20004, "登录参数不完整"),
    VISITOR_EXIST(20004, "访客已注册"),
    VISITOR_NO_EXIST(20004, "访客不存在"),
    USER_NO_EXIST(20004, "用户不存在"),
    PASSWORD_ERROR(20004, "密码错误"),
    VISITOR_BAN(20004, "该账户已禁用"),

    /**
     * redis相关
     */
    CODE_NOT_EXIST(20005, "验证码不存在"),
    CODE_NO_CORRECT(20005, "验证码不正确"),
    STATE_NO_MATCH(20005, "状态码不匹配"),

    /*
    *  邮件相关
    */
    MAIL_SEND_FAIL(20006, "邮件发送失败")
    ;


    private final Integer code;
    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    CustomExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
