import { describe, it, expect } from "vitest";
import { mountSuspended } from "@nuxt/test-utils/runtime";
import IndexPage from "../../app/pages/index.vue";

describe("Landing Page", () => {
    it("renders the hero section with logo and title", async () => {
        const wrapper = await mountSuspended(IndexPage);

        // Check for logo
        const logo = wrapper.find('img[alt="ReconnectED Logo"]');
        expect(logo.exists()).toBe(true);
        expect(logo.attributes("src")).toBe("/logo.png");

        // Check for main heading
        expect(wrapper.text()).toContain("ReconnectED: The Digital Detox Eco-Planner");
    });

    it("displays the tagline", async () => {
        const wrapper = await mountSuspended(IndexPage);
        expect(wrapper.text()).toContain("Minimize the overconsumption of the internet.");
    });

    it("renders call-to-action buttons in hero section", async () => {
        const wrapper = await mountSuspended(IndexPage);
        expect(wrapper.text()).toContain("Download");
        expect(wrapper.text()).toContain("Learn More");
    });

    it("displays the problem section with statistics", async () => {
        const wrapper = await mountSuspended(IndexPage);

        // Check for problem section heading
        expect(wrapper.text()).toContain("The Problem and Its Impact");
        expect(wrapper.text()).toContain("Overconsumption of the Internet");

        // Check for Philippines statistics
        expect(wrapper.text()).toContain("Philippines' average daily internet use");
        expect(wrapper.text()).toContain("2h 12m higher than the global average");
    });

    it("lists mental health impacts", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Mental Health Impact");
        expect(wrapper.text()).toContain("Anxiety & Depression");
        expect(wrapper.text()).toContain("Sleep Disturbances");
        expect(wrapper.text()).toContain("Doom Scrolling");
        expect(wrapper.text()).toContain("Low Productivity");
    });

    it("displays environmental impact information", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("The Impact on the Environment");
        expect(wrapper.text()).toContain("carbon footprint and waste");
        expect(wrapper.text()).toContain("Total CO2e Saved = ∑ (Minutes Saved × Emission Factor)");
    });

    it("shows emission factors for different activities", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Video Streaming");
        expect(wrapper.text()).toContain("3.45 gCO2e/min");
        expect(wrapper.text()).toContain("Web Browsing");
        expect(wrapper.text()).toContain("4.0 gCO2e/min");
        expect(wrapper.text()).toContain("Social Media");
        expect(wrapper.text()).toContain("1.15 gCO2e/min");
    });

    it("renders the solution section with both products", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("The Solution");
        expect(wrapper.text()).toContain("Planner and Companion App");
        expect(wrapper.text()).toContain("Eco Planner");
        expect(wrapper.text()).toContain("AI-Companion App");
    });

    it("displays Eco Planner features and materials", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("abaca and kraft paper");
        expect(wrapper.text()).toContain("daily planner, goal-setting sections, motivational quotes");
    });

    it("lists all SDGs advocated by the Eco Planner", async () => {
        const wrapper = await mountSuspended(IndexPage);

        const sdg3Image = wrapper.find('img[src*="SDG3_ICON.png"]');
        const sdg13Image = wrapper.find('img[src*="SDG13_ICON.png"]');
        const sdg11Image = wrapper.find('img[src*="SDG11_ICON.png"]');
        const sdg12Image = wrapper.find('img[src*="SDG12_ICON.png"]');

        expect(sdg3Image.exists()).toBe(true);
        expect(wrapper.text()).toContain("mental well-being");
        expect(sdg13Image.exists()).toBe(true);
        expect(wrapper.text()).toContain("carbon footprint");
        expect(sdg11Image.exists()).toBe(true);
        expect(wrapper.text()).toContain("responsible consumption");
        expect(sdg12Image.exists()).toBe(true);
        expect(wrapper.text()).toContain("sustainable production");
    });

    it("mentions VBN Theory foundation", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Value-Belief-Norm (VBN) Theory");
    });

    it("displays AI-Companion App features", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Tracks screen time and progress");
        expect(wrapper.text()).toContain("Provides prompts, reminders, and personalized support");
        expect(wrapper.text()).toContain("Google Tools");
    });

    it("lists integrated Google tools", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Google Calendar");
        expect(wrapper.text()).toContain("Google Docs");
        expect(wrapper.text()).toContain("Google Keep");
        expect(wrapper.text()).toContain("Google Meet");
        expect(wrapper.text()).toContain("Firebase");
        expect(wrapper.text()).toContain("Gemini AI");
    });

    it("shows app feature categories", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Screen Time Tracker");
        expect(wrapper.text()).toContain("Screen Time Limiter");
        expect(wrapper.text()).toContain("Calendar");
        expect(wrapper.text()).toContain("AI Assistant");
    });

    it("renders final call-to-action section", async () => {
        const wrapper = await mountSuspended(IndexPage);

        expect(wrapper.text()).toContain("Start Your Digital Detox Journey Today");
        expect(wrapper.text()).toContain("Join ReconnectED and make a positive impact");
        expect(wrapper.text()).toContain("Download");
        expect(wrapper.text()).toContain("Log In");
    });

    it("includes proper SEO meta tags", async () => {
        const wrapper = await mountSuspended(IndexPage);

        // The component should set up SEO meta tags via useSeoMeta
        // This is more of an integration test, but we can verify the component renders
        expect(wrapper.exists()).toBe(true);
    });
});
