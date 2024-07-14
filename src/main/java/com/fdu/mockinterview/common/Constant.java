package com.fdu.mockinterview.common;

import java.util.concurrent.ConcurrentHashMap;

public class Constant {


    public static final String INTERVIEW_ID = "interview_id";
    public static final String candidate_current_answers = "candidate_current_answers";
    public static final String audio_response_path = "audio_response_path";
    public static final String evaluation_result = "evaluation_result";
    public static final String evaluation_result_audio = "evaluation_result_audio";

    public static ConcurrentHashMap<String, String> sessionMap =new ConcurrentHashMap<String, String>();

}
