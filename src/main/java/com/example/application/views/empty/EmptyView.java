package com.example.application.views.empty;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import javax.annotation.security.PermitAll;

@PageTitle("Empty")
@Route(value = "empty", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Tag("empty-view")
@JsModule("./views/empty/empty-view.ts")
public class EmptyView extends LitTemplate implements HasStyle {

    public EmptyView() {
        addClassNames("flex", "flex-col", "h-full");
    }
}
