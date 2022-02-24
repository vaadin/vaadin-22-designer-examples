package com.example.application.views.masterdetailproduct;

import com.example.application.data.entity.SampleFoodProduct;
import com.example.application.data.service.SampleFoodProductService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import elemental.json.Json;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.util.UriUtils;

@PageTitle("Master Detail Product")
@Route(value = "master-detail-product/:sampleFoodProductID?/:action?(edit)", layout = MainLayout.class)
@PermitAll
@Tag("master-detail-product-view")
@JsModule("./views/masterdetailproduct/master-detail-product-view.ts")
public class MasterDetailProductView extends LitTemplate implements HasStyle, BeforeEnterObserver {

    private final String SAMPLEFOODPRODUCT_ID = "sampleFoodProductID";
    private final String SAMPLEFOODPRODUCT_EDIT_ROUTE_TEMPLATE = "master-detail-product/%s/edit";

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    // The design can be easily edited by using Vaadin Designer
    // (vaadin.com/designer)

    @Id
    private Grid<SampleFoodProduct> grid;

    @Id
    private Upload image;
    @Id
    private Image imagePreview;
    @Id
    private TextField name;
    @Id
    private TextField eanCode;

    @Id
    private Button cancel;
    @Id
    private Button save;

    private BeanValidationBinder<SampleFoodProduct> binder;

    private SampleFoodProduct sampleFoodProduct;

    private SampleFoodProductService sampleFoodProductService;

    public MasterDetailProductView(@Autowired SampleFoodProductService sampleFoodProductService) {
        this.sampleFoodProductService = sampleFoodProductService;
        addClassNames("master-detail-product-view", "flex", "flex-col", "h-full");
        LitRenderer<SampleFoodProduct> imageRenderer = LitRenderer.<SampleFoodProduct>of(
                "<span style='border-radius: 50%; overflow: hidden; display: flex; align-items: center; justify-content: center; width: 64px; height: 64px'><img style='max-width: 100%' src=${item.image} /></span>")
                .withProperty("image", SampleFoodProduct::getImage);
        grid.addColumn(imageRenderer).setHeader("Image").setWidth("96px").setFlexGrow(0);

        grid.addColumn(SampleFoodProduct::getName).setHeader("Name").setAutoWidth(true);
        grid.addColumn(SampleFoodProduct::getEanCode).setHeader("Ean Code").setAutoWidth(true);
        grid.setItems(query -> sampleFoodProductService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent()
                        .navigate(String.format(SAMPLEFOODPRODUCT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailProductView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(SampleFoodProduct.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        attachImageUpload(image, imagePreview);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.sampleFoodProduct == null) {
                    this.sampleFoodProduct = new SampleFoodProduct();
                }
                binder.writeBean(this.sampleFoodProduct);
                this.sampleFoodProduct.setImage(imagePreview.getSrc());

                sampleFoodProductService.update(this.sampleFoodProduct);
                clearForm();
                refreshGrid();
                Notification.show("SampleFoodProduct details stored.");
                UI.getCurrent().navigate(MasterDetailProductView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the sampleFoodProduct details.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> sampleFoodProductId = event.getRouteParameters().get(SAMPLEFOODPRODUCT_ID).map(UUID::fromString);
        if (sampleFoodProductId.isPresent()) {
            Optional<SampleFoodProduct> sampleFoodProductFromBackend = sampleFoodProductService
                    .get(sampleFoodProductId.get());
            if (sampleFoodProductFromBackend.isPresent()) {
                populateForm(sampleFoodProductFromBackend.get());
            } else {
                Notification.show(String.format("The requested sampleFoodProduct was not found, ID = %s",
                        sampleFoodProductId.get()), 3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailProductView.class);
            }
        }
    }

    private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");
        upload.setReceiver((fileName, mimeType) -> {
            return uploadBuffer;
        });
        upload.addSucceededListener(e -> {
            String mimeType = e.getMIMEType();
            String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
            String dataUrl = "data:" + mimeType + ";base64,"
                    + UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
            upload.getElement().setPropertyJson("files", Json.createArray());
            preview.setSrc(dataUrl);
            uploadBuffer.reset();
        });
        preview.setVisible(false);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(SampleFoodProduct value) {
        this.sampleFoodProduct = value;
        binder.readBean(this.sampleFoodProduct);
        this.imagePreview.setVisible(value != null);
        if (value == null) {
            this.imagePreview.setSrc("");
        } else {
            this.imagePreview.setSrc(value.getImage());
        }

    }
}
