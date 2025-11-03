import { getAuth, onAuthStateChanged, type User } from "firebase/auth";

export const useFirebaseAuth = () => {
    const { $firebaseAuth } = useNuxtApp();
    return $firebaseAuth;
};

export const useFirestore = () => {
    const { $firebaseFirestore } = useNuxtApp();
    return $firebaseFirestore;
};

export const useCurrentUser = () => {
    const user = useState<User | null>("firebase-user", () => null);
    const auth = useFirebaseAuth();

    onMounted(() => {
        if (auth) {
            onAuthStateChanged(auth, (newUser) => {
                user.value = newUser;
            });
        }
    });

    return user;
};

export const getCurrentUser = (): Promise<User | null> => {
    return new Promise((resolve) => {
        const auth = getAuth();
        const unsubscribe = onAuthStateChanged(auth, (user) => {
            unsubscribe();
            resolve(user);
        });
    });
};
