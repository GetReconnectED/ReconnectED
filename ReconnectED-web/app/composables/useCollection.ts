import { onSnapshot, type Query, type DocumentData } from "firebase/firestore";
import type { Ref } from "vue";

export const useCollection = <T = DocumentData>(queryRef: Ref<Query<T> | null>) => {
    const data = ref<T[]>([]) as Ref<T[]>;
    const pending = ref(true);
    const error = ref<Error | null>(null);

    let unsubscribe: (() => void) | null = null;

    const setupListener = () => {
        if (unsubscribe) {
            unsubscribe();
            unsubscribe = null;
        }

        if (!queryRef.value) {
            data.value = [];
            pending.value = false;
            return;
        }

        pending.value = true;
        unsubscribe = onSnapshot(
            queryRef.value,
            (snapshot) => {
                data.value = snapshot.docs.map((doc) => ({
                    ...doc.data(),
                    id: doc.id,
                })) as T[];
                pending.value = false;
                error.value = null;
            },
            (err) => {
                console.error("Firestore query error");
                error.value = err as Error;
                pending.value = false;
            }
        );
    };

    watch(queryRef, setupListener, { immediate: true });

    onUnmounted(() => {
        if (unsubscribe) {
            unsubscribe();
        }
    });

    return data;
};
