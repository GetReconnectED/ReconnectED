<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <motion.div
        :initial="{ opacity: 0 }"
        :while-in-view="{ opacity: 1 }"
        :transition="{ duration: 0.6 }"
        :viewport="{ once: true }"
    >
        <UFooter>
            <template #left>
                <p class="text-muted text-sm">Copyright © {{ new Date().getFullYear() }}</p>
            </template>
        </UFooter>
    </motion.div>
</template>
