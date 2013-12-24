package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.UIView;

public interface IUIVewAPI {
	
    public void saveUIView(UIView uiView);
    
    public UIView findUIViewByName(String viewName);
    
    public List<UIView> findAll();
    
}
