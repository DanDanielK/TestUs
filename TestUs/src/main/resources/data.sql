INSERT INTO my_user(account_locked,id,address,email,first_name,last_name,password,phone,role)
VALUES (false,1,'admin_adress','admin@testus.com','fadmin','ladmin','$2a$12$4v3b/MxU8HAqg02DIccLSeRnBoSCiG/2Sk59p48XyXy/28IbnwDfa','123456789','ADMIN')ON CONFLICT (id) DO NOTHING;

INSERT INTO my_user(account_locked,id,address,email,first_name,last_name,password,phone,role)
VALUES (false,2,'student_adress','student@testus.com','fstudent','lstudent','$2a$12$HGwVAldnK5aMIBVGVQkbIe2f5FB5uvNAUIedrIg.hkkUp2bCzgfVG','123456789','STUDENT')ON CONFLICT (id) DO NOTHING;

INSERT INTO my_user(account_locked,id,address,email,first_name,last_name,password,phone,role)
VALUES (false,3,'teacher_adress','teacher@testus.com','fteacher','lteacher','$2a$12$D/1tEbPdpQvW6vONq/DwmeO.92ZRmRr.rtKfoPIXPCamNny1YO586','123456789','TEACHER')
ON CONFLICT (id) DO NOTHING;

INSERT INTO student(id,user_id)
VALUES (DEFAULT,2)ON CONFLICT (user_id) DO NOTHING;

INSERT INTO teacher(id,user_id)
VALUES (DEFAULT,3)ON CONFLICT (user_id) DO NOTHING;

INSERT INTO course(id,teacher_id,name,description)
VALUES (1,1,'Test course','Test course description') ON CONFLICT (id) DO NOTHING;