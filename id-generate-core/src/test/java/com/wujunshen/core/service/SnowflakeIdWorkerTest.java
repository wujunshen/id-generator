package com.wujunshen.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author:frankwoo(吴峻申) <br>
 * Date:2017/8/30 <br>
 * Time:下午7:07 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class SnowflakeIdWorkerTest {
  private Set<Long> set;

  @Before
  public void setUp() {
    set = new HashSet<>();
  }

  @After
  public void tearDown() {
    set = null;
  }

  @Test
  public void nextId() {
    List<SnowflakeIdWorker> snowflakeIdWorkers = new ArrayList<>();
    for (int i = 0; i < 32; i++) {
      for (int j = 0; j < 32; j++) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(i, j);
        snowflakeIdWorkers.add(idWorker);
      }
    }

    for (SnowflakeIdWorker snowflakeIdWorker : snowflakeIdWorkers) {
      IdWorkThread idWorkThread = new IdWorkThread(set, snowflakeIdWorker);
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
  public void stressTest() throws Exception {
    loop(50000000);
    loop(50000000);
    loop(50000000);
  }

  private void loop(int idNum) {
    SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    long start = System.currentTimeMillis();
    for (int i = 0; i < idNum; i++) {
      long id = idWorker.nextId();
      // log.info("{}", id);
    }
    long duration = System.currentTimeMillis() - start;
    log.info("total time:{}ms,speed is:{}/ms", duration, idNum / duration);
  }

  @AllArgsConstructor
  static class IdWorkThread implements Runnable {
    private Set<Long> set;
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public void run() {
      while (true) {
        long id = snowflakeIdWorker.nextId();
        log.info("{}", id);
        if (!set.add(id)) {
          log.info("duplicate:{}", id);
        }
      }
    }
  }
}
