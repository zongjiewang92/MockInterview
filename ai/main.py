import os
import openai
from langchain.chains import LLMChain
from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
from langchain_community.document_loaders import PyPDFLoader, Docx2txtLoader

openai.api_key = 'key' 

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

    for i, question in enumerate(questions):
        prompt_template = PromptTemplate(
            input_variables=["info", "question"],
            template="""
            You are an interviewer. The candidate's resume information is as follows:
            
            {info}
            
            Ask the following question:
            {question}
            """
        )
        
        chain = LLMChain(prompt=prompt_template, llm=llm)

        if i == 0:
            result = chain.run({"info": extracted_info, "question": question})
        else:
            result = chain.run({"info": extracted_info + f"\nCandidate's previous answer: {candidate_answers[-1]}", "question": question})

        print(f"Interviewer: {question}")
        candidate_answer = input("Candidate: ")
        candidate_answers.append(candidate_answer)
        print(f"Candidate: {candidate_answer}\n")

    prompt_template_evaluation = PromptTemplate(
        input_variables=["info", "answers"],
        template="""
        You are an interviewer. The candidate's resume information is as follows:
        
        {info}
        
        The candidate's answers to the interview questions are as follows:
        {answers}
        
        Evaluate the candidate based on their answers and give a STAR rating.
        """
    )

    answers = "\n".join([f"Q: {questions[i]}\nA: {answer}" for i, answer in enumerate(candidate_answers)])
    chain = LLMChain(prompt=prompt_template_evaluation, llm=llm)
    evaluation_result = chain.run({"info": extracted_info, "answers": answers})

    print("Evaluation result:")
    print(evaluation_result)


def main(file_path, company, position):
    if file_path.endswith('.pdf'):
        loader = PyPDFLoader(file_path)
    elif file_path.endswith('.docx'):
        loader = Docx2txtLoader(file_path)
    else:
        print("Unsupported file format.")
        return
    
    documents = loader.load()
    resume_text = " ".join([doc.page_content for doc in documents])
    extracted_info = extract_info(resume_text)

    conduct_interview(extracted_info, company, position)