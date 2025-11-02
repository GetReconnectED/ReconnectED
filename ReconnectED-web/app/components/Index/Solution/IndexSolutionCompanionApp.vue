<template>
    <UCard>
        <template #header>
            <div class="flex items-center gap-3">
                <UIcon name="i-lucide-smartphone" class="size-8 text-primary" />
                <h3 class="text-2xl font-bold text-highlighted">AI-Companion App</h3>
            </div>
        </template>

        <div class="space-y-4 mb-6">
            <div>
                <UIcon name="i-lucide-circle-check" class="size-5 text-success mr-2" />
                <span class="text-sm">Tracks screen time and limits app usage</span>
            </div>
            <div>
                <UIcon name="i-lucide-circle-check" class="size-5 text-success mr-2" />
                <span class="text-sm"
                    >Provides personalized support, reminders, and assistance on planner activities</span
                >
            </div>
            <div>
                <UIcon name="i-lucide-circle-check" class="size-5 text-success mr-2" />
                <span class="text-sm"> Uses <strong>Google Tools</strong> for seamless integration</span>
            </div>
        </div>

        <div class="bg-elevated p-4 rounded-lg mb-4">
            <h4 class="font-semibold mb-3 flex items-center gap-2">
                <UIcon name="i-lucide-layers" class="size-5" />
                Google Tools Used
            </h4>
            <div class="grid grid-cols-2 gap-3">
                <div class="flex items-center gap-2">
                    <UIcon name="simple-icons:googlecalendar" class="size-4 text-primary" />
                    <span class="text-xs">Google Calendar</span>
                </div>
                <div class="flex items-center gap-2">
                    <UIcon name="simple-icons:googledocs" class="size-4 text-primary" />
                    <span class="text-xs">Google Docs</span>
                </div>
                <div class="flex items-center gap-2">
                    <UIcon name="simple-icons:googlekeep" class="size-4 text-primary" />
                    <span class="text-xs">Google Keep</span>
                </div>
                <div class="flex items-center gap-2">
                    <UIcon name="simple-icons:googlemeet" class="size-4 text-primary" />
                    <span class="text-xs">Google Meet</span>
                </div>
                <div class="flex items-center gap-2">
                    <UIcon name="simple-icons:firebase" class="size-4 text-primary" />
                    <span class="text-xs">Firebase</span>
                </div>
                <div class="flex items-center gap-2">
                    <UIcon name="simple-icons:googlegemini" class="size-4 text-primary" />
                    <span class="text-xs">Gemini AI</span>
                </div>
            </div>
        </div>

        <div class="bg-primary/10 p-4 rounded-lg">
            <h4 class="font-semibold mb-3">App Features</h4>
            <div class="grid grid-cols-2 gap-3">
                <div class="text-center">
                    <UIcon name="i-lucide-smartphone" class="size-8 mx-auto mb-2 text-primary" />
                    <p class="text-xs font-semibold">Screen Time Tracker</p>
                </div>
                <div class="text-center">
                    <UIcon name="i-lucide-alarm-clock" class="size-8 mx-auto mb-2 text-primary" />
                    <p class="text-xs font-semibold">Screen Time Limiter</p>
                </div>
                <div class="text-center">
                    <UIcon name="i-lucide-calendar" class="size-8 mx-auto mb-2 text-primary" />
                    <p class="text-xs font-semibold">Calendar</p>
                </div>
                <div class="text-center">
                    <UIcon name="i-lucide-sparkles" class="size-8 mx-auto mb-2 text-primary" />
                    <p class="text-xs font-semibold">AI Assistant</p>
                </div>
            </div>
        </div>
    </UCard>
</template>
