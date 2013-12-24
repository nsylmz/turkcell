package com.ns.deneme.neo4j.api;

import com.ns.deneme.neo4j.domain.View;

public interface IViewAPI {
	
    public void saveView(View view);
    
    public View findViewByName(String viewName);
}
