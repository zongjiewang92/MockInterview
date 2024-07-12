import openai
import utils
import dialog_manager
import constant

openai.api_key = constant.jyOpenAIKey

# Define Variables
company = "Tech Innovations Inc."
position = "Machine Learning Engineer"
file_path = "../data/resume1.pdf"

# parse resume file to text.
summary_info, resume_text, extracted_info = utils.parse_resume_file(file_path)

# Initialize interview state machine
interviewSM = dialog_manager.InterviewerStateMachine(company, position, resume_text, extracted_info)

interviewSM = dialog_manager.service(interviewSM, candidate_input="")
interviewSM = dialog_manager.service(interviewSM, candidate_input='../data/User_1_audio.m4a')
interviewSM = dialog_manager.service(interviewSM, candidate_input='../data/User_2_audio.m4a')
interviewSM = dialog_manager.service(interviewSM, candidate_input='../data/User_3_audio.m4a')
interviewSM = dialog_manager.service(interviewSM, candidate_input='../data/User_4_audio.m4a')
interviewSM = dialog_manager.service(interviewSM, candidate_input='../data/User_5_audio.m4a')

