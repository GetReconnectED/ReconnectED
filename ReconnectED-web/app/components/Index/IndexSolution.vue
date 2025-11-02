<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <UContainer class="py-16 sm:py-24">
        <div class="text-center mb-12">
            <motion.h2
                class="text-3xl sm:text-4xl font-bold text-highlighted mb-4"
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6 }"
                :viewport="{ once: true }"
            >
                The Solution
            </motion.h2>
            <motion.p
                class="text-xl text-muted"
                :initial="{ opacity: 0, y: 20 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.1 }"
                :viewport="{ once: true }"
            >
                Planner and Companion App
            </motion.p>
        </div>

        <div class="grid lg:grid-cols-2 gap-12">
            <motion.div
                :initial="{ opacity: 0, x: -50 }"
                :while-in-view="{ opacity: 1, x: 0 }"
                :transition="{ duration: 0.6, delay: 0.2 }"
                :viewport="{ once: true }"
            >
                <IndexSolutionEcoPlanner />
            </motion.div>
            <motion.div
                :initial="{ opacity: 0, x: 50 }"
                :while-in-view="{ opacity: 1, x: 0 }"
                :transition="{ duration: 0.6, delay: 0.3 }"
                :viewport="{ once: true }"
            >
                <IndexSolutionCompanionApp />
            </motion.div>
        </div>
    </UContainer>
</template>
