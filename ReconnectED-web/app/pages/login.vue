<script setup lang="ts">
import { GoogleAuthProvider, signInWithPopup } from "firebase/auth";

useSeoMeta({
    title: "Login - ReconnectED",
    description: "Sign in to view your app usage analytics",
    ogTitle: "Login - ReconnectED",
    ogDescription: "Sign in to view your app usage analytics",
    ogImage: "/logo.png",
});

const auth = useFirebaseAuth();
const user = useCurrentUser();
const router = useRouter();
const loading = ref(false);
const error = ref<string | null>(null);

// Redirect if already logged in
watch(
    user,
    (newUser) => {
        if (newUser) {
            router.push("/dashboard");
        }
    },
    { immediate: true }
);

const signInWithGoogle = async () => {
    if (!auth) {
        error.value = "Authentication system not ready";
        return;
    }

    loading.value = true;
    error.value = null;

    try {
        const provider = new GoogleAuthProvider();
        await signInWithPopup(auth, provider);
        // Explicitly redirect after successful sign-in
        await router.push("/dashboard");
    } catch (err: unknown) {
        const firebaseError = err as { code?: string; message?: string };

        if (firebaseError.code === "auth/popup-closed-by-user") {
            error.value = "Sign-in cancelled. Please try again.";
        } else if (firebaseError.code === "auth/unauthorized-domain") {
            error.value = "This domain is not authorized. Please add localhost to Firebase authorized domains.";
        } else {
            error.value = firebaseError.message || "Failed to sign in with Google";
        }
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div
        class="min-h-screen flex items-center justify-center bg-linear-to-br from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-gray-800 px-4"
    >
        <UCard class="w-full max-w-md">
            <template #header>
                <div class="text-center space-y-2">
                    <h1 class="text-3xl font-bold text-gray-900 dark:text-white">Welcome to ReconnectED</h1>
                    <p class="text-gray-600 dark:text-gray-400">Sign in to view your app usage analytics</p>
                </div>
            </template>

            <div class="space-y-4">
                <UAlert
                    v-if="error"
                    color="error"
                    variant="soft"
                    title="Authentication Error"
                    :description="error"
                    :close-button="{ icon: 'i-lucide-x', color: 'gray', variant: 'link' }"
                    @close="error = null"
                />

                <UButton block size="lg" :loading="loading" :disabled="loading" @click="signInWithGoogle">
                    <template #leading>
                        <UIcon name="i-simple-icons-google" class="w-5 h-5" />
                    </template>
                    Sign in with Google
                </UButton>

                <div class="text-center text-sm text-gray-600 dark:text-gray-400">
                    <p>By signing in, you agree to sync your app usage data from your Android device.</p>
                </div>
            </div>

            <template #footer>
                <div class="text-center text-xs text-gray-500 dark:text-gray-500">
                    <p>
                        Your privacy is important to us. We only collect app usage data to help you understand your
                        digital habits.
                    </p>
                </div>
            </template>
        </UCard>
    </div>
</template>
