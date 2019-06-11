package com.oliver.bean.vo;

import com.oliver.bean.eo.Constant;
import com.oliver.bean.eo.HttpCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * com.oliver.com.oliver.bean Result
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/28 14:32
 */
@Getter
@Setter
@ToString
public class Result<T> {

    public Result() {
    }

    public Result(HttpCode httpCode, boolean redirect) {
        this.code = httpCode.getCode();
        this.message = httpCode.getMessage();
        if (httpCode == HttpCode.HTTP_OK) {
            this.pageName = (redirect ? "redirect:/" : "").concat(Constant.PAGE_INDEX.getValue());
        } else {
            this.pageName = (redirect ? "redirect:/" : "").concat(Constant.PAGE_LOGIN.getValue());
        }
    }

    public Result(HttpCode httpCode) {
        this(httpCode, false);
    }

    public Result(String pageName, boolean redirect) {
        this.code = HttpCode.HTTP_OK.getCode();
        this.message = HttpCode.HTTP_OK.getMessage();
        if (redirect) {
            this.pageName = "redirect:" + pageName;
        } else {
            this.pageName = pageName;

        }
    }

    public Result(String pageName) {
        this(pageName, false);
    }


    /**
     * 响应码
     */
    private Integer code;
    /**
     * 返回的消息内容
     */
    private String message;
    /**
     * 返回的数据
     */
    private T data;

    /**
     * 返回的页面
     */
    private String pageName;


    /**
     * 成功数据返回
     *
     * @param data 数据
     * @return 返回结果
     */
    public Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = HttpCode.HTTP_OK.getCode();
        result.data = data;
        return result;
    }

    public Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = HttpCode.HTTP_GONE.getCode();
        result.message = message;
        return result;
    }
}
