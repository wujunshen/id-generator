package com.wujunshen.rest;

import com.wujunshen.core.service.IdService;
import com.wujunshen.core.service.impl.IdServiceImpl;
import com.wujunshen.rest.bean.Vesta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties(Vesta.class)
@Slf4j
public class RestApplication {
    @Resource
    private Vesta vesta;

    public static void main(String[] args) {
        log.info("start execute RestApplication....\n");
        SpringApplication.run(RestApplication.class, args);
        log.info("end execute RestApplication....\n");
    }

    @Bean(name = "idService")
    public IdService idService() {
        log.info("worker id is :{}", vesta.getMachine());
        return new IdServiceImpl(Long.parseLong(vesta.getMachine()));
    }
}
