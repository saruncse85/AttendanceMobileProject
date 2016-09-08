CREATE TABLE ATTN_BATCH (
	_id INTEGER PRIMARY_KEY,
	name TEXT,
	deleted_flag TEXT,
	created_on LONG,
	created_by TEXT
)

CREATE TABLE ATTN_STUDENT (
	_id INTEGER PRIMARY_KEY,
	name TEXT,
	batch_name TEXT,
	days_per_week INTEGER,
	parent_name TEXT,
	start_date LONG,
	end_date LONG
)

CREATE TABLE ATTN_ATTENDANCE (
	_id INTEGER PRIMARY_KEY,
	batch_name TEXT, 
	student_name TEXT, 
	value_date LONG, 
	present_absent	 INTEGER DEFAULT 0, 	
	created_on LONG, 
	created_by TEXT, 
	deleted TEXT
);