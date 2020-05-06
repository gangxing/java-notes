package com.learn.webdemo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

/**
 * @Description TODO
 * @Author xgangzai
 * @Date 03/07/2018 01:06
 */
public class FieldColumnConverts {

  private static final char UNDERLINE = '_';

  @SuppressWarnings("unchecked")
  public static Sort convert(Sort sort) {
    if (sort != null) {
      Iterator<Sort.Order> iterator = sort.iterator();
      List<Sort.Order> orders = new ArrayList();
      while (iterator.hasNext()) {
        Sort.Order order = iterator.next();
        Sort.Order newOrder = new Sort.Order(order.getDirection(),
            camelToUnderline(order.getProperty()));
        orders.add(newOrder);
      }
      return Sort.by(orders);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static String camelToUnderline(String param) {
    if (StringUtils.isEmpty(param)) {
      return param;
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(UNDERLINE);
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 下划线格式字符串转换为驼峰格式字符串
   *
   * @param param
   * @return
   */
  @SuppressWarnings("unchecked")
  public static String underlineToCamel(String param) {
    if (StringUtils.isEmpty(param)) {
      return param;
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (c == UNDERLINE) {
        if (++i < len) {
          sb.append(Character.toUpperCase(param.charAt(i)));
        }
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
}
