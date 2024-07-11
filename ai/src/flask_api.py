from flask import Flask, request, jsonify, session
import json
from flask_session import Session
import dialog_manager
import pickle

app = Flask(__name__)
app.secret_key = 'your_secret_key_here'
app.config['SESSION_TYPE'] = 'filesystem'
Session(app)

# Create an instance of the InterviewerStateMachine class
state_machine = None


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


@app.route('/service', methods=['POST'])
def next_state():
    data = request.get_json()
    user_input = data.get('user_input', "")
    state_machine_data = data['state_machine_data']
    interviewSM = pickle.loads(session['interviewSM'])
    interviewSM = dialog_manager.service(interviewSM, candidate_input=user_input)
    session['interviewSM'] = pickle.dumps(interviewSM)
    return jsonify({"message": "State Machine Updated"}), 200


if __name__ == '__main__':
    app.run(debug=True, port=5000)
