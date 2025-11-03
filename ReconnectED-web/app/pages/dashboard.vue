<script setup lang="ts">
import { collection, query, orderBy, limit } from "firebase/firestore";

definePageMeta({
    middleware: "auth",
});

useSeoMeta({
    title: "Dashboard - ReconnectED",
    description: "View your app usage analytics",
    ogTitle: "Dashboard - ReconnectED",
    ogDescription: "View your app usage analytics",
});

const user = useCurrentUser();
const db = useFirestore();
const router = useRouter();
const auth = useFirebaseAuth();

// Selected date for viewing (defaults to today)
const selectedDate = ref(new Date().toISOString().split("T")[0]);

// Get today's date in yyyy-MM-dd format (UTC to match Android app)
const today = computed(() => {
    // Using UTC to match Android app's Firebase storage
    return new Date().toISOString().split("T")[0];
});

// Get formatted date for display (converts UTC to local)
const selectedDateDisplay = computed(() => {
    const date = new Date(selectedDate.value + "T00:00:00Z");
    return date.toLocaleDateString("en-US", {
        month: "short",
        day: "numeric",
        year: "numeric",
    });
});

const isToday = computed(() => selectedDate.value === today.value);

// Authentication state monitoring
watch(
    user,
    (newUser) => {
        if (newUser) {
            console.log("User authenticated");
        } else {
            console.log("User not authenticated");
        }
    },
    { immediate: true }
);

// Query app usage data for selected date (using UTC date)
const usageDataRef = computed(() => {
    if (!user.value?.uid || !db) {
        return null;
    }

    const path = `users/${user.value.uid}/appUsage/${selectedDate.value}/apps`;
    return query(collection(db, path), orderBy("usageMillis", "desc"), limit(50));
});

// Date navigation
const goToPreviousDay = () => {
    const date = new Date(selectedDate.value + "T00:00:00Z");
    date.setUTCDate(date.getUTCDate() - 1);
    selectedDate.value = date.toISOString().split("T")[0];
};

const goToNextDay = () => {
    const date = new Date(selectedDate.value + "T00:00:00Z");
    date.setUTCDate(date.getUTCDate() + 1);
    selectedDate.value = date.toISOString().split("T")[0];
};

const goToToday = () => {
    selectedDate.value = today.value;
};

const appUsageData = useCollection(usageDataRef);

// Calculate total usage
const totalUsage = computed(() => {
    if (!appUsageData.value) return 0;
    return appUsageData.value.reduce(
        (sum: number, app: Record<string, unknown>) => sum + (Number(app.usageMillis) || 0),
        0
    );
});

