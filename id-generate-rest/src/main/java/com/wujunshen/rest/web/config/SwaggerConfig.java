package com.wujunshen.rest.web.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2016-10-27 <br>
 * Time:11:10 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("接口管理")
        .description("ID生成API接口")
        .contact(
            new Contact("frankwoo(吴峻申)", "http://darkranger.iteye.com/", "frank_wjs@hotmail.com"))
        .license("Apache License Version 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .termsOfServiceUrl("https://github.com/wujunshen/vesta-id-generator")
        .version("0.0.1-SNAPSHOT")
        .build();
  }

  @Bean
  public Docket idApi() {
    // exclude-path处理
    List<Predicate<String>> excludePath = new ArrayList<>();
    excludePath.add(PathSelectors.ant("/error"));

    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("id-generator")
        .apiInfo(apiInfo())
        .select()
        .paths(regex("/.*"))
        .paths(Predicates.not(Predicates.or(excludePath)))
        .build();
  }
}
