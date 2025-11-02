<script lang="ts" setup>
import { motion } from "motion-v";

const taglines = [
    "Minimize the overconsumption of the internet.",
    "Take control of your digital habits.",
    "Reduce your carbon footprint online.",
    "Reconnect with what matters most.",
    "Balance your digital and real-world life.",
];

const currentTextIndex = ref(0);
const displayedText = ref("");
const isDeleting = ref(false);
const typingSpeed = ref(80);

onMounted(() => {
    // Typewriter effect animation
    const typeWriter = () => {
        const fullText = taglines[currentTextIndex.value];

        if (!fullText) return;
        if (!isDeleting.value) {
            // Typing
            if (displayedText.value.length < fullText.length) {
                displayedText.value = fullText.substring(0, displayedText.value.length + 1);
                typingSpeed.value = 80;
            } else {
                // Pause before deleting
                typingSpeed.value = 3000;
                isDeleting.value = true;
            }
        } else {
            // Deleting
            if (displayedText.value.length > 0) {
                displayedText.value = fullText.substring(0, displayedText.value.length - 1);
                typingSpeed.value = 20;
            } else {
                // Move to next text
                isDeleting.value = false;
                currentTextIndex.value = (currentTextIndex.value + 1) % taglines.length;
                typingSpeed.value = 500;
            }
        }

        setTimeout(typeWriter, typingSpeed.value);
    };

    typeWriter();
});
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
                :while-hover="{ scale: 2 }"
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
                class="text-lg sm:text-xl text-muted max-w-3xl mx-auto mb-8 min-h-14 flex items-center justify-center"
                :initial="{ opacity: 0, y: 20 }"
                :animate="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.4 }"
            >
                <span>{{ displayedText }}</span>
                <span class="inline-block w-0.5 h-6 bg-primary ml-1 animate-pulse" />
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
