1. Database (SQLite  MockInterview.db)

(1). user
id, user_name, passwd, create_date, email, first_name, last_name, update_date


create table user
(
    id          integer
        constraint id
            primary key autoincrement,
    user_name   TEXT not null,
    passwd      TEXT,  # bcrypt, salted and hashed password
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




(2). resume
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



(3). resume_type
id, cv_type_text

create table resume_type
(
    id           integer
        constraint id
            primary key autoincrement,
    cv_type_text TEXT,
    create_date DATETIME default CURRENT_TIMESTAMP,
    update_date DATETIME default CURRENT_TIMESTAMP
);
CREATE TRIGGER update_timestamp_resume_type
    AFTER UPDATE ON resume_type
    FOR EACH ROW
BEGIN
    UPDATE resume_type SET update_date = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;





(4). interview
id, user_id, cv_id, ai_score, ai_result, report, company_name, position, create_date, update_date

create table interview
(
    id           integer
        constraint id
            primary key autoincrement,
    user_id      integer,
    cv_id        integer,
    ai_score     TEXT,
    ai_result    BLOB,
    report       BLOB,
    company_name TEXT,
    position     TEXT,
    create_date DATETIME default CURRENT_TIMESTAMP,
    update_date DATETIME default CURRENT_TIMESTAMP
);

CREATE TRIGGER update_timestamp_interview
    AFTER UPDATE ON interview
    FOR EACH ROW
BEGIN
    UPDATE interview SET update_date = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;




(5). question
id, interview_id, number, description, answer_name, answer_directory, answer_context, question_ai_score, question_ai_result, create_date, update_date

create table question
(
    id                 integer
        constraint id
            primary key autoincrement,
    interview_id       integer
        constraint interview_id
            references interview,
    number             integer,
    description        BLOB,
    answer_name        TEXT,
    answer_directory   TEXT,
    answer_context     BLOB,
    question_ai_score  TEXT,
    question_ai_result BLOB,
    create_date DATETIME default CURRENT_TIMESTAMP,
    update_date DATETIME default CURRENT_TIMESTAMP
);

CREATE TRIGGER update_timestamp_question
    AFTER UPDATE ON question
    FOR EACH ROW
BEGIN
    UPDATE question SET update_date = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;



2. Restful api -> http://localhost:8080/swagger-ui/index.html


3. For any testing purpose, and you do not want to ues authentication, just modify "auth.SecurityConfig.java" -> filterChain -> .anyRequest().permitAll()