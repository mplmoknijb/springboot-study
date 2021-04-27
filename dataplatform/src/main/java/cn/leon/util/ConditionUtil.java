package cn.leon.util;

import java.util.Objects;

public class ConditionUtil {
  public boolean isNull(Object obj) {
    return Objects.nonNull(obj);
  }
}
