1. Database (SQLite  MockInterview.db)

user
id, user_name, passwd, create_date, email, first_name, last_name, update_date


create table user
(
    id          integer
        constraint id
            primary key autoincrement,
    user_name   TEXT not null,
    passwd      TEXT,
    create_date DATETIME default CURRENT_TIMESTAMP,
    email       TEXT,
    first_name  TEXT,
    last_name   TEXT,
    update_date DATETIME default CURRENT_TIMESTAMP
);

CREATE TRIGGER update_timestamp
    AFTER UPDATE ON user
    FOR EACH ROW
BEGIN
    UPDATE user SET update_date = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;




resume
id, user_id, cv_name, cv_type, cv_directory, cv_context, create_date, update_time

create table resume
(
    id           integer
        constraint id
            primary key autoincrement,
    user_id      integer
        constraint user_id
            references user,
    cv_name      TEXT,
    cv_type      integer,
    cv_directory TEXT,
    cv_context   BLOB,
    create_date DATETIME default CURRENT_TIMESTAMP,
    update_date DATETIME default CURRENT_TIMESTAMP
);
CREATE TRIGGER update_timestamp_resume
    AFTER UPDATE ON resume
    FOR EACH ROW
BEGIN
    UPDATE resume SET update_date = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;



resume_type
id, cv_type_text

interview
id, user_id, cv_id, ai_result, ai_score, report, create_date, company_name, position

question
id, interview_id, number, description, answer_name, answer_directory, answer_context, create_date, question_ai_score, question_ai_result




2. Restful api -> http://localhost:8080/swagger-ui/index.html