<template>
    <UContainer class="py-16 sm:py-24 bg-primary/5">
        <div class="text-center max-w-3xl mx-auto">
            <h2 class="text-3xl sm:text-4xl font-bold text-highlighted mb-6">Start Your Digital Detox Journey Today</h2>
            <p class="text-lg text-muted mb-8">
                Join ReconnectED and make a positive impact on both your well-being and the environment.
            </p>
            <div class="flex flex-wrap gap-4 justify-center">
                <UButton size="xl" icon="i-lucide-download" label="Download" to="/download" />
                <UButton
                    size="xl"
                    variant="outline"
                    color="neutral"
                    icon="i-lucide-log-in"
                    label="Log In"
                    to="/login"
                />
            </div>
        </div>
    </UContainer>
</template>
