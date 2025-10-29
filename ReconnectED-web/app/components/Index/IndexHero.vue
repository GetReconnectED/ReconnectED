<template>
    <UContainer class="py-16 sm:py-24">
        <div class="text-center">
            <img src="/logo.png" alt="ReconnectED Logo" class="mx-auto h-48 w-auto mb-8" />
            <h1 class="text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight text-highlighted mb-6">
                ReconnectED: The Digital Detox Eco-Planner
            </h1>
            <p class="text-lg sm:text-xl text-muted max-w-3xl mx-auto mb-8">
                Minimize the overconsumption of the internet.
            </p>
            <div class="flex flex-wrap gap-4 justify-center">
                <UButton size="lg" icon="i-lucide-download" label="Download" to="/download" />
                <UButton
                    size="lg"
                    variant="outline"
                    color="neutral"
                    icon="i-lucide-info"
                    label="Learn More"
                    to="#info"
                />
            </div>
        </div>
    </UContainer>
</template>
