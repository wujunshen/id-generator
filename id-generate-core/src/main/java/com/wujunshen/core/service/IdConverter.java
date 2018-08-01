package com.wujunshen.core.service;

import com.wujunshen.core.bean.ID;

public interface IdConverter {
  long convert(ID id);

  ID convert(long id);
}
