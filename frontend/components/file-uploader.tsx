// src/FileUpload.tsx
import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import { useSession } from 'next-auth/react';

interface FileUploadProps {
  refresh: () => void;
}

const FileUpload: React.FC<FileUploadProps> = ({ refresh }) => {
  const [files, setFiles] = useState<File[]>([]);
  const { data: session } = useSession();

  const onDrop = useCallback((acceptedFiles: File[]) => {
    // upload to server
    console.log(acceptedFiles);
    const formData = new FormData();
    formData.append('file', acceptedFiles[0]);
    formData.append('userId', session!.user!.email!);

    fetch('/sb/resume/uploadResume2', {
      method: 'POST',
      body: formData,
    }).then(resp => {
      console.log(resp);
      refresh();
    });
    setFiles(acceptedFiles);
  }, [refresh, session]);

  const { getRootProps, getInputProps } = useDropzone({ onDrop });

  return (
    <div className="mx-auto py-8">
      <div
        {...getRootProps({
          className: 'border-dashed border-2 border-gray-400 py-4 rounded-md',
        })}
      >
        <input {...getInputProps()} />
        <p className="text-center">Drag 'n' drop some files here, or click to select files</p>
      </div>
      <aside className="mt-0 invisible">
        <h4 className="text-lg font-semibold">Files</h4>
        <ul>
          {files.map((file, index) => (
            <li key={index} className="mt-2">
              {file.name} - {file.size} bytes
            </li>
          ))}
        </ul>
      </aside>
    </div>
  );
};

export default FileUpload;
