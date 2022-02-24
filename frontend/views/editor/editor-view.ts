import '@vaadin/polymer-legacy-adapter';
import '@vaadin/vaadin-rich-text-editor';
import '@vaadin/vaadin-select';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('editor-view')
export class EditorView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`
      <main class="flex flex-grow h-full">
        <vaadin-rich-text-editor
          id="editor"
          class="border-r border-contrast-10 flex-grow"
          theme="no-border"
        ></vaadin-rich-text-editor>
        <section class="bg-contrast-5 box-border flex flex-col flex-shrink-0 overflow-auto p-l" style="width: 256px;">
          <h2 class="sr-only">Project details</h2>
          <dl class="flex flex-col gap-l mb-s mt-0 text-s">
            <div>
              <dt class="font-medium text-secondary">Owner</dt>
              <dd class="ml-0">My Name</dd>
            </div>
            <div>
              <dt class="font-medium text-secondary">Created</dt>
              <dd class="ml-0">2021-08-14 14:48</dd>
            </div>
            <div>
              <dt class="font-medium text-secondary">Last modified</dt>
              <dd class="ml-0">2021-08-14 14:50</dd>
            </div>
            <div>
              <dt class="font-medium text-secondary">Status</dt>
              <dd class="ml-0" theme="badge">Draft</dd>
            </div>
          </dl>
          <vaadin-select label="Project" value="My Project">
            <template>
              <vaadin-list-box>
                <vaadin-item>My Project</vaadin-item>
                <vaadin-item>Your Project</vaadin-item>
                <vaadin-item>Their Project</vaadin-item>
              </vaadin-list-box>
            </template>
          </vaadin-select>
        </section>
      </main>
    `;
  }
}
