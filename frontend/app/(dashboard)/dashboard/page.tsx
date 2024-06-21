'use client';
import { CalendarDateRangePicker } from '@/components/date-range-picker';
import { Overview } from '@/components/overview';
import { RecentSales } from '@/components/recent-sales';
import { Button } from '@/components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from '@/components/ui/card';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Plus, Radar } from 'lucide-react';
import { useRouter } from 'next/navigation';
import FileUpload from '@/components/file-upload';
import { EmployeeForm } from '@/components/forms/employee-form';
import { signOut, useSession } from 'next-auth/react';

export default function page() {
  const router = useRouter();

  return (
    <div className="wrapper">
      <div className="bg-white p-4 shadow-md w-full md:w-[80%] lg:w-[70%] max-w-5xl rounded-md">
        {/* <div className="bg-white p-4 shadow-md w-full md:w-[80%] lg:w-[70%] max-w-5xl rounded-md"> */}
        <h1 className="heading">Quizy</h1>
        <main className="flex-1 overflow-hidden pt-4">
          <ScrollArea className="h-full">
            <div className="flex-1 space-y-4 p-4 pt-6 md:p-8">
              <div className="flex items-center justify-between space-y-2">
                <h2 className="text-3xl font-bold tracking-tight">
                  Hi, Welcome 👋
                </h2>
                <div className="hidden items-center space-x-2 md:flex">
                  <Button onClick={() => router.push('dashboard/questions?categories=science&limit=3&type=multiple&difficulty=medium')}><Radar className="mr-2 h-4 w-4" /> Add Resume</Button>
                  <Button variant={"destructive"} onClick={() => signOut()} > Log out</Button>
                </div>
              </div>
              <Tabs defaultValue="overview" className="space-y-4">
                <TabsList>
                  <TabsTrigger value="overview">Resume</TabsTrigger>
                  <TabsTrigger value="analytics">
                    Analytics
                  </TabsTrigger>
                </TabsList>
                <TabsContent value="overview" className="space-y-4">
                  <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-7">
                    <Card className="col-span-8 md:col-span-8">
                      <CardContent>
                        <EmployeeForm categories={[]} initialData={null} />
                        <RecentSales />
                      </CardContent>
                    </Card>
                  </div>
                </TabsContent>
                <TabsContent value="analytics" className="space-y-4">
                  <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-7">
                    <Card className="col-span-8 md:col-span-8">
                      <CardHeader>
                        <CardTitle>Recent Sales</CardTitle>
                        <CardDescription>
                          You made 265 sales this month.
                        </CardDescription>
                      </CardHeader>
                      <CardContent>
                        <RecentSales />
                      </CardContent>
                    </Card>
                  </div>
                </TabsContent>
              </Tabs>
            </div>
          </ScrollArea>
        </main>
      </div>
    </div>
  );
}
