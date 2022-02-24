import '@vaadin/vaadin-grid-pro';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('list-view')
export class ListView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<vaadin-grid-pro id="grid" theme="no-border column-borders"> </vaadin-grid-pro>`;
  }
}
