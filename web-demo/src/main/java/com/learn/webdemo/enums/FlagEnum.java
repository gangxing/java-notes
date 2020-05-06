package com.learn.webdemo.enums;

/**
 * @Description 通用状态标识
 * @Author xgangzai
 * @Date 14/07/2018 18:26
 */
public enum FlagEnum {

  YES(1),

  NO(0);

  private Integer code;


  FlagEnum(Integer code) {
    this.code = code;
  }

  public static FlagEnum findByCode(Integer code) {
    for (FlagEnum item : values()) {
      if (item.getCode().equals(code)) {
        return item;
      }
    }
    return null;
  }

  public static boolean valid(Integer code) {
    return code == null || findByCode(code) != null;
  }

  public static boolean notValid(Integer code) {
    return code != null && findByCode(code) == null;
  }

  public static boolean yes(Integer code) {
    return YES.getCode().equals(code);
  }

  public static boolean no(Integer code) {
    return NO.getCode().equals(code);
  }

  public static Integer yes() {
    return YES.getCode();
  }

  public static Integer no() {
    return NO.getCode();
  }

  public static Integer fromBoolean(boolean bool) {
    return bool ? yes() : no();
  }

  public static boolean yes(FlagEnum flag) {
    return YES == flag;
  }

  public static boolean no(FlagEnum flag) {
    return NO == flag;
  }

  public static boolean fromFlag(FlagEnum flag) {
    return yes(flag);
  }

  public static void main(String[] args) {
    System.err.println(FlagEnum.valid(-1));
  }

  public Integer getCode() {
    return code;
  }

}
