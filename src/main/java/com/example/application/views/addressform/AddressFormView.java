package com.example.application.views.addressform;

import com.example.application.data.entity.SampleAddress;
import com.example.application.data.service.SampleAddressService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;

/**
 * A Designer generated component for the stub-tag template.
 *
 * Designer will add and remove fields with @Id mappings but does not overwrite
 * or otherwise change this file.
 */
@PageTitle("Address Form")
@Route(value = "address-form", layout = MainLayout.class)
@PermitAll
@Tag("address-form-view")
@JsModule("./views/addressform/address-form-view.ts")
public class AddressFormView extends LitTemplate {

    @Id("streetAddress")
    private TextField street;
    @Id("postalCode")
    private TextField postalCode;
    @Id("city")
    private TextField city;
    @Id("state")
    private ComboBox<String> state;
    @Id("country")
    private ComboBox<String> country;
    @Id("save")
    private Button save;
    @Id("cancel")
    private Button cancel;

    private Binder<SampleAddress> binder = new Binder<>(SampleAddress.class);

    public AddressFormView(SampleAddressService addressService) {
        country.setItems("Country 1", "Country 2", "Country 3");
        state.setItems("State A", "State B", "State C", "State D");

        binder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            addressService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " stored.");
            clearForm();
        });
    }

    private void clearForm() {
        this.binder.setBean(new SampleAddress());
    }

}
