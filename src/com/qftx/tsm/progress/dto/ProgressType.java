package com.qftx.tsm.progress.dto;

public enum ProgressType {
	//通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
    //赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
    HIGH_SEA_PROGRESS("HIGH_SEA_PROGRESS") //公海回收待分配
    , LOSING_CUST_RECYLE("LOSING_CUST_RECYLE")//流失客户回收待分配
    , CUST_MOVE("CUST_MOVE");//客户交接
    private String value;
    
    private ProgressType(String value) {
		this.value = value;
	}
    
    public String getValue() {
        return value;
    }
}
