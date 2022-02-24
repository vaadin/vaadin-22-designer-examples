import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';
import '../../components/leafletmap/leaflet-map';

@customElement('map-view')
export class MapView extends LitElement {
  createRenderRoot() {
    return this;
  }

  render() {
    return html`<leaflet-map class="map" id="theMap"><span></span></leaflet-map>`;
  }
}
