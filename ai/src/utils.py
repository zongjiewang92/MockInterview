import openai
import constant
from pathlib import Path
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
from langchain_community.document_loaders import PyPDFLoader, Docx2txtLoader

def extract_info(text):
    prompt_template = PromptTemplate(

    )
    llm = OpenAI(api_key=openai.api_key)
    chain = LLMChain(prompt=prompt_template, llm=llm)
    result = chain.run({"resume_text": text})

    output = result.strip() if isinstance(result, str) else result.get('output', '').strip()
    lines = output.split('\n')

    extracted_info = {}

    return summary_info, resume_text, extracted_info


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

    return audio_path