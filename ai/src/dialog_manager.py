import openai
import utils
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate

class InterviewerStateMachine:
    # Constructor : __init__
    def __init__(self, company, position, resume_text, extracted_info):
        # set attribute
        self.state = 'INIT'
        self.company = company
        self.position = position
        self.resume_text = resume_text
        self.extrcted_info = extracted_info
        self.cadidate_name = extracted_info['Name']
        self.questions = [
            f"Hi {self.candidate_name}, welcome to {company}, Could you please introduce yourself?",
            f"Why do you want to join {company}?",
            f"What makes you think you are a good fit for the {position} position?",
            "Based on your education and work experience, describe a challenging project you worked on and how you handled it.",
            "Tell me about a time when you had to learn a new technology or skill quickly. How did you approach it?"
        ]
        self.current_question_index = 0
        self.cadidate_answers = []

    # state : INIT, WELCOME, ASK_QUESTION, EVALUATE 
    def next_state(self, user_input=None)
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
        return utils.call_tts_api(response_text, f"../output/R{self.current_question_index}_response.mp3")

    def _evaluate(self):





def service(interviewSM, candidate_input):
    if interviewSM.state == 'INIT':

    elif interviewSM.state == 'EVALUATE':
        
    else:




# if run this file directly : Ture (__name__ เป็นตัวแปรพิเศษใน Python ที่จะมีค่าเป็น "__main__" เมื่อไฟล์นั้นถูกรันโดยตรง แต่ถ้าRunจากไฟล์อื่น จะมีค่าเป็นชื่อของไฟล์ที่ import เข้ามา)
# if import this file : False
if __name__ == "__main__":
    pass
