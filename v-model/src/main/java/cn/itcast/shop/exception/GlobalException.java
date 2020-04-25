package cn.itcast.shop.exception;

import cn.itcast.shop.result.CodeMsg;

public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
