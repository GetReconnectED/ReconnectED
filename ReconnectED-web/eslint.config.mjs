// @ts-check
import withNuxt from "./.nuxt/eslint.config.mjs";

export default withNuxt({
    files: ["**/*.{js,mjs,cjs,ts,vue}"],
    ignores: ["coverage/**", "dist/**", ".nuxt/**", "node_modules/**"],
});
