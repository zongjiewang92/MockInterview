import os
import openai
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
from langchain_community.document_loaders import PyPDFLoader, Docx2txtLoader

openai.api_key = '' 

def conduct_interview(extracted_info, company, position):
    questions = []

    llm = OpenAI(api_key=openai.api_key)
    candidate_answers = []