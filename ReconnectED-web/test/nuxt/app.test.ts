import { describe, it, expect } from "vitest";
import { mountSuspended } from "@nuxt/test-utils/runtime";
import App from "../../app/app.vue";

describe("App Component", () => {
    it("should mount the app component", async () => {
        const component = await mountSuspended(App);
        expect(component.html()).toBeTruthy();
    });
});
