package com.wujunshen.rest.web.controller;

import com.wujunshen.core.bean.ID;
import com.wujunshen.core.service.IdService;
import com.wujunshen.rest.bean.MakeID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "/")
@RestController
@Slf4j
public class IdController {
  @Resource private IdService idService;

  @GetMapping(value = "/id")
  @ApiOperation(value = "生成ID", httpMethod = "GET", notes = "成功返回ID", response = long.class)
  public long genId() {
    return idService.genId();
  }

  @GetMapping("/id/{id:[0-9]*}")
  @ApiOperation(
      value = "对ID进行解析",
      httpMethod = "GET",
      notes = "成功返回解析后的ID（json格式）",
      response = ID.class)
  public ID explainId(@ApiParam(value = "要解析的ID", required = true) @PathVariable("id") long id) {
    log.info("id is {}", id);
    return idService.expId(id);
  }

  @GetMapping("/time/{time:[0-9]*}")
  @ApiOperation(
      value = "对时间戳进行解析",
      httpMethod = "GET",
      notes = "成功返回yyyy-MM-dd HH:mm:ss格式的日期时间",
      response = String.class)
  public String transTime(
      @ApiParam(value = "要解析的时间戳", required = true) @PathVariable("time") long time) {
    log.info("time is {}", time);
    return DateFormatUtils.format(idService.transTime(time), "yyyy-MM-dd HH:mm:ss");
  }

  @PostMapping("/id")
  @ApiOperation(value = "传入相应参数生成ID", httpMethod = "POST", notes = "成功返回ID", response = long.class)
  public long makeId(
      @ApiParam(value = "传入的相应参数（json格式）", required = true) @RequestBody MakeID makeID) {
    long worker = makeID.getMachine();
    long time = makeID.getTime();
    long sequence = makeID.getSeq();
    log.info("worker is {}", worker);
    log.info("time is {}", time);
    log.info("sequence is {}", sequence);

    if (time == -1 || sequence == -1) {
      throw new IllegalArgumentException("Both time and sequence are required.");
    }

    return worker == -1
        ? idService.makeId(time, sequence)
        : idService.makeId(time, worker, sequence);
  }
}
