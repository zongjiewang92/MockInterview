import os
import openai
import utils
import dialog_manager
import constant

from dotenv import load_dotenv
load_dotenv()
MyOpenaiKey = os.getenv("MyOpenaiKey")
openai.api_key = MyOpenaiKey

# Define Variables
company = "Tech Innovations Inc."
position = "Machine Learning Engineer"
file_path = "../data/resume1.pdf"

# parse resume file to text.
summary_info, resume_text, extracted_info = utils.parse_resume_file(file_path)

# Initialize interview state machine
interviewSM = dialog_manager.InterviewerStateMachine(company, position, resume_text, extracted_info)
# Simulated 5 round conversation.
interviewSM, ai_resp_text, user_input_text = dialog_manager.service(interviewSM, candidate_input='', ai_output_dir="../output")
print(f"Round0: Candidate do not have input:{user_input_text}")
print(f"Round1: AI question: {ai_resp_text}")
interviewSM, ai_resp_text, user_input_text = dialog_manager.service(interviewSM, candidate_input='../data/User_1_audio.m4a', ai_output_dir="../output")
print(f"Round1: Candidate audio asr result:{user_input_text}")
print(f"Round2: AI question: {ai_resp_text}")
interviewSM, ai_resp_text, user_input_text = dialog_manager.service(interviewSM, candidate_input='../data/User_2_audio.m4a', ai_output_dir="../output")
print(f"Round2: Candidate audio asr result:{user_input_text}")
print(f"Round3: AI question: {ai_resp_text}")
interviewSM, ai_resp_text, user_input_text = dialog_manager.service(interviewSM, candidate_input='../data/User_3_audio.m4a', ai_output_dir="../output")
print(f"Round3: Candidate audio asr result:{user_input_text}")
print(f"Round4: AI question: {ai_resp_text}")
interviewSM, ai_resp_text, user_input_text = dialog_manager.service(interviewSM, candidate_input='../data/User_4_audio.m4a', ai_output_dir="../output")
print(f"Round4: Candidate audio asr result:{user_input_text}")
print(f"Round5: AI question: {ai_resp_text}")
interviewSM, ai_resp_text, user_input_text = dialog_manager.service(interviewSM, candidate_input='../data/User_5_audio.m4a', ai_output_dir="../output")
print(f"Round5: Candidate audio asr result:{user_input_text}")
print(f"RoundE: AI evaluation: {ai_resp_text}")


