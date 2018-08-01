package com.wujunshen.rest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujunshen.core.bean.ID;
import com.wujunshen.rest.RestAPI;
import com.wujunshen.rest.bean.MakeID;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/24 <br>
 * Time:下午3:20 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestAPI.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Slf4j
public class IdControllerTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired private TestRestTemplate template;

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  @Test
  public void genId() {
    long actual = template.getForObject("/id", long.class, new HashMap<>());

    log.info("actual is: {}", actual);
  }

  @Test
  public void explainId() {
    Map<String, String> multiValueMap = new HashMap<>();
    multiValueMap.put("id", "352834416118059008"); // 传值，但要在url上配置相应的参数

    ID actual = template.getForObject("/id/{id}", ID.class, multiValueMap);

    assertThat(actual.getWorker(), equalTo(1021L));
    assertThat(actual.getSequence(), equalTo(0L));
    assertThat(actual.getTimeStamp(), equalTo(84122280148L));
  }

  @Test
  public void transTime() {
    Map<String, String> multiValueMap = new HashMap<>();
    multiValueMap.put("time", "84122280148"); // 传值，但要在url上配置相应的参数

    String actual = template.getForObject("/time/{time}", String.class, multiValueMap);

    assertThat(actual, equalTo("2017-08-31 15:18:00"));
  }

  @Test
  public void makeId() throws Exception {
    String requestBody =
        "{\n"
            + "  \"worker\": 1021,\n"
            + "  \"timeStamp\": 84122280148,\n"
            + "  \"sequence\": 0\n"
            + "}";

    long actual =
        template.postForObject(
            "/id", OBJECT_MAPPER.readValue(requestBody, MakeID.class), long.class);

    log.info("actual is:{}", actual);
    assertThat(actual, equalTo(352834416118059008L));
  }
}