// Format milliseconds to readable time
const formatTime = (ms: number) => {
    const hours = Math.floor(ms / (1000 * 60 * 60));
    const minutes = Math.floor((ms % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((ms % (1000 * 60)) / 1000);

    if (hours > 0) return `${hours}h ${minutes}m`;
    if (minutes > 0) return `${minutes}m ${seconds}s`;
    return `${seconds}s`;
};

// Convert to hours for percentage calculation
const getUsagePercentage = (ms: number) => {
    if (totalUsage.value === 0) return 0;
    return Math.round((ms / totalUsage.value) * 100);
};

const signOut = async () => {
    if (!auth) return;
    await auth.signOut();
    router.push("/login");
};
</script>

<template>
    <div class="min-h-screen bg-gray-50 dark:bg-gray-900">
        <!-- Header -->
        <header class="bg-white dark:bg-gray-800 shadow">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                <div class="flex items-center justify-between">
                    <div class="flex items-center gap-4">
                        <UAvatar
                            :src="user?.photoURL ?? undefined"
                            :alt="user?.displayName ?? user?.email ?? 'User'"
                            size="3xl"
                        />
                        <div>
                            <h1 class="text-2xl font-bold text-gray-900 dark:text-white">Dashboard</h1>
                            <p class="text-sm text-gray-600 dark:text-gray-400 mt-1">
                                Welcome back, {{ user?.displayName || user?.email }}
                            </p>
                        </div>
                    </div>
                    <div class="flex items-center gap-2">
                        <UButton color="neutral" variant="soft" class="hidden sm:flex" @click="signOut">
                            Sign Out
                        </UButton>
                        <UButton
                            color="neutral"
                            variant="soft"
                            icon="i-lucide-log-out"
                            class="sm:hidden"
                            @click="signOut"
                        />
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <!-- Date Navigation -->
            <div class="flex items-center justify-between mb-6 gap-2">
                <UButton variant="soft" class="hidden sm:flex" @click="goToPreviousDay">
                    <UIcon name="i-lucide-chevron-left" />
                    Previous Day
                </UButton>
                <UButton variant="soft" icon="i-lucide-chevron-left" class="sm:hidden" @click="goToPreviousDay" />
                <div class="text-center flex-1">
                    <p class="text-lg font-semibold text-gray-900 dark:text-white">{{ selectedDateDisplay }}</p>
                    <p class="text-xs text-gray-500 dark:text-gray-500">UTC: {{ selectedDate }}</p>
                </div>
                <UButton variant="soft" class="hidden sm:flex" :disabled="isToday" @click="goToNextDay">
                    Next Day
                    <UIcon name="i-lucide-chevron-right" />
                </UButton>
                <UButton
                    variant="soft"
                    icon="i-lucide-chevron-right"
                    class="sm:hidden"
                    :disabled="isToday"
                    @click="goToNextDay"
                />
            </div>
            <div v-if="!isToday" class="flex justify-center mb-6">
                <UButton variant="outline" @click="goToToday"> Go to Today </UButton>
            </div>

            <!-- Summary Cards -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                <UCard>
                    <div class="space-y-2">
                        <p class="text-sm text-gray-600 dark:text-gray-400">Total Screen Time</p>
                        <p class="text-3xl font-bold text-gray-900 dark:text-white">
                            {{ formatTime(totalUsage) }}
                        </p>
                        <p class="text-xs text-gray-500 dark:text-gray-500">{{ selectedDateDisplay }}</p>
                    </div>
                </UCard>

                <UCard>
                    <div class="space-y-2">
                        <p class="text-sm text-gray-600 dark:text-gray-400">Apps Used</p>
                        <p class="text-3xl font-bold text-gray-900 dark:text-white">
                            {{ appUsageData?.length || 0 }}
                        </p>
                    </div>
                </UCard>

                <UCard>
                    <div class="space-y-2">
                        <p class="text-sm text-gray-600 dark:text-gray-400">Most Used App</p>
                        <p class="text-xl font-bold text-gray-900 dark:text-white">
                            {{ appUsageData?.[0]?.appName || "N/A" }}
                        </p>
                        <p class="text-xs text-gray-500 dark:text-gray-500">
                            {{ appUsageData?.[0] ? formatTime(appUsageData[0].usageMillis) : "" }}
                        </p>
                    </div>
                </UCard>
            </div>

            <!-- App Usage List -->
            <UCard>
                <template #header>
                    <h2 class="text-xl font-bold text-gray-900 dark:text-white">App Usage Breakdown</h2>
                </template>

                <div v-if="appUsageData && appUsageData.length > 0" class="space-y-3">
                    <div
                        v-for="app in appUsageData"
                        :key="app.packageName"
                        class="flex items-center gap-4 p-4 bg-gray-50 dark:bg-gray-800 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
                    >
                        <div
                            class="w-12 h-12 rounded-lg bg-gray-200 dark:bg-gray-700 flex items-center justify-center overflow-hidden"
                        >
                            <img
                                v-if="!app.iconError"
                                :src="`/api/app-icon/${app.packageName}`"
                                :alt="app.appName"
                                class="w-full h-full object-cover"
                                @error="app.iconError = true"
                            />
                            <UIcon v-else name="i-lucide-smartphone" class="w-6 h-6 text-gray-400 dark:text-gray-500" />
                        </div>
                        <div class="flex-1 min-w-0">
                            <h3 class="font-semibold text-gray-900 dark:text-white truncate">
                                {{ app.appName }}
                            </h3>
                            <p class="text-sm text-gray-600 dark:text-gray-400 truncate">
                                {{ app.packageName }}
                            </p>
                        </div>
                        <div class="text-right space-y-1">
                            <p class="text-lg font-bold text-gray-900 dark:text-white">
                                {{ formatTime(app.usageMillis) }}
                            </p>
                            <p class="text-sm text-gray-600 dark:text-gray-400">
                                {{ getUsagePercentage(app.usageMillis) }}%
                            </p>
                        </div>
                    </div>
                </div>

                <div v-else-if="appUsageData" class="text-center py-12">
                    <UIcon name="i-lucide-smartphone" class="w-16 h-16 mx-auto text-gray-400 mb-4" />
                    <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">No usage data yet</h3>
                    <p class="text-gray-600 dark:text-gray-400">
                        Use the ReconnectED Android app to start tracking your app usage.
                    </p>
                </div>

                <div v-else class="text-center py-12">
                    <UIcon name="i-lucide-loader-2" class="w-16 h-16 mx-auto text-gray-400 mb-4 animate-spin" />
                    <p class="text-gray-600 dark:text-gray-400">Loading your app usage data...</p>
                </div>
            </UCard>
        </main>
    </div>
</template>
