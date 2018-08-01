package com.wujunshen.core.service;

import com.wujunshen.core.bean.ID;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface IdService {
  long genId();

  ID expId(long id);

  Date transTime(long time);

  long makeId(long time, long seq);

  long makeId(long time, long seq, long machine);
}
