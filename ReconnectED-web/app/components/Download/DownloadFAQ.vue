<script lang="ts" setup>
import { motion } from "motion-v";
</script>

<template>
    <UContainer class="py-16 sm:py-24">
        <div class="max-w-3xl mx-auto">
            <motion.h2
                class="text-2xl sm:text-3xl font-bold text-highlighted mb-8 text-center"
                :initial="{ opacity: 0, y: 20 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6 }"
                :viewport="{ once: true }"
            >
                Frequently Asked Questions
            </motion.h2>

            <div class="space-y-4">
                <motion.div
                    :initial="{ opacity: 0, y: 30 }"
                    :while-in-view="{ opacity: 1, y: 0 }"
                    :transition="{ duration: 0.6, delay: 0 }"
                    :viewport="{ once: true }"
                >
                    <UCard>
                        <template #header>
                            <h3 class="font-semibold">Why do I need to enable "Unknown Sources"?</h3>
                        </template>
                        <p class="text-sm text-muted">
                            Android requires this permission to install apps from sources other than the Google Play
                            Store. This is a security measure to protect your device from unauthorized installations.
                        </p>
                    </UCard>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, y: 30 }"
                    :while-in-view="{ opacity: 1, y: 0 }"
                    :transition="{ duration: 0.6, delay: 0.1 }"
                    :viewport="{ once: true }"
                >
                    <UCard>
                        <template #header>
                            <h3 class="font-semibold">Which version should I download?</h3>
                        </template>
                        <p class="text-sm text-muted">
                            For most modern Android devices (2018 onwards), choose <strong>ARM64-v8a</strong>. If you
                            have an older device or are unsure, use the <strong>Universal</strong> version.
                        </p>
                    </UCard>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, y: 30 }"
                    :while-in-view="{ opacity: 1, y: 0 }"
                    :transition="{ duration: 0.6, delay: 0.2 }"
                    :viewport="{ once: true }"
                >
                    <UCard>
                        <template #header>
                            <h3 class="font-semibold">Is it safe to download APK files?</h3>
                        </template>
                        <p class="text-sm text-muted">
                            Yes, as long as you download from our official Google Drive link. We recommend verifying the
                            download source before installing any APK file.
                        </p>
                    </UCard>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, y: 30 }"
                    :while-in-view="{ opacity: 1, y: 0 }"
                    :transition="{ duration: 0.6, delay: 0.3 }"
                    :viewport="{ once: true }"
                >
                    <UCard>
                        <template #header>
                            <h3 class="font-semibold">What is the SHA-1 fingerprint and why is it important?</h3>
                        </template>
                        <p class="text-sm text-muted mb-2">
                            The SHA-1 fingerprint is a unique cryptographic hash that verifies the authenticity and
                            integrity of the APK file. You can use it to confirm that the file you downloaded hasn't
                            been tampered with and is the official ReconnectED release.
                        </p>
                        <p class="text-sm text-muted">
                            To verify: After downloading, you can calculate the SHA-1 hash of your APK file by running
                            the command
                            <code class="font-mono bg-elevated/50 px-1 py-0.5 rounded"
                                >Get-FileHash -Algorithm SHA1 -Path "path\to\your\file.apk"</code
                            >
                            in PowerShell (Windows),
                            <code class="font-mono bg-elevated/50 px-1 py-0.5 rounded"
                                >shasum path/to/your/file.apk</code
                            >
                            in Terminal (macOS/Linux), or using a hash-checking app on your Android device. Compare the
                            resulting hash with the SHA-1 fingerprints provided for each APK version on our download
                            page.
                        </p>
                    </UCard>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, y: 30 }"
                    :while-in-view="{ opacity: 1, y: 0 }"
                    :transition="{ duration: 0.6, delay: 0.4 }"
                    :viewport="{ once: true }"
                >
                    <UCard>
                        <template #header>
                            <h3 class="font-semibold">What's the difference between versions?</h3>
                        </template>
                        <p class="text-sm text-muted">
                            Each version is optimized for specific device architectures. Using the correct version
                            ensures better performance and smaller file size. The Universal version works on all devices
                            but is larger.
                        </p>
                    </UCard>
                </motion.div>

                <motion.div
                    :initial="{ opacity: 0, y: 30 }"
                    :while-in-view="{ opacity: 1, y: 0 }"
                    :transition="{ duration: 0.6, delay: 0.5 }"
                    :viewport="{ once: true }"
                >
                    <UCard>
                        <template #header>
                            <h3 class="font-semibold">Will this be available on Google Play Store?</h3>
                        </template>
                        <p class="text-sm text-muted">
                            We're working on making ReconnectED available on the Google Play Store. For now, you can
                            download the APK directly from our Google Drive.
                        </p>
                    </UCard>
                </motion.div>
            </div>
        </div>
    </UContainer>
</template>
