import { create } from "zustand";

export type modalType = "showResults" | "quitQuiz"| "tip";

interface DataItem {
  companyName: string;
  position: string;
  report: string;
  aiResult: string;
  aiScore: string;
  updateDate: string;
}

interface modalStore {
  type: modalType | null;
  isOpen: boolean;
  additionalData: DataItem | null;
  onOpen: (type: modalType, data?: DataItem) => void;
  onClose: () => void;
}

const useModalStore = create<modalStore>((set) => ({
  type: null,
  isOpen: false,
  additionalData: null,
  onOpen: (type, data) => {
    set({ isOpen: true, type, additionalData: data });
  },
  onClose: () => set({ type: null, isOpen: false, additionalData: null }),
}));

export default useModalStore;
