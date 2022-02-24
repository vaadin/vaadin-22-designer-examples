package com.example.application.views.adminrole;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.RolesAllowed;

/**
 * A Designer generated component for the stub-tag template.
 *
 * Designer will add and remove fields with @Id mappings but does not overwrite
 * or otherwise change this file.
 */
@PageTitle("Admin Role")
@Route(value = "admin-role", layout = MainLayout.class)
@RolesAllowed("admin")
@Tag("admin-role-view")
@JsModule("./views/adminrole/admin-role-view.ts")
public class AdminRoleView extends LitTemplate {

    @Id
    private TextField name;

    @Id
    private Button sayHello;

    public AdminRoleView() {
        addClassName("block");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }
}
