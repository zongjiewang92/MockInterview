import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import React, { useState, useEffect } from 'react';

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import { signOut, useSession } from 'next-auth/react';
import { ScanEye, FileArchive, FileCheck, Trash } from 'lucide-react';
import useModalStore from "@/hooks/useModalStore";

interface Data {
  data: DataItem[];
}
interface DataItem {
  id: number;
  aiScore: string;
  aiResult: string;
  updateDate: string;
  report: string;
  position: string;
  companyName: string;
}
export function Results() {
  const [data, setData] = useState<DataItem[]>([]);
  const { data: session } = useSession();
  const { onOpen } = useModalStore();

  const fetchData = async () => {
    try {
      const response = await fetch('/sb/interview/getInterviewsByUserId/' + session!.user?.email, {
        headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + session!.user?.image }
      });
      const result: Data = await response.json();
      console.log(result)
      setData(result.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const start = async (item: DataItem) => {
    onOpen("showResults", item);
  };

  const deleteData = async (id: number) => {
    try {
      const response = await fetch('/sb/interview/deleteInterview/' + id.toString(), {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + session!.user?.image }
      });
      const result: Data = await response.json();
      console.log(result)
      fetchData()
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-7">
      <Card className="col-span-8 md:col-span-8">
        <CardHeader>
          <CardTitle>Recent Analytics</CardTitle>
          <CardDescription>
            You have {data.length} analytics.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-8 mt-2">
            {/* {data.length > 0 && (
              <div className="flex justify-between items-center space-y-1">
                <div className="font-medium">company</div>
                <div className="font-medium">position</div>
                <div className="font-medium">report</div>
                <div className="font-medium">aiResult</div>
                <div className="font-medium">aiScore</div>
                <div className="font-medium">updateDate</div>
              </div>
            )} */}
            {data.length > 0 ? data.map(item => (
              <div className="flex justify-between" key={item.id}>
                <div className="flex items-center min-w-80 max-w-80">
                  <Avatar className="h-9 w-9">
                    <FileCheck className="h-8 w-8 pl-2" />
                  </Avatar>
                  <div className="ml-4 space-y-1">
                    <p className="text-sm font-medium leading-none hover:cursor-pointer" onClick={() => { item.id }}>{item.companyName} - {item.position}</p>
                    <p className="text-sm text-muted-foreground">
                      {item.report?.substring(0, 50)}
                    </p>
                  </div>
                </div>
                <div className="">{item.updateDate}</div>


                <div className="flex font-normal">
                  <div className="ml-4 space-y-1 flex hover:cursor-pointer" onClick={() => start(item)} >
                    <ScanEye className="mr-1 h-4 w-4 mt-1" /> Show
                  </div>
                  <div className="ml-4 space-y-1 flex hover:cursor-pointer" onClick={() => deleteData(item.id)} >
                    <Trash className="mr-1 h-4 w-4 mt-1" /> Delete
                  </div>
                </div>
              </div>
            )
            ) : (
              <p>You have not take any interview yet</p>
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
}