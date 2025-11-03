export default defineNuxtRouteMiddleware(async () => {
    // Firebase is initialized in a client-only plugin
    // so we skip auth check on server-side
    if (import.meta.server) {
        return;
    }

    try {
        const user = await getCurrentUser();

        // If not authenticated, redirect to login
        if (!user) {
            return navigateTo("/login");
        }
    } catch {
        // If Firebase hasn't initialized yet, redirect to login
        return navigateTo("/login");
    }
});
