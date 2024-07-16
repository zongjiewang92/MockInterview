import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import useModalStore from "@/hooks/useModalStore";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";

const ResultModal = () => {
  const { isOpen, type, onClose, additionalData } = useModalStore();
  const open = isOpen && type === "showResults";

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className="text-center text-xl md:text-2xl">
            Quiz Result
          </DialogTitle>
        </DialogHeader>
        <Separator />
        <DialogDescription>
          <div className="flex items-center flex-col py-6 md:py-10 lg:py-12">
            <div className="report-container">
              <div>
                <h3 className="text-lg md:2xl text-primary tracking-wide">
                  Company:
                </h3>
                {additionalData?.companyName}
              </div>
              <div>
                <h3 className="text-lg md:2xl text-primary tracking-wide">
                  Position:
                </h3>
                {additionalData?.position}
              </div>
              <div>
                <h3 className="text-lg md:2xl text-primary tracking-wide">
                  Report:
                </h3>
                {additionalData?.report}
              </div>
              <div>
                <h3 className="text-lg md:2xl text-primary tracking-wide">
                  AI Score: 
                </h3>
                {additionalData?.aiScore}
              </div>
              <div>
                <h3 className="text-lg md:2xl text-primary tracking-wide">
                  Update Date:
                </h3>
                {additionalData?.updateDate}
              </div>
            </div>
          </div>
        </DialogDescription>
      </DialogContent>
    </Dialog>
  );
};

export default ResultModal;
