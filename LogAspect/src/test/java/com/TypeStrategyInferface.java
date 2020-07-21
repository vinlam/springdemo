package com;

import org.springframework.stereotype.Service;

@Service
public interface TypeStrategyInferface {
	String TypeRes(String[] types) throws Exception;
}
