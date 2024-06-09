package com.fdu.mockinterview.entity;

public class Question {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.id
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.interview_id
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private Integer interviewId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.number
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private Integer number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.description
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.answer_name
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String answerName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.answer_directory
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String answerDirectory;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.answer_context
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String answerContext;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.question_ai_score
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String questionAiScore;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.question_ai_result
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String questionAiResult;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.create_date
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question.update_date
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    private String updateDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.id
     *
     * @return the value of question.id
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.id
     *
     * @param id the value for question.id
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.interview_id
     *
     * @return the value of question.interview_id
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public Integer getInterviewId() {
        return interviewId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.interview_id
     *
     * @param interviewId the value for question.interview_id
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.number
     *
     * @return the value of question.number
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.number
     *
     * @param number the value for question.number
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.description
     *
     * @return the value of question.description
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.description
     *
     * @param description the value for question.description
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.answer_name
     *
     * @return the value of question.answer_name
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getAnswerName() {
        return answerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.answer_name
     *
     * @param answerName the value for question.answer_name
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.answer_directory
     *
     * @return the value of question.answer_directory
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getAnswerDirectory() {
        return answerDirectory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.answer_directory
     *
     * @param answerDirectory the value for question.answer_directory
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setAnswerDirectory(String answerDirectory) {
        this.answerDirectory = answerDirectory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.answer_context
     *
     * @return the value of question.answer_context
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getAnswerContext() {
        return answerContext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.answer_context
     *
     * @param answerContext the value for question.answer_context
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.question_ai_score
     *
     * @return the value of question.question_ai_score
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getQuestionAiScore() {
        return questionAiScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.question_ai_score
     *
     * @param questionAiScore the value for question.question_ai_score
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setQuestionAiScore(String questionAiScore) {
        this.questionAiScore = questionAiScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.question_ai_result
     *
     * @return the value of question.question_ai_result
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getQuestionAiResult() {
        return questionAiResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.question_ai_result
     *
     * @param questionAiResult the value for question.question_ai_result
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setQuestionAiResult(String questionAiResult) {
        this.questionAiResult = questionAiResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.create_date
     *
     * @return the value of question.create_date
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.create_date
     *
     * @param createDate the value for question.create_date
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question.update_date
     *
     * @return the value of question.update_date
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question.update_date
     *
     * @param updateDate the value for question.update_date
     *
     * @mbg.generated Sat Jun 08 14:06:10 PDT 2024
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}