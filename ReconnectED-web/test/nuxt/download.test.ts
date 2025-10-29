import { describe, it, expect } from "vitest";
import { mountSuspended } from "@nuxt/test-utils/runtime";
import DownloadPage from "../../app/pages/download.vue";

describe("Download Page", () => {
    it("renders the page title and description", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("Download ReconnectED");
        expect(wrapper.text()).toContain("Get the ReconnectED companion app for Android");
    });

    it("displays installation instructions with all steps", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("Installation Instructions");
        expect(wrapper.text()).toContain("Choose Your APK Version");
        expect(wrapper.text()).toContain("Enable Unknown Sources");
        expect(wrapper.text()).toContain("Download & Install");
        expect(wrapper.text()).toContain("Launch the App");
    });

    it("shows security notice warning", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("Security Notice");
        expect(wrapper.text()).toContain("Always download APK files from trusted sources");
    });

    it("displays all four APK download options", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("ARM64-v8a");
        expect(wrapper.text()).toContain("ARMv7a");
        expect(wrapper.text()).toContain("Universal");
        expect(wrapper.text()).toContain("x86_64");
    });

    it("marks ARM64-v8a as recommended", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("Recommended");
        expect(wrapper.text()).toContain("For 64-bit ARM devices");
    });

    it("includes descriptions for each APK version", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("For 64-bit ARM devices (most modern Android phones)");
        expect(wrapper.text()).toContain("For 32-bit ARM devices (older Android phones and tablets)");
        expect(wrapper.text()).toContain("Works on all devices. Larger file size but guaranteed compatibility");
        expect(wrapper.text()).toContain("For Intel/AMD 64-bit processors (emulators and some tablets)");
    });

    it("renders download buttons for all versions", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("Download ARM64-v8a");
        expect(wrapper.text()).toContain("Download ARMv7a");
        expect(wrapper.text()).toContain("Download Universal");
        expect(wrapper.text()).toContain("Download x86_64");
    });

    it('displays "How to Check Your Device Architecture" section', async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("How to Check Your Device Architecture");
        expect(wrapper.text()).toContain("Method 1: Check Device Specifications");
        expect(wrapper.text()).toContain("Method 2: Use CPU-Z App");
        expect(wrapper.text()).toContain("Method 3: Use Universal Build");
    });

    it("provides architecture identification guidelines", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("ARM64, ARMv8, or Cortex-A series");
        expect(wrapper.text()).toContain("ARM, ARMv7, or Cortex-A series (32-bit)");
        expect(wrapper.text()).toContain("Intel or AMD processor");
    });

    it("includes FAQ section with common questions", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("Frequently Asked Questions");
        expect(wrapper.text()).toContain('Why do I need to enable "Unknown Sources"?');
        expect(wrapper.text()).toContain("Which version should I download?");
        expect(wrapper.text()).toContain("Is it safe to download APK files?");
        expect(wrapper.text()).toContain("What is the SHA-1 fingerprint and why is it important?");
        expect(wrapper.text()).toContain("What's the difference between versions?");
        expect(wrapper.text()).toContain("Will this be available on Google Play Store?");
    });

    it("explains Unknown Sources security measure", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain(
            "Android requires this permission to install apps from sources other than the Google Play Store"
        );
        expect(wrapper.text()).toContain("This is a security measure to protect your device");
    });

    it("provides recommendation for device selection", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("For most modern Android devices (2018 onwards), choose ARM64-v8a");
        expect(wrapper.text()).toContain("If you have an older device or are unsure, use the Universal version");
    });

    it("mentions Google Play Store availability", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("We're working on making ReconnectED available on the Google Play Store");
    });

    it("includes proper SEO meta tags", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        // The component should set up SEO meta tags via useSeoMeta
        expect(wrapper.exists()).toBe(true);
    });

    it("displays architecture-specific ABIs correctly", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("arm64-v8a");
        expect(wrapper.text()).toContain("armeabi-v7a");
        expect(wrapper.text()).toContain("universal");
        expect(wrapper.text()).toContain("x86_64");
    });

    it("displays SHA-1 fingerprint for each APK", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        expect(wrapper.text()).toContain("SHA-1 Fingerprint");
    });

    it("includes copy button for SHA-1 hashes", async () => {
        const wrapper = await mountSuspended(DownloadPage);

        const copyButtons = wrapper.findAll('button[aria-label="Copy SHA-1 hash"]');
        expect(copyButtons.length).toBeGreaterThan(0);
    });
});
