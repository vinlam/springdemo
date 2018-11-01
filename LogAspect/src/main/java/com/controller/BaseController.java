package com.controller;

import com.common.ReturnFormat;

public abstract class BaseController {
	protected String retContent(int status, Object data) {
		return ReturnFormat.retParam(status, data);
	}
}