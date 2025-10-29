import tailwindcss from "@tailwindcss/vite";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    compatibilityDate: "2025-07-15",
    devtools: {
        enabled: true,
        timeline: { enabled: true },
    },
    modules: ["@nuxt/eslint", "@nuxt/ui", "@nuxt/test-utils/module"],
    css: ["~/assets/css/main.css"],
    vite: { plugins: [tailwindcss()] },
    nitro: {
        preset: "cloudflare_module",
        cloudflare: {
            wrangler: {
                name: "reconnected",
                placement: { mode: "smart" },
                workers_dev: true,
                preview_urls: true,
            },
            deployConfig: true,
            nodeCompat: true,
        },
    },
});
