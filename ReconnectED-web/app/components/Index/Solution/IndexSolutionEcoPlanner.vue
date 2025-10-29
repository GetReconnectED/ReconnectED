<template>
    <UCard>
        <template #header>
            <div class="flex items-center gap-3">
                <UIcon name="i-lucide-notebook-pen" class="size-8 text-success" />
                <h3 class="text-2xl font-bold text-highlighted">Eco Planner</h3>
            </div>
        </template>

        <p class="text-sm text-muted mb-4">
            Made of <strong>abaca and kraft paper</strong> → promotes agriculture and tree planting
        </p>

        <p class="text-sm text-muted mb-6">
            <strong>Features:</strong> daily planner, goal-setting sections, motivational quotes, and more
        </p>

        <div class="bg-elevated p-4 rounded-lg">
            <h4 class="font-semibold mb-3">SDGs Advocated</h4>
            <div class="space-y-3">
                <div class="flex items-start gap-3">
                    <img src="/SDG/SDG3_ICON.png" alt="SDG 3" class="size-25 mt-1" />
                    <p class="text-sm text-muted">
                        <strong
                            >Promotes mental well-being by reducing screen time and encouraging real-world
                            activities</strong
                        >. The digital detox and habit tracker features promote mental well-being by reducing screen
                        time and encouraging healthier routines.
                    </p>
                </div>
                <div class="flex items-start gap-3">
                    <p class="text-sm text-muted">
                        <strong>Lowers carbon footprint by reducing digital demand and using eco-materials</strong>. The
                        planner uses eco-friendly materials and encourages tree planting. This helps lower carbon
                        footprints and supports environmental sustainability.
                    </p>
                    <img src="/SDG/SDG13_ICON.png" alt="SDG 13" class="size-25 mt-1" />
                </div>
                <div class="flex items-start gap-3">
                    <img src="/SDG/SDG11_ICON.png" alt="SDG 12" class="size-25 mt-1" />
                    <p class="text-sm text-muted">
                        <strong>Reduces waste with a sustainable, biodegradable eco-planner</strong>. The planner
                        supports responsible consumption of energy. It also promotes sustainable production and reducing
                        waste.
                    </p>
                </div>
                <div class="flex items-start gap-3">
                    <p class="text-sm text-muted">
                        <strong>Fights digital overconsumption; uses sustainable production materials</strong>. By
                        encouraging reduced screen time and using eco-friendly materials, the planner helps combat
                        overconsumption and supports sustainable practices.
                    </p>
                    <img src="/SDG/SDG12_ICON.png" alt="SDG 13" class="size-25 mt-1" />
                </div>
            </div>
        </div>

        <template #footer>
            <div class="bg-primary/10 p-3 rounded-lg">
                <p class="text-xs font-semibold mb-2">How does ReconnectED advocate for these SDGs?</p>
                <p class="text-xs text-muted">
                    The <strong>Value-Belief-Norm (VBN) Theory</strong> serves as the foundation of ReconnectED by
                    explaining how personal values and environmental beliefs can influence responsible digital behavior.
                    It connects the user's mindset and daily actions to measurable environmental impact, promoting
                    sustainable habits that reduce one's digital carbon footprint.
                </p>
                <p class="text-xs text-muted mt-5">
                    Reference: United Nations. (2015). The 17 goals. Sustainable Development Goals.
                    <a href="https://sdgs.un.org/goals" class="text-primary hover:underline" target="_blank"
                        >https://sdgs.un.org/goals</a
                    >
                </p>
            </div>
        </template>
    </UCard>
</template>
