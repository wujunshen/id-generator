package com.wujunshen.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ID implements Serializable {
  private long timeStamp;
  private long worker;
  private long sequence;
}
