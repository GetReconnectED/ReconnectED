<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <UContainer class="py-16 sm:py-24">
        <div class="text-center">
            <motion.img
                src="/logo.png"
                alt="ReconnectED Logo"
                class="mx-auto h-48 w-auto mb-8"
                :initial="{ opacity: 0, scale: 0.8 }"
                :animate="{ opacity: 1, scale: 1 }"
                :transition="{ duration: 0.6, type: 'spring', bounce: 0.4 }"
            />
            <motion.h1
                class="text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight text-highlighted mb-6"
                :initial="{ opacity: 0, y: 20 }"
                :animate="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.2 }"
            >
                ReconnectED: The Digital Detox Eco-Planner
            </motion.h1>
            <motion.p
                class="text-lg sm:text-xl text-muted max-w-3xl mx-auto mb-8"
                :initial="{ opacity: 0, y: 20 }"
                :animate="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.4 }"
            >
                Minimize the overconsumption of the internet.
            </motion.p>
            <motion.div
                class="flex flex-wrap gap-4 justify-center"
                :initial="{ opacity: 0, y: 20 }"
                :animate="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.6 }"
            >
                <UButton size="lg" icon="i-lucide-download" label="Download" to="/download" />
                <UButton
                    size="lg"
                    variant="outline"
                    color="neutral"
                    icon="i-lucide-info"
                    label="Learn More"
                    to="#info"
                />
            </motion.div>
        </div>
    </UContainer>
</template>
