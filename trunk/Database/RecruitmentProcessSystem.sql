/*
* 1. CREATE DATABASE
* */
USE [master]
GO
IF EXISTS ( SELECT  *
            FROM    sys.databases
            WHERE   NAME = 'RecruitmentProcessSystem' ) 
    BEGIN
        DROP DATABASE RecruitmentProcessSystem
    END
GO
CREATE DATABASE RecruitmentProcessSystem
GO
USE RecruitmentProcessSystem
GO

/*
* 2. CREATE TABLE
* */
CREATE TABLE Department
(
	DepartmentID NVARCHAR(10) PRIMARY KEY NOT NULL,
	[Name] NVARCHAR(256) NOT NULL,
	Email NVARCHAR(256),
	PhoneNumber NVARCHAR(16),
	LeaderName NVARCHAR(256),
	[Description] NVARCHAR(MAX)	
)

GO

CREATE TABLE Employee
(
	EmployeeID NVARCHAR(10) PRIMARY KEY NOT NULL,
	DepartmentID NVARCHAR(10) REFERENCES Department(DepartmentID) NOT NULL,
	FirstName NVARCHAR(256) NOT NULL,
	LastName NVARCHAR(256) NOT NULL,
	Gender BIT DEFAULT 1,
	DOB DATETIME,
	PhoneNumber NVARCHAR(16),
	Email NVARCHAR(256) UNIQUE NOT NULL,
	[Address] NVARCHAR(1000),
	Position NVARCHAR(256)
)

GO

CREATE TABLE Account
(
	UserName NVARCHAR(128) PRIMARY KEY NOT NULL,
	EmployeeID NVARCHAR(10) REFERENCES Employee(EmployeeID) UNIQUE NOT NULL,
	[Password] NVARCHAR(128) NOT NULL DEFAULT '123456',
	IsChangedPassword BIT DEFAULT 0		
)

GO

CREATE TABLE [Role]
(
	RoleName NVARCHAR(256) PRIMARY KEY NOT NULL
)

GO

CREATE TABLE RoleOfAccount
(
	UserName NVARCHAR(128) REFERENCES Account(UserName) NOT NULL,
	RoleName NVARCHAR(256) REFERENCES [Role](RoleName) NOT NULL,
	Constraint PK_RoleOfAccount PRIMARY KEY(UserName, RoleName)
)

GO

CREATE TABLE Vacancy
(
	VacancyID NVARCHAR(10) PRIMARY KEY NOT NULL,
	DepartmentID NVARCHAR(10) REFERENCES Department (DepartmentID) NOT NULL,
	Title NVARCHAR(256) NOT NULL,
	CreatedDate DATETIME,
	[Status] INT DEFAULT 0,
	NumberRequirement INT,
	Position NVARCHAR(256),
	WorkingPlace NVARCHAR(256),
	WorkType NVARCHAR(256),
	MinimumSalary FLOAT,
	MaximumSalary FLOAT,
	[Description] NVARCHAR(MAX),
	SkillRequirement NVARCHAR(MAX),
	Entitlement NVARCHAR(MAX),
	MinimumAge INT,
	MaximumAge INT,
	Gender BIT,
	Degree NVARCHAR(256),
	YearOfExperience INT,
	ProbationaryPeriod NVARCHAR(256),
	Deadline DATETIME	
)

GO

CREATE TABLE Applicant
(
	ApplicantID NVARCHAR(10) PRIMARY KEY NOT NULL,
	FirstName NVARCHAR(256) NOT NULL,
	LastName NVARCHAR(256) NOT NULL,
	Gender BIT DEFAULT 1,
	DOB DATETIME,
	PhoneNumber NVARCHAR(16),
	Email NVARCHAR(256) UNIQUE NOT NULL,
	[Address] NVARCHAR(1000),
	[Status] INT DEFAULT 0,
	SalaryRequirement FLOAT,
	[Language] NVARCHAR(256),
	YearOfExperience INT,
	Degree NVARCHAR(256),
	CreatedDate DATETIME,
	Skill NVARCHAR(MAX),
	Award NVARCHAR(MAX)
)

GO

