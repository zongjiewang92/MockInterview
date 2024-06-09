package com.fdu.mockinterview.service.Imp;

import com.fdu.mockinterview.entity.Question;
import com.fdu.mockinterview.mapper.QuestionMapper;
import com.fdu.mockinterview.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public List<Question> getAllQuestions() {
        return questionMapper.selectAll();
    }


    public List<Question> getAllQuestionByUserIdPages(int pageNum, int pageSize, Integer interviewId) {
        int offset = (pageNum - 1) * pageSize;
        return questionMapper.selectQuestionByInterviewIdPages(offset, pageSize, interviewId);
    }
    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Question createQuestion(Question question) {
        questionMapper.insert(question);
        return questionMapper.selectByPrimaryKey(question.getId());
    }

    @Override
    public Question updateQuestion(Question question) {
        questionMapper.updateByPrimaryKey(question);
        return questionMapper.selectByPrimaryKey(question.getId());
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionMapper.deleteByPrimaryKey(id);
    }
}
