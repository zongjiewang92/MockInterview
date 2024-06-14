import os
import openai
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
from langchain_community.document_loaders import PyPDFLoader, Docx2txtLoader

openai.api_key = '' 

def conduct_interview(extracted_info, company, position):
    questions = [
        "Please introduce yourself.",
        f"Why do you want to join {company}?",
        f"What makes you think you are a good fit for the {position} position?",
        "Based on your education and work experience, describe a challenging project you worked on and how you handled it.",
        "Tell me about a time when you had to learn a new technology or skill quickly. How did you approach it?"
    ]

    llm = OpenAI(api_key=openai.api_key)
    candidate_answers = []