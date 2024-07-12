import os
import openai
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
import utils
import constant as fsp


class InterviewerStateMachine:
    def __init__(self, company, position, resume_text, extracted_info):
        self.state = 'INIT'
        self.company = company
        self.position = position
        self.resume_text = resume_text
        self.extracted_info = extracted_info
        self.candidate_name = extracted_info["Name"]
        self.questions = [
            f"Hi {self.candidate_name}, welcome to {company}, Could you please introduce yourself?",
            f"Why do you want to join {company}?",
            f"What makes you think you are a good fit for the {position} position?",
            "Based on your education and work experience, describe a challenging project you worked on and how you handled it.",
            "Tell me about a time when you had to learn a new technology or skill quickly. How did you approach it?"
        ]
        self.current_question_index = 0
        self.candidate_answers = []
        self.llm = OpenAI(api_key=openai.api_key, max_tokens=1000)

    def next_state(self, user_input=None, ai_output_dir="../output"):
        if self.state == 'INIT':
            # print(f'Current state {self.state}')
            self.state = 'ASK_QUESTION'
            # print(f'Next state {self.state}')
            return self._ask_question(ai_output_dir)

        elif self.state == 'ASK_QUESTION':
            # print(f'Current state {self.state}')
            self.candidate_answers.append(user_input)
            self.current_question_index += 1
            # print(self.current_question_index, len(self.questions))
            if self.current_question_index < len(self.questions):
                # print(f'Next state {self.state}')
                return self._ask_question(ai_output_dir)
            else:
                self.state = 'EVALUATE'
                # print(f'Next state {self.state}')
                return self._evaluate(ai_output_dir)

    def _ask_question(self, ai_output_dir):
        question = self.questions[self.current_question_index]
        # Add contextual connection
        if self.current_question_index > 0:
            previous_answer = self.candidate_answers[-1]
            context_connector = self._generate_context_connector(previous_answer, question)
            question = f"{context_connector} {question}"
        response_text = question
        return response_text, utils.call_tts_api(response_text, os.path.join(ai_output_dir, f"R{self.current_question_index}_response.mp3"))


    def _generate_context_connector(self, previous_answer, next_question):
        prompt_template_connector = PromptTemplate(
            input_variables=["previous_answer", "next_question"],
            template="""
            Based on the candidate's previous answer:

            {previous_answer}

            Generate a sentence to smoothly transition to the next question:

            {next_question}

            The transition sentence should be relevant to the previous answer and lead naturally into the next question.

            Please remember that you are the interviewer, and you are interviewing the candidate. Please pay attention to using pronouns like 'you' or 'I' accordingly.
            """
        )
        chain = LLMChain(prompt=prompt_template_connector, llm=self.llm)
        context_connector = chain.run({"previous_answer": previous_answer, "next_question": next_question})
        return context_connector.strip()
    

    def _evaluate(self, ai_output_dir):
        answers = "\n".join([f"Q: {self.questions[i]}\nA: {answer}" for i, answer in enumerate(self.candidate_answers)])
        prompt_template_evaluation = PromptTemplate(
            input_variables=["info", "answers", "good_example", "average_example", "bad_example"],
            template="""
            You are a mock interviewer. The candidate's resume information is as follows:

            {info}

            You have just conducted a mock interview, and the complete dialogue from the interview is as follows:

            {answers}

            Here are some good example for you to reference: {good_example}.
            Here are some average example for you to reference: {average_example}.
            Here are some bad example for you to reference: {bad_example}.

            Based on the candidate's mock interview performance and interaction during the interview, as well as the few-shot examples you have learned, please provide some feedback and guidance to the candidate. You may also suggest how the candidate could improve their responses based on specific details from their resume.

            """
        )
        chain = LLMChain(prompt=prompt_template_evaluation, llm=self.llm)
        evaluation_result = chain.run({"info": self.extracted_info, "answers": answers, "good_example": fsp.good_example, "average_example": fsp.average_example, "bad_example": fsp.bad_example})
        return evaluation_result, utils.call_tts_api(evaluation_result, os.path.join(ai_output_dir, f"R{self.current_question_index}_response.mp3"))


def service(interviewSM, candidate_input, ai_output_dir):
    # Simulate interaction with backend
    if interviewSM.state == 'INIT':
        # Start interviewSM
        ai_text, audio_response_path = interviewSM.next_state()
        user_input_text = "no input"
    elif interviewSM.state == 'EVALUATE':
        ai_text, audio_response_path = interviewSM._evaluate()
    else:
        # Simulate receiving user audio input from backend
        user_audio_path = candidate_input
        user_input_text = utils.call_asr_api(user_audio_path)
        ai_text, audio_response_path = interviewSM.next_state(user_input_text)

    # Send audio_response_path to backend
    print(f"AI respones are store in : {audio_response_path}")
    return interviewSM, ai_text, user_input_text


if __name__ == "__main__":

    pass

