from flask import Flask, request, jsonify, session
from flask_session import Session
import dialog_manager
import os

import utils

app = Flask(__name__)
app.secret_key = 'secret_key'
app.config['SESSION_TYPE'] = 'filesystem'
Session(app)
interviewSMMap = {}

INTERVIEW_ID = 'interview_id'

CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))


def set_uploads_path(input_path):
    parent_dir = os.path.abspath(os.path.join(CURRENT_DIR, '..', '..'))

    return os.path.join(parent_dir, input_path)


@app.route('/parseResumeFile', methods=['GET'])
def parse_resume_file():
    summary_info, resume_text, extracted_info = utils.parse_resume_file(request.args.get('file_path'))
    return jsonify({"summary_info": summary_info, "resume_text": resume_text, "extracted_info": extracted_info}), 200


@app.route('/initialize', methods=['POST'])
def initialize():
    data = request.get_json()
    interview_id = data['interview_id']
    company = data['company']
    position = data['position']
    resume_text = data['resume_text']
    extracted_info = data['extracted_info']
    session_key = str(data[INTERVIEW_ID])
    interviewSM = dialog_manager.InterviewerStateMachine(interview_id, company, position, resume_text, extracted_info)
    interviewSMMap[session_key] = interviewSM

    return jsonify({"message": "State Machine Initialized"}), 200


@app.route('/getAllQuestions', methods=['POST'])
def get_all_questions():
    data = request.get_json()
    session_key = str(data[INTERVIEW_ID])

    if interviewSMMap[session_key] is None:
        jsonify("interviewSM not ready"), 200
    interviewSM = interviewSMMap[session_key]

    return jsonify(interviewSM.questions), 200


@app.route('/service', methods=['POST'])
def next_state():
    data = request.get_json()
    user_input = data.get('user_input', "")
    session_key = str(data[INTERVIEW_ID])
    if interviewSMMap is None or interviewSMMap[session_key] is None:
        jsonify("interviewSM not ready"), 200
    interviewSM = interviewSMMap[session_key]

    file_path = set_uploads_path(user_input)
    interviewSM = dialog_manager.service(interviewSM, candidate_input=file_path)

    interviewSMMap[session_key] = interviewSM

    return jsonify({"message": "State Machine Updated"}), 200


@app.route('/getInterviewEvaluation', methods=['POST'])
def get_interview_evaluation():
    data = request.get_json()
    session_key = str(data[INTERVIEW_ID])
    if interviewSMMap[session_key] is None:
        jsonify("interviewSM not ready"), 200
    interviewSM = interviewSMMap[session_key]
    return jsonify({"evaluation_result": interviewSM.evaluation_result,
                    "evaluation_result_audio": interviewSM.evaluation_result_audio}), 200


if __name__ == '__main__':
    app.run(debug=True, port=5000)
