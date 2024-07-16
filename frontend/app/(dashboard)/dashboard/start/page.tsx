'use client';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { zodResolver } from '@hookform/resolvers/zod';
import useModalStore from "@/hooks/useModalStore";
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import * as z from 'zod';
import { useRouter } from 'next/navigation';

const formSchema = z.object({
  companyName: z.string().min(3, { message: 'companyName must be at least 3 characters' }),
  position: z.string().min(3, { message: 'position must be at least 3 characters' })
});

type UserFormValue = z.infer<typeof formSchema>;
import { Loader2 } from "lucide-react";
import { Separator } from "@/components/ui/separator";
import Image from "next/image";

const Loading = () => {
  return (
    <div className="wrapper">
      <Loader2 className="h-10 w-10 text-primary animate-spin" />
    </div>
  );
};


type Props = {
  searchParams: {
    cvId: string;
  };
};
interface Data {
  status: number;
  message: string;
  data: DataItem[];
}
interface DataItem {
}

export default function AuthenticationPage({ searchParams }: Props) {
  const cvId = searchParams.cvId;
  const router = useRouter();
  const { onOpen } = useModalStore();

  const defaultValues = {
    companyName: 'Google',
    position: 'Java Developer'
  };
  const form = useForm<UserFormValue>({
    resolver: zodResolver(formSchema),
    defaultValues
  });

  const onSubmit = async (data: UserFormValue) => {
    router.push(`/dashboard/questions?cvId=${cvId}&companyName=${data.companyName}&position=${data.position}`)


    // const companyName = data.companyName;
    // const position = data.position;
    // try {
    //   const response = await fetch('/sb/interview/startInterview', {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json',
    //     },
    //     body: JSON.stringify({
    //       companyName,
    //       interviewId,
    //       position,
    //     })
    //   });
    //   const result: Data = await response.json();
    //   if (result.status === 400) {
    //     onOpen("tip", result.message);
    //   }
    //   if (result.status === 200) {
    //     onOpen("tip", "Registration successful");
    //     router.push("/");
    //   }
    // } catch (error) {
    //   onOpen("tip", "Error, try again");
    // }
  };

  return (
    <main className="wrapper">
      <div className="bg-white p-3 shadow-md w-full md:w-[90%] lg:w-[70%] max-w-4xl rounded-md">
        <h1 className="heading">Start AI Mock Interview</h1>
        <Separator />
        <div className="grid grid-cols-1 md:grid-cols-2 p-5 md:p-10 gap-4">
          <div className="relative h-full flex flex-col justify-center items-center">
            <Image
              src={"/8960464_4022161.svg"}
              alt="banner-image"
              priority
              width={500}
              height={500}
              className="object-cover object-center"
            />
          </div>
          <div className="flex flex-col justify-center items-center gap-4 md:gap-6">
            <h2 className="text-center tracking-wide text-lg md:text-xl lg:text-2xl font-bold">
              Company Info
            </h2>

            <Form {...form}>
              <form
                onSubmit={form.handleSubmit(onSubmit)}
                className="w-full space-y-2"
              >
                <FormField
                  control={form.control}
                  name="companyName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Company Name</FormLabel>
                      <FormControl>
                        <Input
                          type="text"
                          placeholder="Enter your Company Name..."
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="position"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Position</FormLabel>
                      <FormControl>
                        <Input
                          type="text"
                          placeholder="Enter your Position..."
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <Button className="ml-auto w-full mt-20" type="submit">
                Start
                </Button>

              </form>
            </Form>
          </div>
        </div>
      </div>
    </main>
  );
}
