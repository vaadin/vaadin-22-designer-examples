import '@vaadin/vaadin-button';
import '@vaadin/vaadin-combo-box';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-text-field';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('address-form-view')
export class AddressFormView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<h3>Address</h3>
      <vaadin-form-layout style="width: 100%;">
        <vaadin-text-field label="Street address" id="streetAddress" colspan="2"></vaadin-text-field>
        <vaadin-text-field label="Postal code" id="postalCode" pattern="\\d*" prevent-invalid-input></vaadin-text-field>
        <vaadin-text-field label="City" id="city"></vaadin-text-field>
        <vaadin-combo-box label="State" id="state"></vaadin-combo-box>
        <vaadin-combo-box label="Country" id="country"></vaadin-combo-box>
      </vaadin-form-layout>
      <vaadin-horizontal-layout
        style="margin-top: var(--lumo-space-m); margin-bottom: var(--lumo-space-l);"
        theme="spacing"
      >
        <vaadin-button theme="primary" id="save"> Save </vaadin-button>
        <vaadin-button id="cancel"> Cancel </vaadin-button>
      </vaadin-horizontal-layout>`;
  }
}
