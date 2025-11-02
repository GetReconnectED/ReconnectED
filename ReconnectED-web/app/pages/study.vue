<script setup lang="ts">
import { motion } from "motion-v";
import { ref, computed, onMounted } from "vue";
import Chart from "chart.js/auto";

type IsoCategory = {
    name: string;
    mean: number;
    stdDev: number;
    interpretation: string;
};

useSeoMeta({
    title: "ReconnectED Survey Results - TAM & ISO 25010 Evaluation",
    description: "Technology Acceptance Model and ISO 25010 evaluation results.",
    ogTitle: "ReconnectED Survey Results - TAM & ISO 25010 Evaluation",
    ogDescription: "Technology Acceptance Model and ISO 25010 evaluation results.",
    ogImage: "/logo.png",
});

const isoResults: IsoCategory[] = [
    { name: "Functionality Suitability", mean: 3.64, stdDev: 0.48, interpretation: "Excellent" },
    { name: "Reliability", mean: 3.52, stdDev: 0.47, interpretation: "Good to Excellent" },
    { name: "Portability", mean: 3.57, stdDev: 0.52, interpretation: "Excellent" },
    { name: "Usability", mean: 3.6, stdDev: 0.4, interpretation: "Excellent" },
    { name: "Performance Efficiency", mean: 3.56, stdDev: 0.49, interpretation: "Excellent" },
    { name: "Security", mean: 3.6, stdDev: 0.48, interpretation: "Excellent" },
    { name: "Compatibility", mean: 3.62, stdDev: 0.49, interpretation: "Excellent" },
    { name: "Maintainability", mean: 3.5, stdDev: 0.51, interpretation: "Good to Excellent" },
];
const chartRefs = ref<{ [key: string]: HTMLCanvasElement | null }>({});

/**
 * Sets the reference for a chart canvas element.
 * @param key The key to identify the chart
 * @param el The canvas element
 */
const setChartRef = (key: string, el: HTMLCanvasElement | null) => {
    if (el) chartRefs.value[key] = el;
};

onMounted(() => {
    initializeCharts();
});

/**
 * Calculates the overall weighted mean from ISO results.
 */
const overallWeightedMean = computed(() => {
    const sum = isoResults.reduce((acc, item) => acc + item.mean, 0);
    return (sum / isoResults.length).toFixed(2);
});

/**
 * Calculates the overall standard deviation from ISO results.
 */
const overallStdDev = computed(() => {
    const sum = isoResults.reduce((acc, item) => acc + item.stdDev, 0);
    return (sum / isoResults.length).toFixed(2);
});

/**
 * Gets the highest scoring ISO criterion.
 */
const highestIsoScore = computed(() => {
    return isoResults.reduce((max, item) => (item.mean > max.mean ? item : max), isoResults[0]);
});

/**
 * Gets ISO results sorted by mean score (highest to lowest).
 */
const sortedIsoResults = computed(() => {
    return [...isoResults].sort((a, b) => b.mean - a.mean);
});

/**
 * Initializes all charts on the page.
 *
 * This is needed because Chart.js requires the canvas elements to be mounted
 * before rendering.
 */
const initializeCharts = () => {
    // ISO 25010 Chart
    if (chartRefs.value.isoResults) {
        new Chart(chartRefs.value.isoResults, {
            type: "bar",
            data: {
                labels: isoResults.map((item) => item.name),
                datasets: [
                    {
                        label: "Mean Score",
                        data: isoResults.map((item) => item.mean),
                        backgroundColor: "#10b981",
                        borderColor: "#059669",
                        borderWidth: 2,
                    },
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    title: {
                        display: true,
                        text: "ISO 25010 Quality Criteria Evaluation",
                        font: { size: 16 },
                    },
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 4,
                        min: 3,
                        title: { display: true, text: "Mean Score" },
                    },
                    x: {
                        ticks: {
                            autoSkip: false,
                            maxRotation: 45,
                            minRotation: 45,
                        },
                    },
                },
            },
        });
    }
};
</script>

