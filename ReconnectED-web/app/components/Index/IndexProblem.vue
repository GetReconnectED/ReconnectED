<template>
    <UContainer class="py-16 sm:py-24 bg-elevated/50">
        <div class="grid lg:grid-cols-2 gap-12 items-center">
            <div>
                <h2 class="text-3xl sm:text-4xl font-bold text-highlighted mb-6">The Problem and Its Impact</h2>
                <p class="text-xl font-semibold text-primary mb-4">Overconsumption of the Internet</p>
                <p class="text-base text-muted mb-8">
                    People spend excessive time <strong>scrolling</strong>, <strong>engaging</strong>, and
                    <strong>consuming online content</strong>, often unaware of its <strong>negative impact</strong>.
                </p>

                <div class="space-y-6 mb-8">
                    <UCard>
                        <template #header>
                            <div class="flex items-center gap-3">
                                <UIcon name="i-lucide-smartphone" class="size-6 text-primary" />
                                <h3 class="font-semibold">Philippines Internet Usage</h3>
                            </div>
                        </template>
                        <p class="text-sm text-muted">
                            The Philippines' average daily internet use (8h 52m) is
                            <strong class="text-highlighted">2h 12m higher</strong> than the global average (6h 40m).
                        </p>
                        <template #footer>
                            <ULink
                                class="text-xs text-muted"
                                to="https://datareportal.com/reports/digital-2025-philippines"
                                target="_blank"
                            >
                                Source: (Kemp, 2025)
                            </ULink>
                        </template>
                    </UCard>

                    <UCard>
                        <template #header>
                            <div class="flex items-center gap-3">
                                <UIcon name="i-lucide-brain" class="size-6 text-primary" />
                                <h3 class="font-semibold">Mental Health Impact</h3>
                            </div>
                        </template>
                        <p class="text-sm text-muted mb-4">
                            Prolonged digital media use contributes to
                            <strong>physical and mental health problems</strong> such as:
                        </p>
                        <div class="grid grid-cols-2 gap-3">
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-frown" class="size-4 text-warning" />
                                <span class="text-sm">Anxiety & Depression</span>
                            </div>
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-moon" class="size-4 text-warning" />
                                <span class="text-sm">Sleep Disturbances</span>
                            </div>
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-alert-circle" class="size-4 text-warning" />
                                <span class="text-sm">Doom Scrolling</span>
                            </div>
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-trending-down" class="size-4 text-warning" />
                                <span class="text-sm">Low Productivity</span>
                            </div>
                        </div>
                        <template #footer>
                            <ULink
                                class="text-xs text-muted"
                                to="https://www.massgeneral.org/news/article/digital-distraction-and-its-impact-on-your-health"
                                target="_blank"
                            >
                                Source: (Keer, 2023)
                            </ULink>
                        </template>
                    </UCard>
                </div>
            </div>

            <div>
                <h3 class="text-2xl sm:text-3xl font-bold text-highlighted mb-6">The Impact on the Environment</h3>
                <p class="text-base text-muted mb-6">
                    Rising digital demand increases <strong>carbon footprint and waste</strong>
                </p>

                <UCard class="mb-6">
                    <div class="flex items-center gap-4 mb-4">
                        <UIcon name="i-lucide-leaf" class="size-12 text-success" />
                        <div>
                            <h4 class="font-semibold text-lg">Calculate Your Impact</h4>
                            <p class="text-sm text-muted">
                                ReconnectED calculates your
                                <strong class="text-success">positive environmental impact</strong>
                                by converting app screen time saved into carbon (CO2e) savings.
                            </p>
                        </div>
                    </div>
                    <div class="bg-elevated p-4 rounded-lg">
                        <p class="text-center font-mono text-sm mb-3">
                            <strong>Total CO2e Saved = ∑ (Minutes Saved × Emission Factor)</strong>
                        </p>
                        <div class="grid grid-cols-3 gap-3 text-center">
                            <div>
                                <UIcon name="i-lucide-monitor" class="size-8 mx-auto mb-2 text-primary" />
                                <p class="text-xs font-semibold">Video Streaming</p>
                                <p class="text-xs text-muted">0.92 gCO2e/min</p>
                            </div>
                            <div>
                                <UIcon name="i-lucide-globe" class="size-8 mx-auto mb-2 text-primary" />
                                <p class="text-xs font-semibold">Web Browsing</p>
                                <p class="text-xs text-muted">1.20 gCO2e/min</p>
                            </div>
                            <div>
                                <UIcon name="i-lucide-share-2" class="size-8 mx-auto mb-2 text-primary" />
                                <p class="text-xs font-semibold">Social Media</p>
                                <p class="text-xs text-muted">1.21 gCO2e/min</p>
                            </div>
                        </div>
                    </div>
                    <template #footer>
                        <p class="text-xs text-muted">
                            Note: Pilot testing, test runs, and the triangulation method were employed to validate the
                            feasibility and reliability of the project's prototype.
                        </p>
                    </template>
                </UCard>
            </div>
        </div>
    </UContainer>
</template>
