package com.wujunshen.core.service.impl;

import com.wujunshen.core.bean.ID;
import com.wujunshen.core.bean.IdMeta;
import com.wujunshen.core.service.IdConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdConverterImpl implements IdConverter {
  public long convert(ID id) {
    long ret = 0;

    ret |= id.getSequence();

    ret |= id.getWorker() << IdMeta.SEQUENCE_BITS;

    ret |= id.getTimeStamp() << IdMeta.TIMESTAMP_LEFT_SHIFT_BITS;

    return ret;
  }

  public ID convert(long id) {
    ID ret = new ID();

    ret.setSequence(id & IdMeta.SEQUENCE_MASK);

    ret.setWorker((id >>> IdMeta.SEQUENCE_BITS) & IdMeta.ID_MASK);

    ret.setTimeStamp((id >>> IdMeta.TIMESTAMP_LEFT_SHIFT_BITS) & IdMeta.TIMESTAMP_MASK);

    return ret;
  }
}
