<script setup lang="ts">
import type { NavigationMenuItem } from "@nuxt/ui";

const route = useRoute();
const items = computed<NavigationMenuItem[]>(() => [
    {
        label: "Download",
        to: "/download",
        active: route.path.startsWith("/download"),
    },
    {
        label: "Log In",
        to: "/login",
        active: route.path.startsWith("/login"),
    },
]);
</script>
<template>
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
</template>
