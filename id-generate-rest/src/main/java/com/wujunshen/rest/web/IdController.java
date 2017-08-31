package com.wujunshen.rest.web;

import com.wujunshen.core.bean.ID;
import com.wujunshen.core.service.IdService;
import com.wujunshen.rest.bean.MakeID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class IdController {
    @Resource
    private IdService idService;

    @GetMapping(value = "/id")
    public long genId() {
        return idService.genId();
    }

    @GetMapping("/id/{id:[0-9]*}")
    public ID explainId(@PathVariable("id") long id) {
        log.info("id is {}", id);
        return idService.expId(id);
    }

    @GetMapping("/time/{time:[0-9]*}")
    public String transTime(@PathVariable("time") long time) {
        log.info("time is {}", time);
        return DateFormatUtils.format(idService.transTime(time), "yyyy-MM-dd HH:mm:ss");
    }


    @PostMapping("/id")
    public long makeId(@RequestBody MakeID makeID) {
        long worker = makeID.getMachine();
        long time = makeID.getTime();
        long sequence = makeID.getSeq();
        log.info("worker is {}", worker);
        log.info("time is {}", time);
        log.info("sequence is {}", sequence);

        if (time == -1 || sequence == -1) {
            throw new IllegalArgumentException("Both time and sequence are required.");
        }

        return worker == -1 ? idService.makeId(time, sequence) : idService.makeId(time, worker, sequence);
    }
}