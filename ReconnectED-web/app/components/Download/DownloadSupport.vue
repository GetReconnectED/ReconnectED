<template>
    <UContainer class="py-16 sm:py-24 bg-primary/5">
        <div class="text-center max-w-2xl mx-auto">
            <UIcon name="i-lucide-help-circle" class="size-12 mx-auto mb-4 text-primary" />
            <h2 class="text-2xl sm:text-3xl font-bold text-highlighted mb-4">Need Help?</h2>
            <p class="text-base text-muted mb-8">
                If you encounter any issues during installation or have questions about which version to download, feel
                free to reach out to us.
            </p>
            <div class="flex flex-wrap gap-4 justify-center">
                <UButton size="lg" icon="i-lucide-mail" label="Contact Support" />
                <UButton to="/" size="lg" variant="outline" color="neutral" icon="i-lucide-home" label="Back to Home" />
            </div>
        </div>
    </UContainer>
</template>
