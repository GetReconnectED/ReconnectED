<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <motion.div
        :initial="{ opacity: 0, y: 30 }"
        :while-in-view="{ opacity: 1, y: 0 }"
        :transition="{ duration: 0.6 }"
        :viewport="{ once: true }"
    >
        <UCard class="mb-12 max-w-4xl mx-auto">
            <template #header>
                <div class="flex items-center gap-3">
                    <UIcon name="i-lucide-info" class="size-6 text-primary" />
                    <h2 class="text-xl font-bold">Installation Instructions</h2>
                </div>
            </template>

            <div class="space-y-4">
                <motion.div
                    :initial="{ opacity: 0, x: -30 }"
                    :while-in-view="{ opacity: 1, x: 0 }"
                    :transition="{ duration: 0.6, delay: 0.1 }"
                    :viewport="{ once: true }"
                >
                    <div class="flex items-start gap-3">
                        <UBadge color="primary" variant="solid" size="lg" class="mt-1">1</UBadge>
                        <div>
                            <p class="font-semibold mb-1">Choose Your APK Version</p>
                            <p class="text-sm text-muted">
                                Select the APK that matches your device's architecture. If unsure, choose the Universal
                                version.
                            </p>
                        </div>
                    </div>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, x: -30 }"
                    :while-in-view="{ opacity: 1, x: 0 }"
                    :transition="{ duration: 0.6, delay: 0.2 }"
                    :viewport="{ once: true }"
                >
                    <div class="flex items-start gap-3">
                        <UBadge color="primary" variant="solid" size="lg" class="mt-1">2</UBadge>
                        <div>
                            <p class="font-semibold mb-1">Enable Unknown Sources</p>
                            <p class="text-sm text-muted">
                                Go to Settings → Security → Enable "Install from Unknown Sources" or "Install Unknown
                                Apps" for your browser.
                            </p>
                        </div>
                    </div>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, x: -30 }"
                    :while-in-view="{ opacity: 1, x: 0 }"
                    :transition="{ duration: 0.6, delay: 0.3 }"
                    :viewport="{ once: true }"
                >
                    <div class="flex items-start gap-3">
                        <UBadge color="primary" variant="solid" size="lg" class="mt-1">3</UBadge>
                        <div>
                            <p class="font-semibold mb-1">Download & Install</p>
                            <p class="text-sm text-muted">
                                Click the download button for your chosen version. Once downloaded, open the APK file
                                and follow the installation prompts.
                            </p>
                        </div>
                    </div>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, x: -30 }"
                    :while-in-view="{ opacity: 1, x: 0 }"
                    :transition="{ duration: 0.6, delay: 0.4 }"
                    :viewport="{ once: true }"
                >
                    <div class="flex items-start gap-3">
                        <UBadge color="primary" variant="solid" size="lg" class="mt-1">4</UBadge>
                        <div>
                            <p class="font-semibold mb-1">Launch the App</p>
                            <p class="text-sm text-muted">
                                Open ReconnectED from your app drawer and start your digital detox journey!
                            </p>
                        </div>
                    </div>
                </motion.div>
            </div>

            <template #footer>
                <motion.div
                    :initial="{ opacity: 0, scale: 0.95 }"
                    :while-in-view="{ opacity: 1, scale: 1 }"
                    :transition="{ duration: 0.6, delay: 0.5 }"
                    :viewport="{ once: true }"
                >
                    <div class="bg-warning/10 p-3 rounded-lg">
                        <div class="flex items-start gap-2">
                            <UIcon name="i-lucide-alert-triangle" class="size-5 text-warning mt-0.5" />
                            <div>
                                <p class="text-sm font-semibold text-warning mb-1">Security Notice</p>
                                <p class="text-xs text-muted">
                                    Always download APK files from trusted sources. Make sure you're downloading from
                                    our official Google Drive link.
                                </p>
                            </div>
                        </div>
                    </div>
                </motion.div>
            </template>
        </UCard>
    </motion.div>
</template>