CREATE TABLE VacancyOfApplicant
(
	VacancyID NVARCHAR(10) REFERENCES Vacancy (VacancyID) NOT NULL,
	ApplicantID NVARCHAR(10) REFERENCES Applicant (ApplicantID) NOT NULL,
	Constraint PK_VacancyOfApplicant PRIMARY KEY(VacancyID, ApplicantID)
)

GO

CREATE TABLE Schedule
(
	ScheduleID NVARCHAR(10) PRIMARY KEY NOT NULL,
	EmployeeID NVARCHAR(10) REFERENCES Employee (EmployeeID) NOT NULL,
	ApplicantID NVARCHAR(10) REFERENCES Applicant (ApplicantID) NOT NULL,
	[Status] INT DEFAULT 0,
	StartedTime DATETIME,
	EndedTime DATETIME
)

/*
* 3. INSERT DUMP DATA
* */
-- DEPARTMENT TABLE --
INSERT INTO Department
VALUES
(
	N'D0001',
	N'IT Department',
	N'itdep@gmail.com',
	N'034921838',
	N'Do Hoang',
	N''
)
GO
INSERT INTO Department
VALUES
(
	N'D0002',
	N'HRD Department',
	N'hrddep@gmail.com',
	N'034437580',
	N'Bach Luong',
	N''
)
GO
INSERT INTO Department
VALUES
(
	N'D0003',
	N'Business Department',
	N'businessdep@gmail.com',
	N'034876203',
	N'Trinh Tung',
	N''
)
GO
INSERT INTO Department
VALUES
(
	N'D0004',
	N'Finance Department',
	N'financedep@gmail.com',
	N'0347612099',
	N'Bach Luong',
	N''
)
-- END --

GO

-- EMPLOYEE TABLE --
INSERT INTO Employee
VALUES
(
	N'E0000',
	N'D0001',
	N'Do',
	N'Hoang',
	1,
	N'',
	N'',
	N'admin@fpt.aptech.ac.vn',
	N'',
	N'Master Chief'
)
GO
INSERT INTO Employee
VALUES
(
	N'E0001',
	N'D0002',
	N'Do',
	N'Hoang',
	1,
	'11-25-1990',
	N'0974422919',
	N'hoangdp_c00473@fpt.aptech.ac.vn',
	N'Hoang Mai, Ha Noi',
	N'Master Chief'
)
GO
INSERT INTO Employee
VALUES
(
	N'E0002',
	N'D0002',
	N'Tran',
	N'Vinh',
	1,
	'6-12-1986',
	N'0987658901',
	N'tranvinh@fpt.aptech.ac.vn',
	N'Cau Giay, Ha Noi',
	N'Employee'
)
GO
INSERT INTO Employee
VALUES
(
	N'E0003',
	N'D0002',
	N'Nguyen',
	N'Anh',
	0,
	'10-3-1993',
	N'0913387456',
	N'nguyenanh@fpt.aptech.ac.vn',
	N'Hoan Kiem, Ha Noi',
	N'Employee'
)
-- END --

GO

-- ACCOUNT TABLE --
INSERT INTO Account
VALUES
(
	'admin',
	'E0000',
	'123456',
	0
)
GO
INSERT INTO Account
VALUES
(
	'dohoang90',
	'E0001',
	'123456',
	0
)
GO
INSERT INTO Account
VALUES
(
	'tranvinh',
	'E0002',
	'123456',
	0
)
GO
INSERT INTO Account
VALUES
(
	'nguyenanh',
	'E0003',
	'123456',
	0
)
-- END --
GO

-- ROLE TABLE --
INSERT INTO [Role]
VALUES
(
	N'Administrator'
)
GO
INSERT INTO [Role]
VALUES
(
	N'HR Group'
)
GO
INSERT INTO [Role]
VALUES
(
	N'Interviewer'
)
-- END --

GO

-- ROLE_OF_ACCOUNT TABLE --
INSERT INTO RoleOfAccount
VALUES
(
	N'admin',
	N'Administrator'
)
GO
INSERT INTO RoleOfAccount
VALUES
(
	N'dohoang90',
	N'HR Group'
)
GO
INSERT INTO RoleOfAccount
VALUES
(
	N'tranvinh',
	N'Interviewer'
)
GO
INSERT INTO RoleOfAccount
VALUES
(
	N'nguyenanh',
	N'Interviewer'
)
-- END --

GO

