"use client";
import Questions from "@/components/questions";
import { useSession } from 'next-auth/react';
import React, { useState, useEffect } from 'react';
import { Progress } from "@/components/ui/progress";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator"; // Import the missing module from the correct file path

type DataItem = {
  number: number;
  id: number;
  interviewId: number;
  questionDirectory: string;
  description: string;
};

type Props = {
  searchParams: {
    cvId: string;
    companyName: string;
    position: string;
  };
};

function convertFilePathToUrl(filePath: string): string {
  console.log(filePath);
  if (!filePath) return '';
  const url = `/sb/resource/getResourceUrl?resourcePath=${filePath}`;
  console.log(url);
  return url;
}

const QuestionsPage = ({ searchParams }: Props) => {
  const [data, setData] = useState<DataItem[]>([]);
  const [count, setCount] = useState<number>(0);
  const companyName = searchParams.companyName;
  const cvId = searchParams.cvId;
  const position = searchParams.position;
  const { data: session } = useSession();
  const userId = session?.user?.email!;

  const fetchData = async () => {
    try {
      // const response = await fetch(`/sb/question/selectByInterviewIdPages?pageNum=1&pageSize=100&interviewId=${interviewId}`, {
      //   headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + session!.user?.image }
      // });
      // const result: WData = await response.json();

      const response = await fetch('/sb/interview/startInterview2', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          companyName,
          cvId,
          userId,
          position,
        })
      });
      const result: DataItem[] = await response.json();
      console.log(result);
      for (let i = 0; i < result.length; i++) {
        result[i].questionDirectory = convertFilePathToUrl(result[i].questionDirectory);
      }
      console.log(result);
      setData(result);
      setCount(result.length)
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (count > 0) {
    return (
      <Questions
        questions={data}
        count={count}
      />
    );
  }
  return (
    <div className="wrapper">
      <div className="bg-white p-4 shadow-md w-full md:w-[80%] lg:w-[70%] max-w-5xl rounded-md">
        <h1 className="heading">AI Mock Interview</h1>
        <Separator className="mb-3" />
        <Progress value={0} />
        <div className="flex justify-between py-5 px-2 font-bold text-md">
          <p>Question: {0}</p>
          <p>Countdown: {0}</p>
        </div>
        <div className="flex flex-col min-h-[70vh] py-10 px-3 md:px-5 gap-4 w-full">
    <h2 className="text-2xl text-center font-medium">
    Loading...
              </h2>
        </div>
      </div>
    </div>
  )
};

export default QuestionsPage;
