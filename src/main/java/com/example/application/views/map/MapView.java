package com.example.application.views.map;

import com.example.application.components.leafletmap.LeafletMap;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;

@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
@PermitAll
@Tag("map-view")
@JsModule("./views/map/map-view.ts")
public class MapView extends LitTemplate implements HasSize {

    @Id("theMap")
    private LeafletMap map;

    public MapView() {
        setSizeFull();
        map.setView(55.0, 10.0, 4);
    }
}
