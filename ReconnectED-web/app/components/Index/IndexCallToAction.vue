<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <UContainer class="py-16 sm:py-24 bg-primary/5">
        <div class="text-center max-w-3xl mx-auto">
            <motion.h2
                class="text-3xl sm:text-4xl font-bold text-highlighted mb-6"
                :initial="{ opacity: 0, scale: 0.9 }"
                :while-in-view="{ opacity: 1, scale: 1 }"
                :transition="{ duration: 0.6, type: 'spring', bounce: 0.3 }"
                :viewport="{ once: true }"
            >
                Start Your Digital Detox Journey Today
            </motion.h2>
            <motion.p
                class="text-lg text-muted mb-8"
                :initial="{ opacity: 0, y: 20 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.1 }"
                :viewport="{ once: true }"
            >
                Join ReconnectED and make a positive impact on both your well-being and the environment.
            </motion.p>
            <motion.div
                class="flex flex-wrap gap-4 justify-center"
                :initial="{ opacity: 0, y: 20 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.2 }"
                :viewport="{ once: true }"
            >
                <UButton size="xl" icon="i-lucide-download" label="Download" to="/download" />
                <UButton
                    size="xl"
                    variant="outline"
                    color="neutral"
                    icon="i-lucide-log-in"
                    label="Log In"
                    to="/login"
                />
            </motion.div>
        </div>
    </UContainer>
</template>
