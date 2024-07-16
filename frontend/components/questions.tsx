import { Progress } from "@/components/ui/progress";
import useModalStore from "@/hooks/useModalStore";
import { useEffect, useRef, useState, Suspense } from "react";
import { Button } from "./ui/button";
import { Separator } from "./ui/separator";
import { AudioRecorder, useAudioRecorder } from "react-audio-voice-recorder";
import { LiveAudioVisualizer } from "react-audio-visualize";

type Props = {
  questions: {
    number: number;
    id: number;
    questionDirectory: string;
  }[];
  count: number;
};

type DataJson = {
  data: DataItem;
};

type DataItem = {
  number: number;
  id: number;
  interviewId: number;
  questionDirectory: string;
  description: string;
};

const formatTime = (totalSeconds: number): string => {
  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;
  return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
};

function convertFilePathToUrl(filePath: string): string {
  if (!filePath) return '';
  const pathParts = filePath.replace('../uploads/', '').split('\\').join('/');
  return process.env.AUDIO_URL + `${pathParts}`;
}

const Questions = ({ questions, count }: Props) => {
  const recorderControls = useAudioRecorder();
  const [audioUrl, setAudioUrl] = useState(questions[0].questionDirectory);

  const [curr, setCurr] = useState(0);
  const [progressValue, setProgressValue] = useState(0);
  const { onOpen } = useModalStore();
  const [isPlaying, setIsPlaying] = useState(0);
  const audioRef = useRef<HTMLAudioElement>(null);
  const initialTimeSeconds = 2 * 60;
  const [timeRemaining, setTimeRemaining] = useState(initialTimeSeconds);
  const [displayTime, setDisplayTime] = useState(formatTime(timeRemaining));
  const [isRunning, setIsRunning] = useState(false);

  const play = (questionDirectory:string) => {
    const audio = audioRef.current;

    if (audio) {
      audio.src = questionDirectory;
      setIsPlaying(1);

      audio.addEventListener('ended', () => {
        setIsPlaying(3);
        recorderControls.startRecording();
        setIsRunning(false);
        setIsRunning(true);
        setTimeRemaining(initialTimeSeconds);
      }, false);

      audio.play();
    }
  };

  const start = (questionDirectory:string) => {
    setTimeRemaining(initialTimeSeconds);
    setCurr((curr) => curr + 1);
    play(questionDirectory);
  };


  const handleNext = () => {
    recorderControls.stopRecording();
    setIsRunning(false);
  };

  const handleShowResult = () => {
    recorderControls.stopRecording();
    setIsRunning(false);
    // setTimeRemaining(initialTimeSeconds);
    onOpen("quitQuiz");
  };

  useEffect(() => {
    let timer: NodeJS.Timeout;

    if (isRunning) {
      timer = setInterval(() => {
        setTimeRemaining(prevTime => {
          if (prevTime <= 0) {
            clearInterval(timer);
            setIsRunning(false);

            return 0;
          }
          return prevTime - 1;
        });
      }, 1000);
    }

    return () => clearInterval(timer);
  }, [isRunning]);

  useEffect(() => {
    setDisplayTime(formatTime(timeRemaining));
  }, [timeRemaining]);

  useEffect(() => {
    setProgressValue((100 / count) * (curr));
  }, [curr]);

  const fetchData = async () => {
    try {
      setIsPlaying(2);
      const formData = new FormData();
      formData.append('file', recorderControls.recordingBlob, 'blob.webm');
      formData.append('questionId', String(questions[curr - 1].id));
      if (curr == count) {
        formData.append('nextQuestionId', '-1');
      } else {
        formData.append('nextQuestionId', String(questions[curr].id));
      }
      const response = await fetch('/sb/question/answerQuestion', {
        method: 'POST',
        body: formData,
      });
      const result: DataJson = await response.json();
      console.log(result);
      setIsPlaying(1);

      if (curr < count) {
        let questionDirectory = convertFilePathToUrl(result.data.questionDirectory);
        setAudioUrl(questionDirectory);
        start(questionDirectory);
      } 
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  useEffect(() => {
    if (!recorderControls.recordingBlob) return;
    console.log(curr, questions[curr - 1].id, recorderControls.recordingBlob);
    fetchData();
  }, [recorderControls.recordingBlob])

  return (
    <div className="wrapper">
      <div className="bg-white p-4 shadow-md w-full md:w-[80%] lg:w-[70%] max-w-5xl rounded-md">
        <h1 className="heading">AI Mock Interview</h1>
        <Separator className="mb-3" />
        <Progress value={progressValue} />
        <div className="flex justify-between py-5 px-2 font-bold text-md">
          <p>Question: {curr}</p>
          <p>Countdown: {displayTime}</p>
        </div>
        <div>
          <audio ref={audioRef} />
        </div>
        <div className="flex flex-col min-h-[70vh] py-10 px-3 md:px-5 gap-4 w-full">
          {1 > 0 && (
            <>
              <h2 className="text-2xl text-center font-medium">
                Please
                {
                  isPlaying == 0 && (
                    <span> start to the question </span>
                  )
                }
                {
                  isPlaying == 1 && (
                    <span> listen to the audio </span>
                  )
                }
                {
                  isPlaying == 3 && (
                    <span>  record your answer </span>
                  )
                }.
              </h2>
              <div className="hidden">
                <AudioRecorder
                  recorderControls={recorderControls}
                />
              </div>
              {recorderControls.mediaRecorder && (
                <Suspense>
                  <LiveAudioVisualizer
                    mediaRecorder={recorderControls.mediaRecorder}
                    barWidth={2}
                    gap={2}
                    width={550}
                    height={30}
                    fftSize={1024}
                    maxDecibels={-10}
                    minDecibels={-120}
                    smoothingTimeConstant={0.8}
                  />
                </Suspense>
              )}

              <Separator />
              {
                isPlaying == 0 && (
                  <div className="flex mt-5 md:justify-center md:flex-row flex-col gap-4 md:gap-0 mx-auto max-w-xs w-full">
                    <Button onClick={() => start(questions[0].questionDirectory)}>Start</Button>
                  </div>
                )
              }
              {
                isPlaying != 0 && (
                  <div className="flex mt-5 md:justify-center md:flex-row flex-col gap-4 md:gap-0 mx-auto max-w-xs w-full">
                    <Button
                      disabled={isPlaying == 1}
                      onClick={() => count === curr
                        ? handleShowResult()
                        : handleNext()}
                    >
                      {count > curr
                        ? "Next Question"
                        : "Submit Answer"}
                    </Button>
                    {/* <Button variant={"destructive"} onClick={handleQuit}>
                      Quit Quiz
                    </Button> */}
                  </div>
                )
              }
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default Questions;
