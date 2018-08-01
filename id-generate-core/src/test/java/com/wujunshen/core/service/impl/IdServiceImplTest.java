package com.wujunshen.core.service.impl;

import com.wujunshen.core.bean.ID;
import com.wujunshen.core.bean.IdMeta;
import com.wujunshen.core.service.IdService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Author:frankwoo(吴峻申) <br>
 * Date:2017/8/30 <br>
 * Time:下午11:04 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class IdServiceImplTest {
  private long id;
  private Set<Long> set;
  private IdService idService;

  @Before
  public void setUp() {
    set = new HashSet<>();
    id = 352608540609069079L;
    idService = new IdServiceImpl(0);
  }

  @After
  public void tearDown() {
    set = null;
    id = 0L;
    idService = null;
  }

  @Test
  public void expId() {
    ID actual = idService.expId(id);
    assertThat(actual.getSequence(), equalTo(23L));
    assertThat(actual.getWorker(), equalTo(92L));
    assertThat(actual.getTimeStamp(), equalTo(84068427231L));
  }

  @Test
  public void transTime() {
    assertThat(
        idService.transTime(84068427231L).getTime(), equalTo(84068427231L + IdMeta.START_TIME));
  }

  @Test
  public void makeId() {
    long actual = idService.makeId(84068427231L, 92L, 23L);

    assertThat(actual, equalTo(id));
  }

  @Test
  public void genId() {
    List<IdService> idServices = new ArrayList<>();
    for (int j = 0; j < 1024; j++) {
      IdService idService = new IdServiceImpl(j);
      idServices.add(idService);
    }

    for (IdService idService : idServices) {
      IdWorkThread idWorkThread = new IdWorkThread(set, idService);
      Thread t = new Thread(idWorkThread);
      t.setDaemon(true);
      t.start();
    }

    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void stressTest() {
    loop(2000000000);
    // loop(2000000000);
    // loop(2000000000);
    // loop(2000000000);
  }

  private void loop(int idNum) {
    long start = System.currentTimeMillis();
    for (int i = 0; i < idNum; i++) {
      long id = idService.genId();
      log.info("{}", id);
    }
    long duration = System.currentTimeMillis() - start;
    log.info("total time:{}ms,speed is:{}/ms", duration, idNum / duration);
  }

  @AllArgsConstructor
  static class IdWorkThread implements Runnable {
    private Set<Long> set;
    private IdService idService;

    @Override
    public void run() {
      while (true) {
        long id = idService.genId();
        log.info("{}", id);
        if (!set.add(id)) {
          log.info("duplicate:{}", id);
        }
      }
    }
  }
}
