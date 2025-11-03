export default defineEventHandler(async (event) => {
    const packageName = getRouterParam(event, "packageName");

    if (!packageName) {
        throw createError({
            statusCode: 400,
            message: "Package name is required",
        });
    }

    try {
        // Fetch the Google Play Store page
        const response = await fetch(`https://play.google.com/store/apps/details?id=${packageName}`, {
            headers: {
                "User-Agent":
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
            },
        });

        if (!response.ok) {
            throw new Error("App not found on Play Store");
        }

        const html = await response.text();

        // Extract icon URL from the HTML
        // Google Play uses this pattern for icons
        const iconMatch = html.match(/"icon":\["(https:\/\/play-lh\.googleusercontent\.com\/[^"]+)"/i);

        if (iconMatch && iconMatch[1]) {
            // Redirect to the actual icon URL
            return sendRedirect(event, iconMatch[1], 302);
        }

        // Fallback: try alternative pattern
        const altIconMatch = html.match(/src="(https:\/\/play-lh\.googleusercontent\.com\/[^"]+)"/i);
        if (altIconMatch && altIconMatch[1]) {
            return sendRedirect(event, altIconMatch[1], 302);
        }

        throw new Error("Could not extract icon URL");
    } catch {
        // Return error that will trigger the @error handler on the client
        throw createError({
            statusCode: 404,
            message: "App icon not found",
        });
    }
});
