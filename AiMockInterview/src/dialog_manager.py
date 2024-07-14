import openai
import utils
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
import os


class InterviewerStateMachine:
    # Constructor : __init__
    def __init__(self, interview_id, company, position, resume_text, extracted_info):
        # set attribute
        self.state = 'INIT'
        self.interview_id = interview_id
        self.company = company
        self.position = position
        self.resume_text = resume_text
        self.extracted_info = extracted_info
        self.candidate_name = extracted_info['Name']
        self.questions = [
            f"Hi {self.candidate_name}, welcome to {company}, Could you please introduce yourself?",
            f"Why do you want to join {company}?",
            f"What makes you think you are a good fit for the {position} position?",
            f"Based on your education and work experience, describe a challenging project you worked on and how you handled it.",
            f"Tell me about a time when you had to learn a new technology or skill quickly. How did you approach it?"
        ]
        self.current_question_index = 0
        self.candidate_answers = []
        self.llm = OpenAI(api_key=openai.api_key, max_tokens=-1)
        self.evaluation_result = None
        self.evaluation_result_audio = None

    # state : INIT, WELCOME, ASK_QUESTION, EVALUATE 
    def next_state(self, user_input=None):
        if self.state == 'INIT':
            # print(f'Current state {self.state}')
            self.state = 'ASK_QUESTION'
            # print(f'Next state {self.state}')
            return self._ask_question()

        elif self.state == 'WELCOME':
            # print(f'Current state {self.state}')
            self.candidate_answers.append(user_input)
            self.state = 'ASK_QUESTION'
            # print(f'Next state {self.state}')
            return self._ask_question()

        elif self.state == 'ASK_QUESTION':
            # print(f'Current state {self.state}')
            self.candidate_answers.append(user_input)
            self.current_question_index += 1
            # print(self.current_question_index, len(self.questions))
            if self.current_question_index < len(self.questions):
                # print(f'Next state {self.state}')
                return self._ask_question()
            else:
                self.state = 'EVALUATE'
                # print(f'Next state {self.state}')
                return self._evaluate()

    def _ask_question(self):
        question = self.questions[self.current_question_index]
        response_text = question

        file_path = self.check_path()

        return utils.call_tts_api(response_text, file_path)

    def _evaluate(self):
        answers = "\n".join([f"Q: {self.questions[i]}\nA: {answer}" for i, answer in enumerate(self.candidate_answers)])
        prompt_template_evaluation = PromptTemplate(
            input_variables=["info", "answers"],
            template="""
            You are a mock interviewer. The candidate's resume information is as follows:

            {info}

            You have just conducted a mock interview, and the complete dialogue from the interview is as follows:

            {answers}

            Now, please first assign a score to the candidate's mock interview performance out of a total of 10 points. Then, provide some feedback and guidance based on the interaction during the interview. You may also suggest how the candidate could improve their responses based on specific details from their resume.

            """
        )
        chain = LLMChain(prompt=prompt_template_evaluation, llm=self.llm)
        self.evaluation_result = chain.run({"info": self.extracted_info, "answers": answers})
        # this file name should be whole interview evaluation result?

        file_path = self.check_path()

        self.evaluation_result_audio = utils.call_tts_api(self.evaluation_result, file_path)

        return self.evaluation_result_audio

    def check_path(self):
        file_path = f"../output/R{self.interview_id}/R{self.current_question_index}_response.mp3"
        directory = os.path.dirname(file_path)
        if not os.path.exists(directory):
            os.makedirs(directory)

        if not os.path.exists(file_path):
            with open(file_path, 'wb') as f:
                pass
            print(f"File created: {file_path}")
        else:
            print(f"File already exists: {file_path}")
        return file_path


def service(interview_sm, candidate_input):
    # Simulate interaction with backend
    if interview_sm.state == 'INIT':
        # Start interviewSM
        audio_response_path = interview_sm.next_state()
    elif interview_sm.state == 'EVALUATE':
        audio_response_path = interview_sm._evaluate()
    else:
        # Simulate receiving user audio input from backend
        user_audio_path = candidate_input
        user_input_text = utils.call_asr_api(user_audio_path)
        audio_response_path = interview_sm.next_state(user_input_text)

    # Send audio_response_path to backend
    print(f"AI respones are store in : {audio_response_path}")
    return interview_sm


# if run this file directly : Ture (__name__ เป็นตัวแปรพิเศษใน Python ที่จะมีค่าเป็น "__main__" เมื่อไฟล์นั้นถูกรันโดยตรง แต่ถ้าRunจากไฟล์อื่น จะมีค่าเป็นชื่อของไฟล์ที่ import เข้ามา)
# if import this file : False
if __name__ == "__main__":
    pass
