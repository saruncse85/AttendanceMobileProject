SELECT * FROM ATTN_ATTENDANCE

SELECT * FROM ATTN_ATTENDANCE WHERE BATCH_NAME = 'Batch1' AND STUDENT_NAME = 'Tharshitha' AND VALUE_DATE >= '06-01-2016' and VALUE_DATE <='06-30-2016'

SELECT * FROM ATTN_ATTENDANCE WHERE BATCH_NAME = 'Batch1' AND STUDENT_NAME = 'Tharshitha' AND VALUE_DATE BETWEEN '06-01-2016' and '06-30-2016'


select * from ATTN_STUDENT WHERE NAME='Tharshitha' 

select * from ATTN_ATTENDANCE WHERE BATCH_NAME = 'Batch1' AND STUDENT_NAME = 'Tharshitha'

select COUNT(*) FROM ATTN_ATTENDANCE WHERE BATCH_NAME = 'Batch1' AND STUDENT_NAME = 'Tharshitha' AND VALUE_DATE IN ('06-02-2016','06-07-2016','06-09-2016','06-14-2016','06-19-2016','06-21-2016','06-23-2016','06-28-2016')


SELECT VALUE_DATE FROM ATTN_ATTENDANCE WHERE BATCH_NAME = 'Batch1' AND STUDENT_NAME ='Stu1'  AND VALUE_DATE BETWEEN '07-01-2016' AND '07-31-2016' AND PRESENT_ABSENT = 1

SELECT RATE FROM ATTN_STUDENT WHERE BATCH_NAME = 'Batch1' AND NAME = 'Student3'