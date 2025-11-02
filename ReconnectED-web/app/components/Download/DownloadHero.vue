<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <div class="text-center max-w-3xl mx-auto mb-12">
        <!-- <UBadge color="primary" variant="subtle" size="lg" class="mb-4"> Android App </UBadge> -->
        <motion.h1
            class="text-4xl sm:text-5xl font-bold tracking-tight text-highlighted mb-6"
            :initial="{ opacity: 0, y: 20 }"
            :animate="{ opacity: 1, y: 0 }"
            :transition="{ duration: 0.6 }"
        >
            Download ReconnectED
        </motion.h1>
        <motion.p
            class="text-lg text-muted mb-8"
            :initial="{ opacity: 0, y: 20 }"
            :animate="{ opacity: 1, y: 0 }"
            :transition="{ duration: 0.6, delay: 0.1 }"
        >
            Get the ReconnectED companion app for Android. Choose the right version for your device architecture below.
        </motion.p>
        <motion.div
            :initial="{ opacity: 0, scale: 0.9 }"
            :animate="{ opacity: 1, scale: 1 }"
            :transition="{ duration: 0.6, delay: 0.2 }"
        >
            <UBadge color="info">
                Note: The Eco-planner is a must-have since the Android application is only a companion for it
            </UBadge>
        </motion.div>
    </div>
</template>
