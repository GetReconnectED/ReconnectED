<script setup lang="ts">
import type { NavigationMenuItem } from "@nuxt/ui";
import { motion } from "motion-v";

const route = useRoute();
const items = computed<NavigationMenuItem[]>(() => [
    {
        label: "Home",
        to: "/",
        active: route.path === "/",
    },
    {
        label: "Study",
        to: "/study",
        active: route.path.startsWith("/study"),
    },
    {
        label: "Download",
        to: "/download",
        active: route.path.startsWith("/download"),
    },
    {
        label: "Dashboard",
        to: "/login",
        active: route.path.startsWith("/login"),
    },
]);
</script>

<template>
    <motion.div :initial="{ opacity: 0, y: -20 }" :animate="{ opacity: 1, y: 0 }" :transition="{ duration: 0.6 }">
        <UHeader>
            <template #title>
                <div class="flex items-center gap-2">
                    <img src="/logo.png" alt="ReconnectED Logo" class="h-12 w-auto" />
                    <p>ReconnectED</p>
                </div>
            </template>
            <UNavigationMenu :items="items" />
            <template #right>
                <UColorModeButton />

                <UTooltip text="Open on GitHub" :kbds="['meta', 'G']">
                    <UButton
                        color="neutral"
                        variant="ghost"
                        to="https://github.com/GetReconnectED/ReconnectED"
                        target="_blank"
                        icon="i-simple-icons-github"
                        aria-label="GitHub"
                    />
                </UTooltip>
            </template>
        </UHeader>
    </motion.div>
</template>
