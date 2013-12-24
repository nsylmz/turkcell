package com.ns.deneme.neo4j.api;

import java.util.List;

import com.ns.deneme.neo4j.domain.UIComponent;

public interface IUIComponentAPI {
	
    public void saveUIComponent(UIComponent uiComponent);
    
    public UIComponent findUIComponentByName(String uiComponentName);
    
    public List<UIComponent> findAll();
}
