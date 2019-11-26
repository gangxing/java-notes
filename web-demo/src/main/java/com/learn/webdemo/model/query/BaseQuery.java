package com.learn.webdemo.model.query;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author xgangzai
 * @Date 03/07/2018 00:18
 */
@Getter
@Setter
public class BaseQuery {

    private List<Long> ids;

    private Date createdAtMinInclude;

    private Date createdAtMaxInclude;

    private Date updatedAtMinInclude;

    private Date updatedAtMaxInclude;

    private Date createdAtMinExclude;

    private Date createdAtMaxExclude;

    private Date updatedAtMinExclude;

    private Date updatedAtMaxExclude;



    public String addPercent(String s) {
        return addPercent(s, true, true);
    }

    public String addPercentLeft(String s) {
        return addPercent(s, true, false);
    }

    public String addPercentRight(String s) {
        return addPercent(s, false, true);
    }

    private String addPercent(String s, boolean left, boolean right) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (left) {
            s = "%" + s;
        }
        if (right) {
            s += "%";
        }
        return s;
    }


    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
