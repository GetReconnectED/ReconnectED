<script setup lang="ts">
useSeoMeta({
    title: "Download ReconnectED - Digital Detox Eco-Planner App",
    description: "Download the ReconnectED Android app for your device.",
    ogTitle: "Download ReconnectED - Digital Detox Eco-Planner App",
    ogDescription: "Download the ReconnectED Android app for your device.",
    ogImage: "/logo.png",
});

interface ApkDownload {
    name: string;
    abi: string;
    description: string;
    icon: string;
    recommended?: boolean;
    driveUrl: string;
    sha1: string;
}

const apkDownloads = ref<ApkDownload[]>([
    {
        name: "ARM64-v8a",
        abi: "arm64-v8a",
        description: "For 64-bit ARM devices (most modern Android phones). Recommended for best performance.",
        icon: "i-lucide-smartphone",
        recommended: true,
        driveUrl: "https://drive.google.com/file/d/1QADy5mvc_4pWhCkLK9ywzzL7EDuEW7-M/view?usp=sharing",
        sha1: "CBBCC3259FF91C96741BEAE860E183E7814C56E2",
    },
    {
        name: "ARMv7a",
        abi: "armeabi-v7a",
        description: "For 32-bit ARM devices (older Android phones and tablets).",
        icon: "i-lucide-tablet",
        driveUrl: "https://drive.google.com/file/d/1FFQKkCDx_i7vFpdUD2aZ8e6eeQFxHSDs/view?usp=sharing",
        sha1: "2CB5939D9D270F3E0B0F7653C64E42EB4D501074",
    },
    {
        name: "Universal",
        abi: "universal",
        description: "Works on all devices. Larger file size but guaranteed compatibility.",
        icon: "i-lucide-package",
        driveUrl: "https://drive.google.com/file/d/15ybKIt0Vy4uzcOuce4gzjIeOYDbcjzBV/view?usp=sharing",
        sha1: "5D3C1209CF114F1A81FF4368A9D5B19833E07DA1",
    },
    {
        name: "x86_64",
        abi: "x86_64",
        description: "For Intel/AMD 64-bit processors (emulators and some tablets).",
        icon: "i-lucide-cpu",
        driveUrl: "https://drive.google.com/file/d/1mJ97NHD-XV1Cm02x8DvrO01znkBMreaV/view?usp=sharing",
        sha1: "0124013E6533D8483D734520B72F11351EDF254D",
    },
]);
</script>

<template>
    <div>
        <UContainer class="py-16 sm:py-24">
            <DownloadHero />
            <DownloadInstallationInstructions />
            <DownloadCards :apk-downloads="apkDownloads" />
        </UContainer>

        <!-- How to Check Your Device Architecture -->
        <UContainer class="py-16 sm:py-24 bg-elevated/50">
            <div class="max-w-3xl mx-auto">
                <h2 class="text-2xl sm:text-3xl font-bold text-highlighted mb-6 text-center">
                    How to Check Your Device Architecture
                </h2>

                <UCard>
                    <div class="space-y-6">
                        <div>
                            <h3 class="font-semibold mb-3 flex items-center gap-2">
                                <UIcon name="i-lucide-shield-check" class="size-5 text-success" />
                                Method 1: Check Device Specifications
                            </h3>
                            <p class="text-sm text-muted mb-2">
                                Go to Settings → About Phone → Processor or CPU to see your device's architecture.
                            </p>
                            <ul class="list-disc list-inside space-y-1 text-sm text-muted ml-4">
                                <li>ARM64, ARMv8, or Cortex-A series → Use <strong>ARM64-v8a</strong></li>
                                <li>ARM, ARMv7, or Cortex-A series (32-bit) → Use <strong>ARMv7a</strong></li>
                                <li>Intel or AMD processor → Use <strong>x86_64</strong></li>
                            </ul>
                        </div>

                        <div>
                            <h3 class="font-semibold mb-3 flex items-center gap-2">
                                <UIcon name="i-lucide-download" class="size-5 text-primary" />
                                Method 2: Use CPU-Z App
                            </h3>
                            <p class="text-sm text-muted mb-2">
                                Download the free "CPU-Z" app from Google Play Store to see detailed information about
                                your device's CPU architecture.
                            </p>
                        </div>

                        <div>
                            <h3 class="font-semibold mb-3 flex items-center gap-2">
                                <UIcon name="i-lucide-package" class="size-5 text-info" />
                                Method 3: Use Universal Build
                            </h3>
                            <p class="text-sm text-muted">
                                If you're unsure or want the easiest option, simply download the
                                <strong>Universal</strong> version. It works on all devices but has a larger file size.
                            </p>
                        </div>
                    </div>
                </UCard>
            </div>
        </UContainer>
        <DownloadFAQ />
        <DownloadSupport />
    </div>
</template>
