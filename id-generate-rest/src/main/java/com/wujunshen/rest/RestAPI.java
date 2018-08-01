package com.wujunshen.rest;

import com.wujunshen.core.service.IdService;
import com.wujunshen.core.service.impl.IdServiceImpl;
import com.wujunshen.rest.bean.Generate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties(Generate.class)
@Slf4j
public class RestAPI {
  @Resource private Generate generate;

  public static void main(String[] args) {
    log.info("start execute RestApplication....\n");
    SpringApplication.run(RestAPI.class, args);
    log.info("end execute RestApplication....\n");
  }

  @Bean(name = "idService")
  public IdService idService() {
    log.info("worker id is :{}", generate.getWorker());
    return new IdServiceImpl(Long.parseLong(generate.getWorker()));
  }
}
