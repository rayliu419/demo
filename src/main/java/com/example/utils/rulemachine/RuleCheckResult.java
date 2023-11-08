package com.example.utils.rulemachine;

import lombok.Data;

@Data
public class RuleCheckResult {

    private String checkInfo;

    private int code;

    boolean pass;

}
