from flask import Flask, request, jsonify, session
import json
from flask_session import Session
import dialog_manager
import pickle

import utils

app = Flask(__name__)
app.secret_key = 'your_secret_key_here'
app.config['SESSION_TYPE'] = 'filesystem'
Session(app)


@app.route('/parseResumeFile', methods=['GET'])
def parse_resume_file():
    summary_info, resume_text, extracted_info = utils.parse_resume_file(request.args.get('file_path'))
    return jsonify({"summary_info": summary_info, "resume_text": resume_text, "extracted_info": extracted_info}), 200


@app.route('/initialize', methods=['POST'])
def initialize():
    data = request.get_json()
    company = data['company']
    position = data['position']
    resume_text = data['resume_text']
    extracted_info = data['extracted_info']
    interviewSM = dialog_manager.InterviewerStateMachine(company, position, resume_text, extracted_info)
    session['interviewSM'] = pickle.dumps(interviewSM)
    return jsonify({"message": "State Machine Initialized"}), 200


@app.route('/getAllQuestions', methods=['GET'])
def initialize():
    interviewSM = pickle.loads(session['interviewSM'])
    return jsonify({"questions": interviewSM.questions}), 200


@app.route('/service', methods=['POST'])
def next_state():
    data = request.get_json()
    user_input = data.get('user_input', "")
    interviewSM = pickle.loads(session['interviewSM'])
    interviewSM = dialog_manager.service(interviewSM, candidate_input=user_input)
    session['interviewSM'] = pickle.dumps(interviewSM)
    return jsonify({"message": "State Machine Updated"}), 200


@app.route('/getInterviewEvaluation', methods=['GET'])
def get_interview_evaluation():
    interviewSM = pickle.loads(session['interviewSM'])
    return jsonify({"evaluation_result": interviewSM.evaluation_result, "evaluation_result_audio": interviewSM.evaluation_result_audio}), 200


if __name__ == '__main__':
    app.run(debug=True, port=5000)
