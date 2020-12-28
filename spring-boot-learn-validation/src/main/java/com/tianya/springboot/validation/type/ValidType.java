package com.tianya.springboot.validation.type;

import javax.validation.groups.Default;

public interface ValidType {
	
	// 若是不继承 Default，则分组校验只会校验 相应加了 update组检验的属性才能校验，没有添加的，是不会校验的
	interface Update extends Default{}
	
	interface Selete extends Default{}
	
	interface Delete extends Default{}
	
	interface Insert extends Default{}
	
}