<template>
    <div>
        <!-- Hero Section -->
        <UContainer class="py-16 sm:py-24">
            <motion.div
                class="text-center max-w-4xl mx-auto mb-16"
                :initial="{ opacity: 0, y: 20 }"
                :animate="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6 }"
            >
                <h1 class="text-4xl sm:text-5xl font-bold tracking-tight text-highlighted mb-6">
                    ReconnectED Survey Results
                </h1>
                <p class="text-lg text-muted mb-4">
                    Technology Acceptance Model (TAM) and ISO 25010 Software Quality Evaluation conducted with 30
                    respondents from Baliuag University.
                </p>
                <!-- <UBadge color="primary" size="lg">Research Study • Academic Year 2024-2025</UBadge> -->
            </motion.div>

            <!-- Executive Summary -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6 }"
                :viewport="{ once: true }"
            >
                <UCard class="mb-12">
                    <template #header>
                        <div class="flex items-center gap-3">
                            <UIcon name="i-lucide-file-text" class="size-6 text-primary" />
                            <h2 class="text-2xl font-bold">Executive Summary</h2>
                        </div>
                    </template>
                    <div class="prose prose-gray dark:prose-invert max-w-none">
                        <p class="text-muted mb-4">
                            The ReconnectED project underwent evaluation through two methodologies:
                            <strong>Technology Acceptance Model (TAM)</strong> and
                            <strong>ISO 25010 Software Quality Standards</strong>. Both assessments were conducted with
                            <strong>30 respondents</strong> from Baliuag University.
                        </p>
                        <p class="text-muted">
                            The results demonstrate <strong>strong positive acceptance</strong> from the respondents.
                            The TAM survey achieved a mean score of <strong>3.59</strong> (SD = 0.55), while the ISO
                            evaluation yielded an overall weighted mean of
                            <strong>{{ overallWeightedMean }}</strong> (SD = {{ overallStdDev }}), both indicating
                            ratings between "Agree" and "Strongly Agree" on a 4-point Likert scale.
                        </p>
                    </div>
                </UCard>
            </motion.div>

            <!-- Key Findings -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.1 }"
                :viewport="{ once: true }"
            >
                <h2 class="text-3xl font-bold text-highlighted mb-8 text-center">Key Findings</h2>
                <div class="grid md:grid-cols-3 gap-6 mb-12">
                    <UCard>
                        <div class="text-center">
                            <div class="text-4xl font-bold text-primary mb-2">3.59</div>
                            <p class="text-sm text-muted">
                                TAM Survey <strong>Mean Score</strong> (SD = 0.55) indicating strong acceptance between
                                "Agree" and "Strongly Agree"
                            </p>
                        </div>
                    </UCard>
                    <UCard>
                        <div class="text-center">
                            <div class="text-4xl font-bold text-success mb-2">{{ overallWeightedMean }}</div>
                            <p class="text-sm text-muted">
                                ISO 25010 <strong>Overall Weighted Mean</strong> (SD = {{ overallStdDev }})
                                demonstrating excellent software quality
                            </p>
                        </div>
                    </UCard>
                    <UCard>
                        <div class="text-center">
                            <div class="text-4xl font-bold text-highlighted mb-2">{{ highestIsoScore.mean }}</div>
                            <p class="text-sm text-muted">
                                <strong>Highest ISO Score</strong> in {{ highestIsoScore.name }}, showing system
                                effectiveness
                            </p>
                        </div>
                    </UCard>
                </div>
            </motion.div>

            <!-- TAM Survey Section -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.2 }"
                :viewport="{ once: true }"
                class="mb-12"
            >
                <h2 class="text-3xl font-bold text-highlighted mb-8 text-center">
                    Technology Acceptance Model (TAM) Results
                </h2>

                <UCard>
                    <template #header>
                        <h3 class="text-xl font-bold">TAM Interpretation</h3>
                    </template>
                    <div class="space-y-4 p-4">
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">Statistical Summary</h4>
                            <ul class="text-sm text-muted space-y-2 list-disc">
                                <li><strong>Mean</strong>: 3.59</li>
                                <li><strong>Standard Deviation</strong>: 0.55</li>
                                <li><strong>Sample Size</strong>: 30 respondents</li>
                            </ul>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">Key Insights</h4>
                            <ul class="text-sm text-muted space-y-2 list-disc">
                                <li>The score falls between "Agree" and "Strongly Agree"</li>
                                <li>There is a positive perception of the ReconnectED system among the respondents</li>
                                <li>The respondents find the eco-planner and companion app effective</li>
                                <li>The system promotes digital wellness and productivity</li>
                            </ul>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">Response Consistency</h4>
                            <p class="text-sm text-muted">
                                SD of 0.55 indicates fairly consistent responses across 15 indicators, showing minimal
                                disagreement among participants.
                            </p>
                        </div>
                    </div>
                </UCard>
            </motion.div>

            <!-- ISO 25010 Section -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.3 }"
                :viewport="{ once: true }"
                class="mb-12"
            >
                <h2 class="text-3xl font-bold text-highlighted mb-8 text-center">
                    ISO 25010 Software Quality Evaluation
                </h2>

                <div class="grid md:grid-cols-2 gap-6 mb-8">
                    <UCard>
                        <template #header>
                            <h3 class="text-xl font-bold">Quality Criteria Scores</h3>
                        </template>
                        <div class="h-96">
                            <canvas :ref="(el) => setChartRef('isoResults', el)" />
                        </div>
                    </UCard>

                    <UCard>
                        <template #header>
                            <h3 class="text-xl font-bold">Detailed Breakdown</h3>
                        </template>
                        <div class="space-y-3 p-4">
                            <div
                                v-for="(item, index) in sortedIsoResults"
                                :key="item.name"
                                class="flex justify-between items-center pb-2"
                                :class="{ 'border-b': index < sortedIsoResults.length - 1 }"
                            >
                                <span class="text-sm font-medium">{{ item.name }}</span>
                                <span
                                    class="text-sm font-bold"
                                    :class="{
                                        'text-primary': item.mean >= 3.62,
                                        'text-success': item.mean >= 3.6 && item.mean < 3.62,
                                    }"
                                >
                                    {{ item.mean }}
                                </span>
                            </div>
                            <div class="mt-4 pt-4 border-t-2">
                                <div class="flex justify-between items-center">
                                    <span class="font-bold">Overall Weighted Mean</span>
                                    <span class="text-lg font-bold text-primary">{{ overallWeightedMean }}</span>
                                </div>
                                <div class="flex justify-between items-center mt-2">
                                    <span class="text-sm text-muted">Standard Deviation</span>
                                    <span class="text-sm font-medium">{{ overallStdDev }}</span>
                                </div>
                            </div>
                        </div>
                    </UCard>
                </div>
            </motion.div>

            <!-- Comparison Charts -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.4 }"
                :viewport="{ once: true }"
                class="mb-12"
            >
                <h2 class="text-3xl font-bold text-highlighted mb-8 text-center">Comparative Analysis</h2>

                <div class="grid md:grid-cols-2 gap-6">
                    <UCard>
                        <template #header>
                            <h3 class="text-xl font-bold">Overall Mean Comparison</h3>
                        </template>
                        <template #footer>
                            <p class="text-sm text-muted">
                                Both TAM (3.59) and ISO ({{ overallWeightedMean }}) scores are remarkably consistent,
                                indicating strong agreement across different evaluation methodologies.
                            </p>
                        </template>
                    </UCard>

                    <UCard>
                        <template #header>
                            <h3 class="text-xl font-bold">Response Consistency</h3>
                        </template>
                        <template #footer>
                            <p class="text-sm text-muted">
                                Low standard deviations (TAM: 0.55, ISO: {{ overallStdDev }}) demonstrate consistent
                                responses and shared positive experience among participants.
                            </p>
                        </template>
                    </UCard>
                </div>
            </motion.div>

            <!-- User Feedback Section -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.5 }"
                :viewport="{ once: true }"
                class="mb-12"
            >
                <h2 class="text-3xl font-bold text-highlighted mb-8 text-center">Qualitative Feedback Analysis</h2>

                <div class="grid md:grid-cols-3 gap-6">
                    <UCard>
                        <template #header>
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-heart" class="size-5 text-pink-500" />
                                <h3 class="text-lg font-bold">Appreciation</h3>
                            </div>
                        </template>
                        <p class="text-sm text-muted">
                            The respondents recognized ReconnectED as
                            <strong>helpful for digital detox and mental wellness</strong>, particularly for users
                            experiencing social media burnout. The planner and companion app promote balance and focus.
                        </p>
                    </UCard>

                    <UCard>
                        <template #header>
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-sparkles" class="size-5 text-yellow-500" />
                                <h3 class="text-lg font-bold">Engagement Suggestions</h3>
                            </div>
                        </template>
                        <p class="text-sm text-muted">
                            The respondents suggested making
                            <strong>more interactive and engaging</strong> content, such as adding creative activities
                            in the eco-planner rather than relying mainly on AI tools.
                        </p>
                    </UCard>

                    <UCard>
                        <template #header>
                            <div class="flex items-center gap-2">
                                <UIcon name="i-lucide-scale" class="size-5 text-blue-500" />
                                <h3 class="text-lg font-bold">Balance Recommendation</h3>
                            </div>
                        </template>
                        <p class="text-sm text-muted">
                            Multiple respondents emphasized that both the companion app and eco-planner should be
                            <strong>equally developed</strong>—enriching the planner's content with more environmental
                            and digital wellness themes.
                        </p>
                    </UCard>
                </div>
            </motion.div>

            <!-- Conclusions -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.6 }"
                :viewport="{ once: true }"
            >
                <UCard>
                    <template #header>
                        <div class="flex items-center gap-3">
                            <UIcon name="i-lucide-lightbulb" class="size-6 text-primary" />
                            <h2 class="text-2xl font-bold">Conclusions & Implications</h2>
                        </div>
                    </template>
                    <div class="space-y-4">
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">1. Strong Positive Acceptance</h4>
                            <p class="text-sm text-muted">
                                The TAM survey results show
                                <strong>strong positive acceptance</strong> among the 30 respondents. The mean score of
                                3.59 reflects general agreement that ReconnectED is both meaningful and effective.
                            </p>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">2. High Quality Assurance</h4>
                            <p class="text-sm text-muted">
                                The high ISO mean values confirm that ReconnectED meets the
                                <strong>ISO 25010 quality benchmarks</strong>-functional, secure, user-friendly, and
                                efficient. The system shows readiness for broader implementation at Baliuag University.
                            </p>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">3. Overall Effectiveness</h4>
                            <p class="text-sm text-muted">
                                The system is
                                <strong>generally effective in achieving its objectives</strong>-encouraging digital
                                balance, self-reflection, and sustainable behavior. Functionality, Usability, and
                                Security received the highest scores.
                            </p>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">4. Need for Balanced Enhancement</h4>
                            <p class="text-sm text-muted">
                                To sustain engagement, the planner and app should be developed in tandem, integrating
                                more <strong>interactive, reflective, and environmental features</strong>. Minor
                                improvements in app stability and feature balance are recommended.
                            </p>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">5. Educational Integration Potential</h4>
                            <p class="text-sm text-muted">
                                The system has <strong>potential to be formally integrated</strong> into digital
                                wellness or guidance programs at Baliuag University because it resonates well with
                                student needs and preferences.
                            </p>
                        </div>
                        <div>
                            <h4 class="font-semibold text-highlighted mb-2">6. Sustainability Alignment</h4>
                            <p class="text-sm text-muted">
                                The alignment of digital detox goals with
                                <strong>eco-friendly initiatives</strong> strengthens the system's relevance in
                                sustainable education and technology integration.
                            </p>
                        </div>
                    </div>
                </UCard>
            </motion.div>

            <!-- Final Summary -->
            <motion.div
                :initial="{ opacity: 0, y: 30 }"
                :while-in-view="{ opacity: 1, y: 0 }"
                :transition="{ duration: 0.6, delay: 0.7 }"
                :viewport="{ once: true }"
                class="mb-12 pt-10"
            >
                <UCard class="bg-linear-to-br from-primary/5 to-success/5">
                    <template #header>
                        <div class="flex items-center gap-3">
                            <UIcon name="i-lucide-check-circle" class="size-6 text-success" />
                            <h2 class="text-2xl font-bold">Final Summary</h2>
                        </div>
                    </template>
                    <div class="prose prose-gray dark:prose-invert max-w-none">
                        <p class="text-muted mb-4">
                            <strong>TAM Evaluation</strong>: The TAM survey results demonstrate strong positive
                            acceptance and consistent user satisfaction among the 30 respondents. The mean score of 3.59
                            reflects general agreement that ReconnectED is both meaningful and effective, while the low
                            variability in responses (SD = 0.55) underscores its reliability and coherence as a digital
                            wellness intervention tool.
                        </p>
                        <p class="text-muted">
                            <strong>ISO 25010 Evaluation</strong>: The ISO evaluation reveals that the ReconnectED
                            interactive learning module demonstrates strong quality performance across all dimensions.
                            With an overall mean of {{ overallWeightedMean }} and low variability (SD =
                            {{ overallStdDev }}), the system is consistent, functional, and user-approved. The
                            respondents' feedback validates its impact on digital mindfulness and sustainability, while
                            also encouraging further feature enrichment and planner-app balance.
                        </p>
                    </div>
                </UCard>
            </motion.div>
        </UContainer>
    </div>
</template>
