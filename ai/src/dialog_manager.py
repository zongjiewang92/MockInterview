import openai
import utils
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate

class InterviewerStateMachine:
    def __init__(self, company, position, resume_text, extracted_info):
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








def service(interviewSM, candidate_input):
    if interviewSM.state == 'INIT':

    elif interviewSM.state == 'EVALUATE':

    else:





if __name__ == "__main__":

    pass
