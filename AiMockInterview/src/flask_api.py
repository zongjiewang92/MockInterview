from flask import Flask, request, jsonify, session
from flask_session import Session
import dialog_manager
import os

import utils

app = Flask(__name__)
app.secret_key = 'secret_key'
app.config['SESSION_TYPE'] = 'filesystem'
Session(app)
interview_sm_map = {}

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
    interview_sm = dialog_manager.InterviewerStateMachine(interview_id, company, position, resume_text, extracted_info)
    interview_sm, audio_response_path = dialog_manager.service(interview_sm, candidate_input="")

    interview_sm_map[session_key] = interview_sm

    return jsonify(audio_response_path), 200


@app.route('/getAllQuestions', methods=['POST'])
def get_all_questions():
    data = request.get_json()
    session_key = str(data[INTERVIEW_ID])

    if session_key in interview_sm_map:
        if interview_sm_map[session_key] is not None:
            interview_sm = interview_sm_map[session_key]
            return jsonify(interview_sm.questions), 200

    return jsonify("interview_sm not ready"), 200


@app.route('/service', methods=['POST'])
def next_state():
    data = request.get_json()
    user_input = data.get('user_input', "")
    session_key = str(data[INTERVIEW_ID])

    if session_key in interview_sm_map:
        if interview_sm_map[session_key] is not None:
            interview_sm = interview_sm_map[session_key]

            file_path = set_uploads_path(user_input)
            interview_sm, audio_response_path = dialog_manager.service(interview_sm, candidate_input=file_path)
            interview_sm_map[session_key] = interview_sm
            return jsonify({"candidate_current_answers": interview_sm.candidate_answers[interview_sm.current_question_index-1],
                            "audio_response_path": audio_response_path}), 200

    return jsonify("interview_sm not ready"), 200


@app.route('/getInterviewEvaluation', methods=['POST'])
def get_interview_evaluation():
    data = request.get_json()
    session_key = str(data[INTERVIEW_ID])
    if session_key in interview_sm_map:
        if interview_sm_map[session_key] is not None:
            interview_sm = interview_sm_map[session_key]
            # del interview_sm_map[session_key]

            return jsonify({"evaluation_result": interview_sm.evaluation_result,
                            "evaluation_result_audio": interview_sm.evaluation_result_audio}), 200

    return jsonify("interview_sm not ready"), 200


if __name__ == '__main__':
    app.run(debug=True, port=5000)
