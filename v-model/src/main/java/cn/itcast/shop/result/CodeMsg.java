package cn.itcast.shop.result;

public enum CodeMsg {

    //通用异常
    SERVER_ERROR(500000,"服务端异常"),

    //用户登录模块
    USERNAME_NOT_EMPTY(500100,"用户名不能为空"),
    PASSWORD_NOT_EMPTY(500101,"密码不能为空"),
    PASSWORD_ERROR(500102,"用户名或密码错误"),
    USER_NOT_EXIST(500103,"用户不存在"),
    USER_LOGIN_ERROR(500104,"用户登录异常"),
    USER_NOT_VERIFY(500105,"账户还没激活"),

    //用户注册模块
    USER_PARAMER_EMPTY(500200,"用户注册参数不能为空"),
    USERNAME_EXIST(500201,"用户名已存在"),
    EMAIL_EXIST(500202,"邮箱已被注册"),
    EMAIL_NOT_EMPTY(500203,"邮箱不能为空"),
    EMAIL_ENABLE_FAIL(500204,"用户邮箱激活失败"),

    //验证码模块
    IMAGE_CODE_NOT_EMPTY(500300,"验证码不能为空"),
    IMAGE_CODE_NOT_EXIST(500301,"验证码不存在"),
    IMAGE_CODE_ERROR(500302,"验证码错误"),
    IMAGE_CODE_EXPRIED(500303,"验证码已过期"),

    //商品模块
    CATEGORY_NOT_EMPTY(500400,"商品类别为空"),

    ;

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    CodeMsg(int code,String msg) {
        this.msg = msg;
        this.code = code;
    }
}