-- VACANCY TABLE --
INSERT INTO Vacancy
VALUES
(
	N'V0001',
	N'D0001',
	N'Tuyển lập trình viên PHP',
	'4-2-2012',
	0,
	5,
	N'Nhân viên',
	N'Hà Nội',
	N'Part Time',
	200,
	500,
	N'Chúng tôi cần tuyển lập trình viên PHP để phát triển các dự án của Công ty như:
- Các Website của công ty.
- Các Website cho các game.
- Các hệ thống kết nối với các đối tác như FPT, VTC, VDC, Zing...
- Các website portal về Game
...
Làm việc dựa trên các framework có sẵn hoặc code cơ bản từ đầu tuỳ dự án.

Sẽ phỏng vấn liên tục cho đến khi tuyển dụng được theo yêu cầu công ty. Chỉ hồ sơ đạt yêu cầu mới được liên hệ phỏng vấn.',
	N'- Tốt nghiệp Đại học/Cao đẳng chuyên ngành CNTT hoặc các chuyên ngành có liên quan.
- Tiếng Anh đọc hiểu tài liệu.
- Có khả năng phối hợp nhóm.
- Năng động, sáng tạo. Tư duy rõ ràng, tích cực.
- Có trách nhiệm và luôn cam kết thực hiện công việc tốt nhất.
- Chịu được áp lực cao trong công việc, sẵn sàng làm thêm giờ nếu cần.

- Có kiến thức lập trình web, khả năng xử lý tốt HTML và CSS.
- Làm tốt các công việc lập trình PHP, PHP OOP...
- Thành thạo kêt nối các cơ sở dữ liệu SQL và hiểu biết về No SQL.
- Hiểu biết về các cơ chế bảo mật.

- Ưu tiên các ứng viên có kinh nghiệm với MVC framework (Zend-framework, Drupal, CakePHP, Joomla, Symfony…). 
- Ưu tiên những ứng viên thành thạo các ngôn ngữ lập trình khác như Java, .Net...
- Ưu tiên những ứng viên có kiến thức tốt về hệ thống mạng, OS...
- Ưu tiên những ứng viên đã từng làm việc trong môi trường nước ngoài.',
	N'+ Môi trường làm việc năng động, sáng tạo và chuyên nghiệp.
+ Lương & Thưởng (có thể đạt mức độ rất cao tuỳ nếu đóng góp lớn cho công ty)
+ Các bảo hiểm theo luật định.
+ Thưởng các ngày Lễ, Tết.
+ Các chế độ xã hội chung hàng năm.
+ Ăn trưa tại công ty.
+ Thử việc được nhận 80% lương chính thức.',
	18,
	30,
	99,
	N'Không yêu cầu',
	0,
	N'Thử việc 2 tháng',
	'4-22-2012'
)
GO
INSERT INTO Vacancy
VALUES
(
	N'V0002',
	N'D0001',
	N'Quản trị hệ thống',
	'4-2-2012',
	0,
	1,
	N'Nhân viên',
	N'Hà Nội',
	N'Full Time',
	550,
	850,
	N'- Quản trị mạng, hệ thống server của công ty (hiện tại > 80 server).
- Quản trị mạng giữa các Server và kết nối ra Internet.
- Quản trị hệ thống, bảo mật, quản lý và monitor server.
- Quản trị hệ thống, bảo mật tài nguyên, máy tính làm việc tại công ty.

Sẽ phỏng vấn liên tục cho đến khi tuyển dụng được theo yêu cầu công ty. Chỉ hồ sơ đạt yêu cầu mới được liên hệ phỏng vấn.',
	N'- Tốt nghiệp Đại học/Cao đẳng chuyên ngành CNTT hoặc các chuyên ngành có liên quan.
- Tiếng Anh đọc hiểu tài liệu.
- Có khả năng phối hợp nhóm.
- Năng động, sáng tạo. Tư duy rõ ràng, tích cực.
- Có trách nhiệm và luôn cam kết thực hiện công việc tốt nhất.
- Chịu được áp lực cao trong công việc, sẵn sàng làm thêm giờ nếu cần.
- Hiểu biết về các cơ chế bảo mật, Firewall, xây dựng hệ thống cân bằng tải, backup... trên Linux và Windows
- Hiểu biết và cấu hình các hệ thống mạng, sw, server...

- Hiểu biết về các phần cứng và quản trị, các OS (win, linux, BSD...)
- Ưu tiên các ứng viên có kiến thức lập trình web, khả năng xử lý tốt HTML và CSS, làm việc trên các ngôn ngữ lập trình và các cơ sở dữ liệu (sql, No Sql)',
	N'+ Môi trường làm việc năng động, sáng tạo và chuyên nghiệp.
+ Lương & Thưởng (có thể đạt mức độ rất cao tuỳ nếu đóng góp lớn cho công ty)
+ Các bảo hiểm theo luật định.
+ Thưởng các ngày Lễ, Tết.
+ Các chế độ xã hội chung hàng năm.
+ Ăn trưa tại công ty.
+ Thử việc được nhận 70% lương chính thức.',
	24,
	30,
	99,
	N'Không yêu cầu',
	2,
	N'Thử việc 2 tháng',
	'4-12-2012'
)
GO
INSERT INTO Vacancy
VALUES
(
	N'V0003',
	N'D0003',
	N'Nhân viên Kinh doanh',
	'4-2-2012',
	0,
	6,
	N'Nhân viên',
	N'Hà Nội',
	'Full Time',
	150,
	300,
	N'-	Tìm kiếm, khai thác khách hàng
-	Giao dịch trực tiếp khách hàng, giới thiệu sản phẩm-dịch vụ phần mềm BRAVO cho khách hàng.
-	Xúc tiến bán sản phẩm và dịch vụ phần mềm cho khách hàng
-	Phân tích thị trường, đối thủ cạnh tranh. 
-	Thực hiện các công việc theo đúng tiêu chuẩn ISO của công ty ',
	N'- Tốt nghiệp Đại học, Cao đẳng
-	Có khả năng giao tiếp tốt, giao tiếp tiếng anh.
-	Đam mê và yêu thích công việc kinh doanh.	
-	Chăm chỉ, cẩn thận, nhanh nhẹn. 
-	Ưu tiên các ứng viên có kinh nghiệm trong lĩnh vực bán hàng và làm các công việc tương tự.',
	N'+	Lương khởi điểm: 4.200.000 đồng/tháng (có lộ trình tăng lương rõ ràng). Đối với các cá nhân có thâm niên công tác, mức lương sẽ được thỏa thuận.
+	Có thưởng doanh số (theo hệ số được Công ty và phòng quy định).
+	Có phụ cấp điện thoại, phụ cấp ăn trưa (~ 500 000 đồng/tháng), phụ cấp chuyên cần (200 000 đồng/tháng). 
+	Có phụ cấp công việc, phụ cấp chức vụ (nếu là cấp cán bộ quản lý).
+	Được ký hợp đồng lao động và hưởng các quyền lợi đúng luật lao động (BHXH, BHYT...).
+	Được hưởng các chế độ phúc lợi hàng năm của công ty (Liên hoan, nghỉ mát, hoạt động văn thể mỹ…)
+	Cơ hội làm việc trong môi trường năng động, công việc ổn định, phát huy nhiều khả năng, được đào tạo và học tập nâng cao kiến thức nghề nghiệp.',
	22,
	25,
	99,
	N'Cao đẳng',
	0,
	'Thử việc 3 tháng',
	'4-15-2012'
)
GO
INSERT INTO Vacancy
VALUES
(
	N'V0004',
	N'D0001',
	N'Lập trình viên JAVA, C, C++, C#, .NET, Tester',
	'4-2-2012',
	0,
	15,
	N'Nhân viên',
	N'Hà Nội',
	N'Full Time',
	350,
	700,
	N'1. Lập trình viên .NET C#/ASP.NET - Số lượng: 5
2. JAVA: Số lượng: 3
3. ANSI-C/ C++: Số lượng: 3
4. Leader C# / ASP.NET: Số lượng 2 (Y.cầu kinh nghiệm 2 năm trở lên)
5. Tester: Số lượng 2 - (Ưu tiên nữ)',
	N'Yêu thích lập trình , chịu được áp lực công việc .',
	N'Là thành viên của SaoMai J.S.C, các bạn có cơ hội được phát triển bản thân, được học hỏi kinh nghiệm và làm việc trong một môi trường chuyên nghiệp, hiện đại, có nhiều cơ hội thăng tiến và rất nhiều thuận lợi khác:
-	Được hỗ trợ đào tạo tiếng Nhật, tiếng Anh tại trung tâm đào tạo nguồn nhân lực SAOMAI.
-	Có cơ hội tiếp xúc làm việc trực tiếp với các chuyên gia Nhật bản và Mỹ. 
-	Có cơ hội tham gia các khóa đào tạo kỹ sư phần mềm tại Nhật bản hàng năm.
-	Các cơ hội khác.',
	18,
	22,
	99,
	N'Cao đẳng',
	1,
	'Trao đổi trực tiếp khi phỏng vấn',
	'4-30-2012'
)
-- END --

