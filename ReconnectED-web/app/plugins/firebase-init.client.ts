import { initializeApp, getApps } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";

export default defineNuxtPlugin(() => {
    const config = useRuntimeConfig();

    const firebaseConfig = {
        apiKey: config.public.firebaseApiKey as string,
        authDomain: config.public.firebaseAuthDomain as string,
        projectId: config.public.firebaseProjectId as string,
        storageBucket: config.public.firebaseStorageBucket as string,
        messagingSenderId: config.public.firebaseMessagingSenderId as string,
        appId: config.public.firebaseAppId as string,
    };

    // Initialize Firebase only if it hasn't been initialized yet
    const apps = getApps();
    const app = apps.length === 0 ? initializeApp(firebaseConfig) : apps[0]!;

    // Initialize services
    const auth = getAuth(app);
    const firestore = getFirestore(app);

    // Provide globally
    return {
        provide: {
            firebaseApp: app,
            firebaseAuth: auth,
            firebaseFirestore: firestore,
        },
    };
});
