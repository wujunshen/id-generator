package com.wujunshen.rest.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/24 <br>
 * Time:上午1:13 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@ConfigurationProperties(prefix = "generate")
@Data
public class Generate {
  private String worker;
}