GO

-- APPLICANT TABLE --
INSERT INTO Applicant
VALUES
(
	N'A0001',
	N'Trinh',
	N'Tuan',
	1,
	N'10-20-1987',
	N'0248467393',
	'tuan@gmail.com',
	'Cao Bang',
	0,
	300,
	N'English, Japanness',
	1,
	N'Cao đẳng',
	'4-6-2012',
	N'- Lap trinh duoc nhieu ngon ngu JAVA, .NET ...',
	N'Doat giai nhi cuoc thi BEst Coder tai Aptech FPT'
)
GO
INSERT INTO Applicant
VALUES
(
	N'A0002',
	N'John',
	N'Hart',
	1,
	'2-8-1991',
	N'029876398',
	N'hart@gmail.com',
	N'HCM City',
	0,
	800,
	N'English, French, VietNammess',
	2,
	N'University',
	N'4-8-2012',
	N'- Nhanh nhen, giao tiep tot, het minh voi cong viec',
	N'khong co'
)
GO
INSERT INTO Applicant
VALUES
(
	N'A0003',
	N'Nguyen',
	N'Phuong Ha',
	0,
	N'2-5-1995',
	N'098764820',
	N'haphuong@gmail.com',
	N'Ha Noi',
	0,
	500,
	N'English, Japanness, French, Spanish, Chinness',
	1,
	N'Dai hoc',
	'4-7-2012',
	N'- Noi duoc nhieu thu tieng, giao tiep tot',
	N''
)
GO
INSERT INTO Applicant
VALUES
(
	N'A0004',
	N'Vu',
	N'Minh Anh',
	0,
	N'5-12-1994',
	N'094127923',
	N'minhanh@gmail.com',
	N'Thanh Hoa',
	0,
	320,
	N'English',
	0,
	N'Cao Dang',
	'4-8-2012',
	N'- Ngoai giao tot, tu tin, het long vi cong viec',
	N'Vao vong chung ket cuoc thi Tim kiem doanh nhan tre to chuc vao thang 3/2012 tai Ha Noi'
)
-- END --

