package com.wujunshen.rest.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/24 <br>
 * Time:下午4:29 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Data
public class MakeID {
    @JsonProperty("worker")
    private long machine = -1;
    @JsonProperty("timeStamp")
    private long time = -1;
    @JsonProperty("sequence")
    private long seq = -1;
}
