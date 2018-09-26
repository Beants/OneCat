package com.pilab.maxu.calculator.calu.calu;

/**
 * Created by andersen on 2018/9/15.
 */

public class calu_history_item {

    private String time;
    private String formula;

    public calu_history_item(String time, String formula) {
        this.time = time;
        this.formula = formula;
    }

    public String getTime() {
        return time;
    }

    public String getFormula() {
        return formula;
    }
}
