package com.service;

import java.util.Map;
import java.util.Set;

public interface AnalyseService {
	
	public Set<String> analysisBeforeQuery(String queryTerm);
	
	public Map<String, String> analysisGroupBy(String queryTerm);

}
