<template>
    <UCard class="mb-12 max-w-4xl mx-auto">
        <template #header>
            <div class="flex items-center gap-3">
                <UIcon name="i-lucide-info" class="size-6 text-primary" />
                <h2 class="text-xl font-bold">Installation Instructions</h2>
            </div>
        </template>

        <div class="space-y-4">
            <div class="flex items-start gap-3">
                <UBadge color="primary" variant="solid" size="lg" class="mt-1">1</UBadge>
                <div>
                    <p class="font-semibold mb-1">Choose Your APK Version</p>
                    <p class="text-sm text-muted">
                        Select the APK that matches your device's architecture. If unsure, choose the Universal version.
                    </p>
                </div>
            </div>

            <div class="flex items-start gap-3">
                <UBadge color="primary" variant="solid" size="lg" class="mt-1">2</UBadge>
                <div>
                    <p class="font-semibold mb-1">Enable Unknown Sources</p>
                    <p class="text-sm text-muted">
                        Go to Settings → Security → Enable "Install from Unknown Sources" or "Install Unknown Apps" for
                        your browser.
                    </p>
                </div>
            </div>

            <div class="flex items-start gap-3">
                <UBadge color="primary" variant="solid" size="lg" class="mt-1">3</UBadge>
                <div>
                    <p class="font-semibold mb-1">Download & Install</p>
                    <p class="text-sm text-muted">
                        Click the download button for your chosen version. Once downloaded, open the APK file and follow
                        the installation prompts.
                    </p>
                </div>
            </div>

            <div class="flex items-start gap-3">
                <UBadge color="primary" variant="solid" size="lg" class="mt-1">4</UBadge>
                <div>
                    <p class="font-semibold mb-1">Launch the App</p>
                    <p class="text-sm text-muted">
                        Open ReconnectED from your app drawer and start your digital detox journey!
                    </p>
                </div>
            </div>
        </div>

        <template #footer>
            <div class="bg-warning/10 p-3 rounded-lg">
                <div class="flex items-start gap-2">
                    <UIcon name="i-lucide-alert-triangle" class="size-5 text-warning mt-0.5" />
                    <div>
                        <p class="text-sm font-semibold text-warning mb-1">Security Notice</p>
                        <p class="text-xs text-muted">
                            Always download APK files from trusted sources. Make sure you're downloading from our
                            official Google Drive link.
                        </p>
                    </div>
                </div>
            </div>
        </template>
    </UCard>
</template>
