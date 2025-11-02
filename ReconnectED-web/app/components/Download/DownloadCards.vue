<script setup lang="ts">
import { motion } from "motion-v";

interface ApkDownload {
    name: string;
    abi: string;
    description: string;
    icon: string;
    recommended?: boolean;
    driveUrl: string;
    sha1: string;
}

interface Props {
    apkDownloads: ApkDownload[];
}

defineProps<Props>();

/**
 * Copy text to clipboard and show toast notification
 * @param abi The ABI name
 * @param text The text to copy
 */
const copyToClipboard = async (abi: string, text: string) => {
    try {
        await navigator.clipboard.writeText(text);
        console.log(`${abi} copied to clipboard:`, text);

        // Show toast notification if available
        try {
            const toast = useToast();
            toast.add({
                title: `${abi} SHA-1 fingerprint copied to clipboard!`,
                icon: "i-lucide-check-circle",
            });
        } catch {
            console.error("Toast notification not available");
        }
    } catch (err) {
        console.error("Failed to copy:", err);
    }
};
</script>

<template>
    <div class="max-w-5xl mx-auto">
        <motion.h2
            class="text-2xl font-bold text-highlighted mb-6 text-center"
            :initial="{ opacity: 0, y: 20 }"
            :while-in-view="{ opacity: 1, y: 0 }"
            :transition="{ duration: 0.6 }"
            :viewport="{ once: true }"
        >
            Choose Your Version
        </motion.h2>

        <div class="grid md:grid-cols-2 gap-6">
            <motion.div
                v-for="(apk, index) in apkDownloads"
                :key="apk.abi"
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: index * 0.1 }"
                :viewport="{ once: true }"
            >
                <UCard :class="{ 'ring-2 ring-primary': apk.recommended }">
                    <template #header>
                        <div class="flex items-start justify-between">
                            <div class="flex items-center gap-3">
                                <UIcon :name="apk.icon" class="size-8 text-primary" />
                                <div>
                                    <h3 class="text-lg font-bold text-highlighted">{{ apk.name }}</h3>
                                    <p class="text-xs text-muted">{{ apk.abi }}</p>
                                </div>
                            </div>
                            <UBadge v-if="apk.recommended" color="success" variant="solid">Recommended</UBadge>
                        </div>
                    </template>

                    <p class="text-sm text-muted mb-4">
                        {{ apk.description }}
                    </p>

                    <div class="bg-elevated p-3 rounded-lg mb-4">
                        <div class="flex items-center justify-between gap-2 mb-1">
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-shield-check" class="size-4 text-success" />
                                <span class="text-xs font-semibold text-muted">SHA-1 Fingerprint</span>
                            </div>
                            <UButton
                                size="xs"
                                variant="ghost"
                                color="neutral"
                                icon="i-lucide-copy"
                                aria-label="Copy SHA-1 hash"
                                class="cursor-pointer"
                                @click="copyToClipboard(apk.name, apk.sha1)"
                            />
                        </div>
                        <p class="text-xs font-mono text-muted break-all">
                            {{ apk.sha1 }}
                        </p>
                    </div>

                    <template #footer>
                        <UButton
                            :href="apk.driveUrl"
                            target="_blank"
                            external
                            block
                            size="lg"
                            icon="i-lucide-download"
                            :color="apk.recommended ? 'primary' : 'neutral'"
                            :variant="apk.recommended ? 'solid' : 'outline'"
                        >
                            Download {{ apk.name }}
                        </UButton>
                    </template>
                </UCard>
            </motion.div>
        </div>
    </div>
</template>
