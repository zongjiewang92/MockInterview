import React from 'react';

interface ReportProps {
  company: string;
  position: string;
  report: string;
  aiResult: string;
  aiScore: string;
  updateDate: string;
}

const ReportPage: React.FC<ReportProps> = (props) => {
  const { company, position, report, aiResult, aiScore, updateDate } = props;

  return (
    <div className="report-container">
      <div className="font-medium">Company: {company}</div>
      <div className="font-medium">Position: {position}</div>
      <div className="font-medium">Report: {report}</div>
      <div className="font-medium">AI Result: {aiResult}</div>
      <div className="font-medium">AI Score: {aiScore}</div>
      <div className="font-medium">Update Date: {updateDate}</div>
    </div>
  );
}
<ReportPage
    company="Example Company"
    position="Example Position"
    report="Example Report"
    aiResult="Example Result"
    aiScore="85%"
    updateDate="July 15, 2024"
/>
export default ReportPage;