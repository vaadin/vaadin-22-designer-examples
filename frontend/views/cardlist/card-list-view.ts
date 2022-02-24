import '@vaadin/vaadin-grid';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('card-list-view')
export class CardListView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<vaadin-grid id="grid" theme="no-border no-row-borders"> </vaadin-grid>`;
  }
}
