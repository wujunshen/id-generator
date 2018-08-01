package com.wujunshen.core.service.impl;

import com.wujunshen.core.bean.ID;
import com.wujunshen.core.service.IdConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Author:frankwoo(吴峻申) <br>
 * Date:2017/8/31 <br>
 * Time:上午12:17 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class IdConverterImplTest {
  private long id;

  @Before
  public void setUp() {
    id = 352608540609069079L;
  }

  @After
  public void tearDown() {
    id = 0L;
  }

  @Test
  public void convert() {
    IdConverter idConverter = new IdConverterImpl();

    ID actual = idConverter.convert(id);
    assertThat(actual.getSequence(), equalTo(23L));
    assertThat(actual.getWorker(), equalTo(92L));
    assertThat(actual.getTimeStamp(), equalTo(84068427231L));

    assertThat(idConverter.convert(actual), equalTo(id));
  }
}
