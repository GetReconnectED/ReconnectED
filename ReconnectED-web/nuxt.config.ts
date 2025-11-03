import tailwindcss from "@tailwindcss/vite";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    compatibilityDate: "2025-07-15",
    devtools: {
        enabled: true,
        timeline: { enabled: true },
    },
    modules: ["@nuxt/eslint", "@nuxt/ui", "@nuxt/test-utils/module", "motion-v/nuxt"],
    css: ["~/assets/css/main.css"],
    vite: { plugins: [tailwindcss()] },
    runtimeConfig: {
        public: {
            firebaseApiKey: process.env.NUXT_PUBLIC_FIREBASE_API_KEY,
            firebaseAuthDomain: process.env.NUXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
            firebaseProjectId: process.env.NUXT_PUBLIC_FIREBASE_PROJECT_ID,
            firebaseStorageBucket: process.env.NUXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
            firebaseMessagingSenderId: process.env.NUXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
            firebaseAppId: process.env.NUXT_PUBLIC_FIREBASE_APP_ID,
        },
    },
    nitro: {
        preset: "cloudflare_module",
        cloudflare: {
            wrangler: {
                name: "reconnected",
                routes: [
                    {
                        pattern: "reconnected.tech",
                        custom_domain: true,
                    },
                ],
                placement: { mode: "smart" },
                workers_dev: true,
                preview_urls: true,
                observability: {
                    logs: {
                        enabled: true,
                        head_sampling_rate: 1,
                        invocation_logs: true,
                        persist: true,
                    },
                },
            },
            deployConfig: true,
            nodeCompat: true,
        },
    },
});