GO

-- VACANCY_OF_APPLICANT TABLE --
INSERT INTO VacancyOfApplicant
VALUES
(
	N'V0004',
	N'A0001'
)
GO
INSERT INTO VacancyOfApplicant
VALUES
(
	N'V0002',
	N'A0002'
)
GO
INSERT INTO VacancyOfApplicant
VALUES
(
	N'V0003',
	N'A0003'
)
GO
INSERT INTO VacancyOfApplicant
VALUES
(
	N'V0003',
	N'A0004'
)
-- END --

GO

-- SCHEDULING INTERVIEW TABLE --
INSERT INTO Schedule
VALUES
(
	N'S0001',
	N'E0002',
	N'A0001',
	0,
	'5-1-2012 8:0:0',
	'5-1-2012 9:30:0'
)
GO
INSERT INTO Schedule
VALUES
(
	N'S0002',
	N'E0002',
	N'A0002',
	0,
	'5-1-2012 10:0:0',
	'5-1-2012 11:30:0'
)
GO
INSERT INTO Schedule
VALUES
(
	N'S0003',
	N'E0001',
	N'A0003',
	0,
	'6-1-2012 8:0:0',
	'6-1-2012 9:0:0'
)
GO
INSERT INTO Schedule
VALUES
(
	N'S0004',
	N'E0002',
	N'A0004',
	0,
	'6-1-2012 14:0:0',
	'6-1-2012 15:30:0'
)
-- END --