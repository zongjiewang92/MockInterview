"use client";
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import React, { useState, useEffect } from 'react';

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import FileUpload from '@/components/file-uploader';
import { signOut, useSession } from 'next-auth/react';
import { Edit, FileArchive, FileCheck, Trash } from 'lucide-react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

interface Data {
  data: DataItem[];
}
interface DataItem {
  id: number;
  cvName: string;
  updateDate: string;
  cvContext: string;
}

export function RecentSales() {
  const [data, setData] = useState<DataItem[]>([]);
  const { data: session } = useSession();
  const router = useRouter();

  const fetchData = async () => {
    try {
      const response = await fetch('/sb/resume/getResumesByUserId/' + session!.user?.email, {
        headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + session!.user?.image }
      });
      const result: Data = await response.json();
      console.log(result)
      setData(result.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const start = async (id: number) => {
    router.push(`/dashboard/start?cvId=${id}`)
  };

  const deleteData = async (id: number) => {
    try {
      const response = await fetch('/sb/resume/deleteResume/' + id.toString(), {
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
          <CardTitle>Recent Resumes</CardTitle>
          <CardDescription>
            You have {data.length} resumes.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-8 mt-2">
            {data.length > 0 ? data.map(item => (
              <div className="flex justify-between" key={item.id}>
                <div className="flex items-center min-w-80 max-w-80">
                  <Avatar className="h-9 w-9">
                    <FileArchive className="h-8 w-8 pl-2" />
                  </Avatar>
                  <div className="ml-4 space-y-1">
                    <p className="text-sm font-medium leading-none">
                      <Link href={`/sb/resume/download/${item.id}`}>
                        {item.cvName}
                      </Link>
                    </p>
                    <p className="text-sm text-muted-foreground overflow-hidden">
                      {item.cvContext?.substring(0, 50)}
                    </p>
                  </div>
                </div>
                <div className="">{item.updateDate}</div>

                <div className="flex font-normal">
                  <div className="ml-4 space-y-1 flex hover:cursor-pointer" onClick={() => start(item.id)} >
                    <Edit className="mr-1 h-4 w-4 mt-1" /> Start
                  </div>
                  <div className="ml-4 space-y-1 flex hover:cursor-pointer" onClick={() => deleteData(item.id)} >
                    <Trash className="mr-1 h-4 w-4 mt-1" /> Delete
                  </div>
                </div>
              </div>
            )
            ) : (
              <p>You have not uploaded any reseme here</p>
            )}
          </div>
            <FileUpload refresh={fetchData} />
        </CardContent>
      </Card>
    </div>
  );
}
