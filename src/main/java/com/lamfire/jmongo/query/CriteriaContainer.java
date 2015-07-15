package com.lamfire.jmongo.query;

public interface CriteriaContainer extends Criteria {
	void add(Criteria... criteria);
	
	CriteriaContainer and(Criteria... criteria);
	CriteriaContainer or(Criteria... criteria);
	
	FieldEnd<? extends CriteriaContainer> criteria(String field);
}
