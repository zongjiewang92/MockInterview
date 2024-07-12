import openai
import constant
from pathlib import Path
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
from langchain_community.document_loaders import PyPDFLoader, Docx2txtLoader

def extract_info(text):
    prompt_template = PromptTemplate(
        input_variables=["resume_text"],
        template="""
        Extract the following information from the resume text:
        1. Name
        2. Education (including school name and graduation date)
        3. Work experience (including company name and duration)
        
        Resume text:
        {resume_text}
        
        Please provide the extracted information in the following format:
        'Name: [Name Here]'
        'Education: [Education Details Here]'
        'Work Experience: [Work Experience Details Here]'
        """.format(resume_text=text)
    )
    llm = OpenAI(api_key=openai.api_key)
    chain = LLMChain(prompt=prompt_template, llm=llm)
    result = chain.run({"resume_text": text})

    output = result.strip() if isinstance(result, str) else result.get('output', '').strip()
    lines = output.split('\n')

    extracted_info = {}
    for line in lines:
        print(f"Processing line: {line}")  # Debugging each line
        if "Name:" in line:
            extracted_info["Name"] = line.split("Name: ")[1].strip()
        elif "Education:" in line:
            try:
                extracted_info["Education"] = line.split("Education: ")[1].strip()
            except IndexError:
                print("Error parsing Work Experience. Check formatting.")
                extracted_info["Work Experience"] = "Error parsing. Check data."                
        elif "Work Experience:" in line:
            try:
                extracted_info["Work Experience"] = line.split("Work Experience: ")[1].strip()
            except IndexError:
                print("Error parsing Work Experience. Check formatting.")
                extracted_info["Work Experience"] = "Error parsing. Check data."
    return result, extracted_info


def parse_resume_file(file_path):
    if file_path.endswith('.pdf'):
        loader = PyPDFLoader(file_path)
    elif file_path.endswith('.docx'):
        loader = Docx2txtLoader(file_path)
    else:
        print("Unsupported file format.")
        return
    documents = loader.load()
    resume_text = " ".join([doc.page_content for doc in documents])
    summary_info, extracted_info = extract_info(resume_text)
    return summary_info, resume_text, extracted_info


def call_asr_api(audio_path):
    client = openai.OpenAI(api_key=constant.jyOpenAIKey)
    audio_file= open(audio_path, "rb")
    transcription = client.audio.transcriptions.create(model="whisper-1", file=audio_file, language="en")
    print(f"Candidate audio asr result:{transcription.text}")
    return transcription.text


def call_tts_api(text, audio_path):
    client = openai.OpenAI(api_key=constant.jyOpenAIKey)
    speech_file_path = Path(audio_path)
    response = client.audio.speech.create(model="tts-1", voice="alloy", input=text)
    response.stream_to_file(speech_file_path)
    print(f"AI response: {text}")
    return audio_path