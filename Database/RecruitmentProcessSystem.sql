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
	[Password] NVARCHAR(128) NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e',
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

CREATE TABLE Interview
(
	InterviewID NVARCHAR(10) PRIMARY KEY NOT NULL,
	EmployeeID NVARCHAR(10) REFERENCES Employee (EmployeeID),
	ApplicantID NVARCHAR(10) REFERENCES Applicant (ApplicantID) NOT NULL,
	VacancyID NVARCHAR(10) REFERENCES Vacancy (VacancyID) NOT NULL,
	[Status] INT DEFAULT 0,
	AVStatus INT DEFAULT 0,
	StartedTime DATETIME,
	EndedTime DATETIME
)

/*
* 3. INSERT DUMP DATA
* */
-- DEPARTMENT TABLE --
/****** Object:  Table [dbo].[Department]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Department] ([DepartmentID], [Name], [Email], [PhoneNumber], [LeaderName], [Description]) VALUES (N'D0001', N'IT Department', N'itdep@gmail.com', N'034921838', N'Do Hoang', N'')
INSERT [dbo].[Department] ([DepartmentID], [Name], [Email], [PhoneNumber], [LeaderName], [Description]) VALUES (N'D0002', N'HRD Department', N'hrddep@gmail.com', N'034437580', N'Bach Luong', N'')
INSERT [dbo].[Department] ([DepartmentID], [Name], [Email], [PhoneNumber], [LeaderName], [Description]) VALUES (N'D0003', N'Business Department', N'businessdep@gmail.com', N'034876203', N'Trinh Tung', N'')
INSERT [dbo].[Department] ([DepartmentID], [Name], [Email], [PhoneNumber], [LeaderName], [Description]) VALUES (N'D0004', N'Finance Department', N'financedep@gmail.com', N'0347612099', N'Bach Luong', N'')
-- END --

GO

-- EMPLOYEE TABLE --
/****** Object:  Table [dbo].[Employee]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0000', N'D0001', N'Do', N'Hoang', 1, CAST(0x0000000000000000 AS DateTime), N'', N'admin@fpt.aptech.ac.vn', N'', N'Master Chief')
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0001', N'D0002', N'Do', N'Hoang', 1, CAST(0x000081B000000000 AS DateTime), N'0974422919', N'hoangdp_c00473@fpt.aptech.ac.vn', N'Hoang Mai, Ha Noi', N'Master Chief')
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0002', N'D0002', N'Tran', N'Vinh', 1, CAST(0x00007B5500000000 AS DateTime), N'0987658901', N'tranvinh@fpt.aptech.ac.vn', N'Cau Giay, Ha Noi', N'Employee')
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0003', N'D0002', N'Nguyen', N'Anh', 0, CAST(0x000085C300000000 AS DateTime), N'0913387456', N'nguyenanh@fpt.aptech.ac.vn', N'Hoan Kiem, Ha Noi', N'Employee')
-- END --

GO

-- ACCOUNT TABLE --
/****** Object:  Table [dbo].[Account]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Account] ([UserName], [EmployeeID], [Password], [IsChangedPassword]) VALUES (N'admin', N'E0000', N'e10adc3949ba59abbe56e057f20f883e', 0)
INSERT [dbo].[Account] ([UserName], [EmployeeID], [Password], [IsChangedPassword]) VALUES (N'hr', N'E0001', N'e10adc3949ba59abbe56e057f20f883e', 0)
INSERT [dbo].[Account] ([UserName], [EmployeeID], [Password], [IsChangedPassword]) VALUES (N'interviewer', N'E0002', N'e10adc3949ba59abbe56e057f20f883e', 0)
INSERT [dbo].[Account] ([UserName], [EmployeeID], [Password], [IsChangedPassword]) VALUES (N'nguyenanh', N'E0003', N'e10adc3949ba59abbe56e057f20f883e', 0)
-- END --
GO

-- ROLE TABLE --
/****** Object:  Table [dbo].[Role]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Role] ([RoleName]) VALUES (N'Administrator')
INSERT [dbo].[Role] ([RoleName]) VALUES (N'HR Group')
INSERT [dbo].[Role] ([RoleName]) VALUES (N'Interviewer')
-- END --

GO

-- ROLE_OF_ACCOUNT TABLE --
/****** Object:  Table [dbo].[RoleOfAccount]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[RoleOfAccount] ([UserName], [RoleName]) VALUES (N'admin', N'Administrator')
INSERT [dbo].[RoleOfAccount] ([UserName], [RoleName]) VALUES (N'hr', N'HR Group')
INSERT [dbo].[RoleOfAccount] ([UserName], [RoleName]) VALUES (N'interviewer', N'Interviewer')
INSERT [dbo].[RoleOfAccount] ([UserName], [RoleName]) VALUES (N'nguyenanh', N'Interviewer')
-- END --

GO

-- VACANCY TABLE --
/****** Object:  Table [dbo].[Vacancy]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0001', N'D0001', N'Tuyển lập trình viên PHP', CAST(0x0000A02700000000 AS DateTime), 0, 5, N'Nhân viên', N'Hà Nội', N'Part Time', 200, 500, N'Chúng tôi cần tuyển lập trình viên PHP để phát triển các dự án của Công ty như:
- Các Website của công ty.
- Các Website cho các game.
- Các hệ thống kết nối với các đối tác như FPT, VTC, VDC, Zing...
- Các website portal về Game
...
Làm việc dựa trên các framework có sẵn hoặc code cơ bản từ đầu tuỳ dự án.

Sẽ phỏng vấn liên tục cho đến khi tuyển dụng được theo yêu cầu công ty. Chỉ hồ sơ đạt yêu cầu mới được liên hệ phỏng vấn.', N'- Tốt nghiệp Đại học/Cao đẳng chuyên ngành CNTT hoặc các chuyên ngành có liên quan.
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
- Ưu tiên những ứng viên đã từng làm việc trong môi trường nước ngoài.', N'+ Môi trường làm việc năng động, sáng tạo và chuyên nghiệp.
+ Lương & Thưởng (có thể đạt mức độ rất cao tuỳ nếu đóng góp lớn cho công ty)
+ Các bảo hiểm theo luật định.
+ Thưởng các ngày Lễ, Tết.
+ Các chế độ xã hội chung hàng năm.
+ Ăn trưa tại công ty.
+ Thử việc được nhận 80% lương chính thức.', 18, 30, 1, N'Không yêu cầu', 0, N'Thử việc 2 tháng', CAST(0x0000A03B00000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0002', N'D0001', N'Quản trị hệ thống', CAST(0x0000A02700000000 AS DateTime), 1, 1, N'Nhân viên', N'Hà Nội', N'Full Time', 550, 850, N'- Quản trị mạng, hệ thống server của công ty (hiện tại > 80 server).
- Quản trị mạng giữa các Server và kết nối ra Internet.
- Quản trị hệ thống, bảo mật, quản lý và monitor server.
- Quản trị hệ thống, bảo mật tài nguyên, máy tính làm việc tại công ty.

Sẽ phỏng vấn liên tục cho đến khi tuyển dụng được theo yêu cầu công ty. Chỉ hồ sơ đạt yêu cầu mới được liên hệ phỏng vấn.', N'- Tốt nghiệp Đại học/Cao đẳng chuyên ngành CNTT hoặc các chuyên ngành có liên quan.
- Tiếng Anh đọc hiểu tài liệu.
- Có khả năng phối hợp nhóm.
- Năng động, sáng tạo. Tư duy rõ ràng, tích cực.
- Có trách nhiệm và luôn cam kết thực hiện công việc tốt nhất.
- Chịu được áp lực cao trong công việc, sẵn sàng làm thêm giờ nếu cần.
- Hiểu biết về các cơ chế bảo mật, Firewall, xây dựng hệ thống cân bằng tải, backup... trên Linux và Windows
- Hiểu biết và cấu hình các hệ thống mạng, sw, server...

- Hiểu biết về các phần cứng và quản trị, các OS (win, linux, BSD...)
- Ưu tiên các ứng viên có kiến thức lập trình web, khả năng xử lý tốt HTML và CSS, làm việc trên các ngôn ngữ lập trình và các cơ sở dữ liệu (sql, No Sql)', N'+ Môi trường làm việc năng động, sáng tạo và chuyên nghiệp.
+ Lương & Thưởng (có thể đạt mức độ rất cao tuỳ nếu đóng góp lớn cho công ty)
+ Các bảo hiểm theo luật định.
+ Thưởng các ngày Lễ, Tết.
+ Các chế độ xã hội chung hàng năm.
+ Ăn trưa tại công ty.
+ Thử việc được nhận 70% lương chính thức.', 24, 30, 1, N'Không yêu cầu', 2, N'Thử việc 2 tháng', CAST(0x0000A03100000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0003', N'D0003', N'Nhân viên Kinh doanh', CAST(0x0000A02700000000 AS DateTime), 0, 2, N'Nhân viên', N'Hà Nội', N'Full Time', 150, 300, N'-	Tìm kiếm, khai thác khách hàng
-	Giao dịch trực tiếp khách hàng, giới thiệu sản phẩm-dịch vụ phần mềm BRAVO cho khách hàng.
-	Xúc tiến bán sản phẩm và dịch vụ phần mềm cho khách hàng
-	Phân tích thị trường, đối thủ cạnh tranh. 
-	Thực hiện các công việc theo đúng tiêu chuẩn ISO của công ty ', N'- Tốt nghiệp Đại học, Cao đẳng
-	Có khả năng giao tiếp tốt, giao tiếp tiếng anh.
-	Đam mê và yêu thích công việc kinh doanh.	
-	Chăm chỉ, cẩn thận, nhanh nhẹn. 
-	Ưu tiên các ứng viên có kinh nghiệm trong lĩnh vực bán hàng và làm các công việc tương tự.', N'+	Lương khởi điểm: 4.200.000 đồng/tháng (có lộ trình tăng lương rõ ràng). Đối với các cá nhân có thâm niên công tác, mức lương sẽ được thỏa thuận.
+	Có thưởng doanh số (theo hệ số được Công ty và phòng quy định).
+	Có phụ cấp điện thoại, phụ cấp ăn trưa (~ 500 000 đồng/tháng), phụ cấp chuyên cần (200 000 đồng/tháng). 
+	Có phụ cấp công việc, phụ cấp chức vụ (nếu là cấp cán bộ quản lý).
+	Được ký hợp đồng lao động và hưởng các quyền lợi đúng luật lao động (BHXH, BHYT...).
+	Được hưởng các chế độ phúc lợi hàng năm của công ty (Liên hoan, nghỉ mát, hoạt động văn thể mỹ…)
+	Cơ hội làm việc trong môi trường năng động, công việc ổn định, phát huy nhiều khả năng, được đào tạo và học tập nâng cao kiến thức nghề nghiệp.', 22, 25, 1, N'Cao đẳng', 0, N'Th? vi?c 3 tháng', CAST(0x0000A03400000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0004', N'D0001', N'Lập trình viên JAVA, C, C++, C#, .NET, Tester', CAST(0x0000A02700000000 AS DateTime), 0, 15, N'Nhân viên', N'Hà Nội', N'Full Time', 350, 700, N'1. Lập trình viên .NET C#/ASP.NET - Số lượng: 5
2. JAVA: Số lượng: 3
3. ANSI-C/ C++: Số lượng: 3
4. Leader C# / ASP.NET: Số lượng 2 (Y.cầu kinh nghiệm 2 năm trở lên)
5. Tester: Số lượng 2 - (Ưu tiên nữ)', N'Yêu thích lập trình , chịu được áp lực công việc .', N'Là thành viên của SaoMai J.S.C, các bạn có cơ hội được phát triển bản thân, được học hỏi kinh nghiệm và làm việc trong một môi trường chuyên nghiệp, hiện đại, có nhiều cơ hội thăng tiến và rất nhiều thuận lợi khác:
-	Được hỗ trợ đào tạo tiếng Nhật, tiếng Anh tại trung tâm đào tạo nguồn nhân lực SAOMAI.
-	Có cơ hội tiếp xúc làm việc trực tiếp với các chuyên gia Nhật bản và Mỹ. 
-	Có cơ hội tham gia các khóa đào tạo kỹ sư phần mềm tại Nhật bản hàng năm.
-	Các cơ hội khác.', 18, 22, 1, N'Cao đẳng', 1, N'Trao d?i tr?c ti?p khi ph?ng v?n', CAST(0x0000A04300000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0005', N'D0003', N'Trưởng phòng kinh doanh', CAST(0x0000A04D0125CA9B AS DateTime), 0, 2, N'Trưởng phòng', N'Hà Nội, Bắc Ninh, Hưng Yên', N'Full time', 1500, 2500, N'- Tuyển dụng đội ngũ nhân viên, quản lý kinh doanh cho công ty
- Đào tạo kỹ năng chuyên ngành cho đội ngũ nhân viên mới của công ty
- Quản lý và giám sát các hoạt động kinh doanh theo nhóm, phòng
- Báo cáo kết quả kinh doanh, tuyển dụng cho ban giám đốc theo tuần, tháng', N'- Kỹ năng vi tính văn phòng
- Kỹ năng giao tiếp', N'- Môi trường làm việc chuyên nghiệp
- Chế độ lương thưởng hấp dẫn nhất trên thị trường hiện nay 
- ưu tiên những ứng viên có kinh nghiệm làm việc ở vị trí tương đương; hỗ trợ thêm 10 triệu/tháng 
- Thường xuyên được đi du lịch nước ngoài', 24, 28, 1, N'Cao đẳng', 1, N'Nhận luôn', CAST(0x0000A05300000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0006', N'D0003', N'TUYỂN QUẢN LÝ KINH DOANH', CAST(0x0000A04D012653F7 AS DateTime), 0, 8, N'Nhân viên', N'Hà Nội, Hưng Yên, Vĩnh Phúc', N'Full time', 1000, 1800, N'- Giao dịch và ký hợp động với khách hàng
- Chăm sóc khách hàng của công ty
- Lập kế hoạch kinh doanh cho phòng
- Hoạch định chiến lược kinh doanh.
- Phân công công việc, giám sát toàn bộ hoạt động kinh doanh
- Triển khai kinh doanh theo vùng.
- Xây dựng và phát triển bền vững đội ngũ nhóm kinh doanh', N'- Thành thạo tin học văn phòng
- Có khả năng thuyết trình', N'- Môi trường làm việc năng động, hiệu quả.
- Cơ hội học tập và du lịch trong nước và nước ngoài.
- Cơ hội thăng tiến hấp dẫn.
- Làm việc trong môi trường năng động, chuyên nghiệp, cởi mở & sáng tạo; có nhiều cơ hội học hỏi, phát triển về kỹ năng và kinh nghiệm quản lý.
- Có cơ hội phát huy được khả năng của bản thân
- Nghỉ thứ 7 và CN', 20, 24, 1, N'Trung cấp', 1, N'Thử việc 1 tháng', CAST(0x0000A05C00000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0007', N'D0002', N'Nhân viên văn phòng', CAST(0x0000A04D0126CFDB AS DateTime), 0, 1, N'Nhân viên', N'Hà Nội', N'Full time', 1200, 1400, N'- Thực hiện quản lý toàn bộ hoạt động hành chính văn phòng của công ty.
- Thực hiện công tác tuyển dụng và quản lý tuyển dụng.
- Lập bảng lương hàng tháng.
- Thực hiện thủ tục bảo hiểm xã hội, bảo hiểm y tế, bảo hiểm thất nghiệp.
- Hướng dẫn, quản lý mã số thuế thu nhập cá nhân và thủ tục giảm trừ gia cảnh.
- Soạn thảo văn bản thuộc ban giám đốc.
- Thực hiện công tác văn thư lưu trữ, quản lý con dấu.
- Xây dựng các quy định, quy chế quản lý trong lĩnh vưc hành chính, nhân sự.
- Các công việc khác.', N'- Trình độ: cao đẳng trở lên.
- Ưu tiên những ứng viên có thành tích trong các hoạt động xã hội.
- Ngoại hình: Cân đối, ưa nhìn, cao 1m60 trở lên, giọng nói chuẩn.
- Sử dụng thành thạo máy vi tính, các phần mềm thông dụng.
- Biết soạn thảo các văn bản hành chính, sử dụng các loại máy văn phòng, kỹ năng giao tiếp trực tiếp và qua điện thoại tốt.
- Tính cách: Chăm chỉ, cầu tiến, ham học hỏi.', N'- Lương + thưởng
- Du lịch hàng năm cùng công ty', 20, 25, 0, N'Cao đẳng', 1, N'Thử việc 2 tháng', CAST(0x0000A06000000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0008', N'D0004', N'CÁN BỘ QUẢN LÝ NHÂN VIÊN', CAST(0x0000A04D01279B38 AS DateTime), 0, 5, N'Trưởng phòng', N'Hà Nội', N'Full time', 1300, 1500, N'Quản lý đội ngũ nhân viên , nhận và triển khai kế hoạch làm việc từ công ty . Huấn luyện , đào tạo , kèm cặp nhân viên , chọn lọc và tuyển dụng các ứng viên đạt tiêu chuẩn cho công ty .', N'Tuổi từ 30 đến 55 , tốt nghiệp trung cấp trở lên , hộ khẩu thường trú tại Hà Nội và các tỉnh phụ cận . Giao tiếp tốt , nhiệt tình với công việc , có tố chất lãnh đạo và khả năng làm việc độc lập . Ưu tiên người có kinh nghiệm Quản Lý , Kinh Doanh . Người làm trong ngành Tài Chính , Kế Toán và Ngân Hàng .', N'Được làm việc trong môi trường năng động chuyên nghiệp , công việc lâu dài ổn định , thu nhập trên 10 triệu một tháng , sau 3 tháng làm việc thu nhập trên 20 triệu một tháng + thưởng và các khoản phụ cấp , có cơ hội thăng tiến nhanh theo năng lực .', 30, 55, 1, N'Trung cấp', 1, N'Trao đổi trực tiếp khi phỏng vấn', CAST(0x0000A05900000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0009', N'D0004', N'Phó phòng kinh doanh dịch vụ', CAST(0x0000A04D0128126E AS DateTime), 1, 1, N'Phó phòng', N'Hà Nội', N'Full time', 500, 1200, N'Quản lý chi phí và quản lý nhân sự của hệ thống câu lạc bộ, phân tích thường xuyên đối với tình hình chi phí của hệ thống câu lạc bộ, đề xuất các kế hoạch chi phí, kế hoạch nhân sự, các giải pháp quản lý chi phí; thực hiện các kế hoạch chi phí, nhân sự được phê duyệt cho hệ thống câu lạc bộ.', N'Tốt nghiệp CĐ trở lên các chuyên ngành quản trị kinh doanh tổng hợp hoặc chuyên ngành quản trị nhà hàng, cà phê, giải trí, du lịch; đã từng quản lý hệ thống kinh doanh thuộc lĩnh vực nhà hàng, cà phê, giải trí 2 năm; có kỹ năng tổ chức, kỹ năng đào tạo, huấn luyện nhân viên; tuổi >= 28; cẩn thận, tỉ mỉ.', N'Được hưởng lương, thưởng theo chính sách tài chính của công ty. Được tham gia đóng BHXH, BHYT theo đúng luật định. Phụ cấp ăn trưa và các phụ cấp khác...', 28, 40, 1, N'Cao đẳng', 1, N'Trao đổi trực tiếp khi phỏng vấn', CAST(0x0000A05A00000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0010', N'D0001', N'Chuyên viên đồ hoạ Flash & Flex', CAST(0x0000A04D012812A4 AS DateTime), 0, 2, N'Nhân viên', N'Hà Nội', N'Full time', 900, 2200, N'- Thiết kế ứng dụng flash trên PC và Android.
- Thiết kế hiệu ứng flash các banner, adv, intro...
- Thiết kế hiệu ứng flash cho Website.
- Hiệu chỉnh game flash.', N'- Có tư duy thiết kế.
- Thành thạo các phần mềm lên quan đến flash.
- Thành thạo dịch ngược và sửa file Flash.
- Ưu tiên ứng viên biết làm phim đồ hoạ.

- Có khả năng phối hợp nhóm.
- Năng động, sáng tạo. Tư duy rõ ràng, tích cực.
- Có trách nhiệm và luôn cam kết thực hiện công việc tốt nhất.
- Chịu được áp lực cao trong công việc, sẵn sàng làm thêm giờ nếu cần.', N'+ Môi trường làm việc năng động, sáng tạo và chuyên nghiệp.
+ Lương & Thưởng (có thể đạt mức độ rất cao tuỳ nếu đóng góp lớn cho công ty)
+ Các bảo hiểm theo luật định.
+ Thưởng các ngày Lễ, Tết.
+ Các chế độ xã hội chung hàng năm.
+ Ăn trưa tại công ty.
+ Thử việc được nhận 70% lương chính thức.', 18, 30, 1, N'', 2, N'Thử việc 2 tháng', CAST(0x0000A05A00000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0011', N'D0002', N'Nhân viên hành chính - nhân sự', CAST(0x0000A04D0128864C AS DateTime), 0, 3, N'Nhân viên', N'Hà Nội', N'Part time', 600, 900, N'Làm việc tại văn phòng theo giờ hành chính, t7+cn nghỉ.Chi tiết công việc sẽ được trao đổi trực tiếp khi phỏng vấn.', N'- Giao tiếp tốt, đàm phán tốt
- Ham học hỏi, chịu áp lực công việc.
- Đam mê công việc', N'- lương + thưởng theo doanh số + phụ cấp điện thoại.
- Làm việc trong môi trường năng động, cơ hội thăng tiến cao
- Được tham gia các khóa học đào tạo nâng cao miễn phí
- Cơ hội đi du lịch trong và ngoài nước', 22, 40, 1, N'Trung cấp', 2, N'Nhận luôn', CAST(0x0000A05C00000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0012', N'D0001', N'Nhân viên Triển khai phần mềm Kế toán', CAST(0x0000A04D012A1FEA AS DateTime), 0, 10, N'Nhân viên', N'Hà Nội', N'Full time', 200, 500, N'	Customize phần mềm (Lập trình theo yêu cầu khách hàng).
	Cài đặt, hướng dẫn, hỗ trợ khách hàng sử dụng phần mềm.
	Trợ giúp khách hàng các nghiệp vụ kế toán.
	Thực hiện các công việc theo đúng tiêu chuẩn ISO của công ty.', N'	Tốt nghiệp Đại học, Cao đẳng 
	Biết lập trình và Biết kế toán (Yêu cầu bắt buộc)
	Giao tiếp tốt.', N'+	Lương khởi điểm: 4.200.000 đồng/tháng (có lộ trình tăng lương rõ ràng). Đối với các cá nhân có thâm niên công tác mức lương sẽ được thỏa thuận.
+	Có phụ cấp ăn trưa (~ 500 000 đồng/tháng), phụ cấp chuyên cần (200 000 đồng/tháng). 
+	Có thưởng doanh số (theo hệ số được Công ty và phòng quy định).
+	Có phụ phụ cấp điện thoại, phụ cấp công việc, phụ cấp chức vụ (nếu là cấp cán bộ quản lý).
+	Được ký hợp đồng lao động và hưởng các quyền lợi đúng luật lao động (BHXH, BHYT...).
+	Được hưởng các chế độ phúc lợi hàng năm của công ty (Liên hoan, nghỉ mát, hoạt động văn thể mỹ…)
+	Có cơ hội mở rộng kiến thức về hệ thống quản trị ERP và hệ thống kế toán doanh nghiệp
+	Cơ hội làm việc trong môi trường năng động, công việc ổn định, phát huy khả năng của bản thân.', 18, 30, 1, N'Cao đẳng', 2, N'Thử việc 2 tháng', CAST(0x0000A06200000000 AS DateTime))
INSERT [dbo].[Vacancy] ([VacancyID], [DepartmentID], [Title], [CreatedDate], [Status], [NumberRequirement], [Position], [WorkingPlace], [WorkType], [MinimumSalary], [MaximumSalary], [Description], [SkillRequirement], [Entitlement], [MinimumAge], [MaximumAge], [Gender], [Degree], [YearOfExperience], [ProbationaryPeriod], [Deadline]) VALUES (N'V0013', N'D0004', N'Quản lý Kinh doanh', CAST(0x0000A04D012A9A21 AS DateTime), 0, 1, N'Giám đốc', N'Hà Nội', N'Full time', 3000, 5000, N'• Quản lý công việc kinh doanh của trung tâm trên phạm vi/lĩnh vực được phân công
- Lập kế hoạch kinh doanh và triển khai thực hiện kế hoạch kinh doanh đã được phê duyệt trên phạm vi/lĩnh vực được phân công theo kỳ (quý, 6 tháng, năm)
- Trực tiếp điều hành công việc kinh doanh của trung tâm
- Quyết định các công việc và chính sách liên quan đến Hãng và hệ thống khách hàng của trung tâm
- Giải quyết các công việc phát sinh khác trong kỳ
• Đàm phán với hãng về: Chính sách bán hàng của sản phẩm được phân công phụ trách hoặc hỗ trợ; Chính sách Marketing và các chương trình Promotion khác
• Làm việc với trưởng phòng kinh doanh/NVKD phụ trách trực tiếp các dòng sản phẩm về việc: Tổng hợp ý kiến đề xuất của NVKD trong cùng line sản phẩm; Thống nhất chính sách bán hàng (giá cả, các chính sách hỗ trợ cho khách hàng…) trên khu vực quản lý; Lập kế hoạch và theo dõi việc triển khai kế hoạch trên khu vực quản lý
• Làm việc với đội Marketing về việc: Lập kế hoạch các chương trình Marketing triển khai trong nước, trên phạm vi quản lý; Đánh giá hiệu quả các chương trình Marketing, rút kinh nghiệm và lập kế hoạch cho kỳ tiếp theo 
• Nghiên cứu sản phẩm hiện tại và sản phẩm mới, nghiên cứu thị trường để có kế hoạch kinh doanh phù hợp với mỗi dòng sản phẩm
• Quản lý nhân sự trên phạm vi/lĩnh vực được phân công và hỗ trợ Giám đốc quản lý nhân sự trực thuộc
• Hỗ trợ Giám đốc Quản trị hệ thống
• Thực hiện các công việc khác theo yêu cầu của Ban lãnh đạo công ty', N'- Nam; Tốt nghiệp Đại học trở lên với các chuyên ngành Kinh tế, Thương mại, Quản trị kinh doanh và CNTT
- Có kinh nghiệm làm việc trong lĩnh vực chuyên môn từ 03 năm trở lên.
- Có kinh nghiệm quản lý nhân sự, hệ thống từ 02 năm trở lên
- Hiểu biết rộng, am hiểu rõ về thị trường IT
- Trình độ tiếng Anh tốt, có khả năng giao tiếp hiệu quả bằng tiếng Anh với các đối tác nước ngoài
- Phong cách chững trạc, tự tin, chuyên nghiệp
- Khả năng lập kế hoạch và thuyết trình tốt 
- Trách nhiệm, chịu được áp lực cao trong công việc
- Khả năng sell-in, sell-out tốt
- Kỹ năng quản lý kinh doanh , nắm bắt chuẩn nhu cầu khách hàng, và thực hiện các biện pháp linh hoạt, hiệu quả, kịp thời để đáp ứng các nhu cầu đó.
- Kỹ năng quản lý hiệu quả công việc
- Khả năng phân tích tốt và giải quyết vấn đề triệt để
- Khả năng tập hợp lực lượng, phát huy sức mạnh của mỗi thành viên tốt', N'- Thu nhập hấp dẫn.
- Môi trường làm việc năng động, hiện đại.
- Được đào tạo để nâng cao năng lực và nghiệp vụ.
- Có cơ hội thăng tiến trong Tập đoàn CNTT lớn nhất Việt Nam
- Được hưởng đầy đủ chế độ lao động theo Quy định nhà nước.
- Được hưởng chế độ bảo hiểm FPT Care.', 20, 40, 1, N'Đại học', 2, N'Trao đổi trực tiếp khi phỏng vấn', CAST(0x0000A08000000000 AS DateTime))
-- END --

GO

-- APPLICANT TABLE --
/****** Object:  Table [dbo].[Applicant]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0001', N'Trinh', N'Tuan', 1, CAST(0x00007D4400000000 AS DateTime), N'0248467393', N'tuan@gmail.com', N'Cao Bang', 1, 300, N'English, Japanness', 1, N'Cao đẳng', CAST(0x0000A02B00000000 AS DateTime), N'- Lap trinh duoc nhieu ngon ngu JAVA, .NET ...', N'Doat giai nhi cuoc thi BEst Coder tai Aptech FPT')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0002', N'John', N'Hart', 1, CAST(0x000081FB00000000 AS DateTime), N'029876398', N'hart@gmail.com', N'HCM City', 1, 800, N'English, French, VietNammess', 2, N'University', CAST(0x0000A02D00000000 AS DateTime), N'- Nhanh nhen, giao tiep tot, het minh voi cong viec', N'khong co')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0003', N'Nguyen', N'Phuong Ha', 0, CAST(0x000087AD00000000 AS DateTime), N'098764820', N'haphuong@gmail.com', N'Ha Noi', 0, 500, N'English, Japanness, French, Spanish, Chinness', 1, N'Dai hoc', CAST(0x0000A02C00000000 AS DateTime), N'- Noi duoc nhieu thu tieng, giao tiep tot', N'')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0004', N'Vu', N'Minh Anh', 0, CAST(0x000086A000000000 AS DateTime), N'094127923', N'minhanh@gmail.com', N'Thanh Hoa', 1, 320, N'English', 0, N'Cao Dang', CAST(0x0000A02D00000000 AS DateTime), N'- Ngoai giao tot, tu tin, het long vi cong viec', N'Vao vong chung ket cuoc thi Tim kiem doanh nhan tre to chuc vao thang 3/2012 tai Ha Noi')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0005', N'Phạm', N'Như Ngọc', 0, CAST(0x00007EB900000000 AS DateTime), N'', N'phamnhungoc@yahoo.com', N'Gò Vấp, Tp. Hồ Chí Minh', 99, 0, N'', 0, N'Đại học', CAST(0x0000A04D012ECBC4 AS DateTime), N'-Kỹ năng vi tính: sử dụng thành thạo vi tính văn phòng, biết trình bày Power Point, excel
-Có hiểu biết về Mạng xã hội
-Có khả năng làm việc độc lập, làm việc theo nhóm
-Kỹ năng sử dụng Internet
-Kỹ năng lập kế hoạch, lập báo cáo
-Trung thực trong công việc
-Sáng tạo, chủ đông, ham học hỏi và biết cách tìm kiếm thông tin hiệu quả trên Google', N'Tốt nghiệp trường Đại học Tài Chính Marketing Tp. HCM xếp loại khá toàn khóa')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0006', N'Lê', N'Xuân Thành', 1, CAST(0x0000794B00000000 AS DateTime), N'', N'thanhlx@gmail.com', N'2B Tạ Hiện, P.Hàng Buồm, quận Hoàn Kiếm, TP Hà Nội', 1, 500, N'', 2, N'Cử nhân kinh tế', CAST(0x0000A04D01301F16 AS DateTime), N'', N'- Luôn là một trong các nhân viên xuất sắc tại các đơn vị công tác (NV Xuất sắc năm 2011 tại VIB).
- Là nhân viên tiêu biểu đại diện cho MB Bank Thanh Hóa dự thi cuộc thi bán hàng chuyên nghiệp nhân dịp 15 năm thành lập ngành và lọt vào top 10 nhân viên tiêu biểu toàn khu vực niền Trung.')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0007', N'Ngô', N'Quang Huân', 1, CAST(0x00007FC200000000 AS DateTime), N'', N'huannq@gmail.com', N'Yên Lợi - Ý Yên - Nam Định', 0, 0, N'Tiếng Anh', 2, N'Đại học', CAST(0x0000A04D01315827 AS DateTime), N'Làm việc chắc chắn, không ngừng học hỏi, nâng cao trình độ chuyên môn', N'Tốt nghiệp khá chuyên ngành kế toán
-tài chính doanh nghiệp')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0008', N'Hoàng', N'Thị Dương', 0, CAST(0x000078DE00000000 AS DateTime), N'', N'hoangduongbk4@gmail.com', N'Phú Lương, Hà Đông, Hà Nội', 99, 0, N'', 0, N'Cử nhân', CAST(0x0000A04D013279A6 AS DateTime), N'Sử dụng thành thạo vi tính văn phòng: Microsoft Word, Excel, Power Point, Access, Mạng inertnet...
-Có kỹ năng giao tiếp, trình bày tốt. 
-Lập kế hoạch, tổ chức sắp xếp công việc khoa học.
-Cẩn thận trung thực, có tinh thần trách nhiệm.
-Thiết lập công thức excel theo đặc thù của Công ty.
-Chăm chỉ, cẩn thận, tỉ mỉ, biết tổ chức sắp xếp công việc và có tinh thần trách nhiệm trong công việc. Dễ dàng thích nghi với môi trường mới.', N'')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0009', N'Võ', N'Mỹ Linh', 0, CAST(0x00007B4600000000 AS DateTime), N'', N'linhmv@gmail.com', N'27/16/4 Đường số 8, P. Hiệp Bình Chánh, Q. Thủ Đức', 1, 600, N'English', 1, N'Trung cấp', CAST(0x0000A04D01361E27 AS DateTime), N'Khả năng làm việc nhóm
Chịu được áp lức công việc
Tỉ mỉ, cẩn thận
Nhiệt tình, hòa đồng
Ham học hỏi và tiếp thu ý kiến của cấp trên và đồng nghiệp', N'Tốt nghiệp loại giỏi hệ trung cấp
Bằng A Anh văn
Bằng A Tin học
Hiện đang theo học hệ hoàn chỉnh Đai học')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0010', N'TRƯƠNG', N'THU THẢO', 0, CAST(0x0000728600000000 AS DateTime), N'0936420572', N'thuthao@yahoo.com', N'Số 67A, Đường 18, KP 5. Linh Trung Thủ Đức', 1, 350, N'', 1, N'Cao Đẳng', CAST(0x0000A04D013884EC AS DateTime), N'Thích nghi nhanh, chịu được áp lực', N'Thu thập thông tin phục vụ công việc sau này.')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0011', N'Nguyễn', N'Việt Dũng', 1, CAST(0x000084FC00000000 AS DateTime), N'', N'dungvn@yahoo.com', N'385/3 Lê Văn Sỹ, p.2, q.Tân Bình', 1, 0, N'', 2, N'Cử Nhân Đại học Chính Quy', CAST(0x0000A04D0138D7D4 AS DateTime), N'', N'')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0012', N'Nguyễn', N'Đăng Khoa', 1, CAST(0x00007DBD00000000 AS DateTime), N'', N'khoand@gmail.com', N'21/40 Lã Xuân Oai, P. Trường Thạnh, Quận 9', 1, 0, N'', 0, N'Trung Cấp', CAST(0x0000A04D013991A3 AS DateTime), N'', N'')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0013', N'Nguyễn', N'Ngọc Quyền', 1, CAST(0x0000701E00000000 AS DateTime), N'0163628686', N'quyennn@gmail.com', N'Số 105 láng hạ - đống đa - Hà Nội', 0, 1400, N'tiếng pháp', 3, N'Thạc Sỹ Và Cử Nhân', CAST(0x0000A04D013A1E50 AS DateTime), N'Có thể nói rất tốt tiếng pháp , rất linh hoạt trong những tình huống bất ngờ .', N'')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0014', N'Nguyễn', N'Bích Thảo', 0, CAST(0x000080A800000000 AS DateTime), N'', N'sanni@gmail.com', N'249/44 Lê Đức Thọ, phường 17, quận Gò Vấp , Tp.HCM', 0, 0, N'', 1, N'', CAST(0x0000A04D013A76EF AS DateTime), N'Kỹ năng phân tích thực 
- Có kiến thức cơ bản : Kinh Doanh, Marketing , PR.
-Có Khả năng giao tiếp tốt, khả năng làm việc độc lập- làm việc nhóm tốt.
Khả năng tự học và nghiên cứu 
- Thu thập thông tin từ nhiều nguồn khác nhau: thư viện, sách tham khảo, internet để làm giàu vốn kiến thức của mình.
Anh văn
- Đã Đạt Chứng Chỉ C
Máy tính 
- Khá:Word, Power Point, Excel
-Có khả năng tìm kiếm thông tin trên mạng. 
Làm việc theo nhóm và kỹ năng thuyết trình
- Kỹ năng làm powerpoint tốt.
- Có khả năng làm việc theo nhóm tốt.
- Đưa ra ý kiến để thảo luận trong nhóm và thuyết trình tốt.
- Liên kết các thành viên trong nhóm.', N'')
INSERT [dbo].[Applicant] ([ApplicantID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Status], [SalaryRequirement], [Language], [YearOfExperience], [Degree], [CreatedDate], [Skill], [Award]) VALUES (N'A0015', N'Lê', N'Ngọc Quý', 1, CAST(0x000079AB00000000 AS DateTime), N'', N'quylengoc@yahoo.com', N'Ngõ 460 Thụy Khuê - tây hô - Hà Nội', 99, 0, N'', 0, N'', CAST(0x0000A04D013AD07F AS DateTime), N'-	Sử dụng thành thạo các chương trình tin học văn phòng: Microsoft Word, Excel, PowerPoint
-	Kỹ năng tìm kiếm, khai thác thông tin trên Internet hiệu quả.
-	Kỹ năng giao tiếp tốt, khả năng thuyết trình tốt.
-	Có khả năng thiết lập, xây dựng mối quan hệ với khách hàng tốt.
-	Kỹ năng đàm phán, thương lượng, thuyết phục khách hàng tốt.
-	Kỹ năng phục vụ và chăm sóc khách hàng chuyên nghiệp.
-	Khả năng thích nghi nhanh với môi trường công việc mới
-	Có khả năng cộng tác, làm việc theo nhóm hiệu quả và khả năng làm việc độc lập dưới áp lực và cường độ công việc cao.
-	Khả năng phân tích, xử lý công việc nhanh. Quản lý, sắp xếp công việc hợp lý, hiệu quả.', N'')
-- END --

GO

-- INTERVIEW TABLE --
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0001', N'E0002', N'A0001', N'V0004', 1, 100, CAST(0x0000A03B0083D600 AS DateTime), CAST(0x0000A03B009C8E20 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0002', N'E0002', N'A0002', N'V0001', 1, 100, CAST(0x0000A03B009C8E20 AS DateTime), CAST(0x0000A03B00BD83A0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0003', N'E0003', N'A0003', N'V0003', 1, -100, CAST(0x0000A03F00D63BC0 AS DateTime), CAST(0x0000A03F00EEF3E0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0004', N'E0002', N'A0004', N'V0002', 1, 100, CAST(0x0000A04000E6B680 AS DateTime), CAST(0x0000A04000FF6EA0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0005', N'E0002', N'A0001', N'V0003', 1, 1, CAST(0x0000A03F0083D600 AS DateTime), CAST(0x0000A03F009C8E20 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0006', N'E0003', N'A0002', N'V0003', 1, 100, CAST(0x0000A03F00A4CB80 AS DateTime), CAST(0x0000A03F00BD83A0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0007', N'E0003', N'A0005', N'V0003', 1, -100, CAST(0x00009FCC00735B40 AS DateTime), CAST(0x00009FCC009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0008', N'E0003', N'A0006', N'V0009', 1, 100, CAST(0x00009FD50083D600 AS DateTime), CAST(0x00009FD500A4CB80 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0009', N'E0003', N'A0011', N'V0008', 1, 100, CAST(0x00009FF200E6B680 AS DateTime), CAST(0x00009FF20107AC00 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0010', N'E0003', N'A0012', N'V0008', 1, 100, CAST(0x00009FF90083D600 AS DateTime), CAST(0x00009FF900A4CB80 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0011', N'E0002', N'A0013', N'V0013', 1, -100, CAST(0x00009FFF00735B40 AS DateTime), CAST(0x00009FFF009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0012', N'E0003', N'A0014', N'V0009', 1, -100, CAST(0x00009FFF00735B40 AS DateTime), CAST(0x00009FFF009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0013', N'E0003', N'A0015', N'V0013', 1, -100, CAST(0x0000A00E00D63BC0 AS DateTime), CAST(0x0000A00E00F73140 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0014', N'E0003', N'A0010', N'V0008', 1, 100, CAST(0x0000A00E0083D600 AS DateTime), CAST(0x0000A00E00B54640 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0015', N'E0002', N'A0010', N'V0013', 1, 1, CAST(0x0000A04500735B40 AS DateTime), CAST(0x0000A045009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0016', N'E0002', N'A0009', N'V0010', 1, 100, CAST(0x0000A04500E6B680 AS DateTime), CAST(0x0000A04500F73140 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0017', N'E0002', N'A0009', N'V0011', 1, 1, CAST(0x0000A04B00735B40 AS DateTime), CAST(0x0000A04B009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0018', N'E0003', N'A0003', N'V0010', 1, -100, CAST(0x0000A03000735B40 AS DateTime), CAST(0x0000A030009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0019', N'E0003', N'A0003', N'V0004', 1, -100, CAST(0x0000A03000F73140 AS DateTime), CAST(0x0000A030010FE960 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0020', N'E0002', N'A0006', N'V0005', 1, 1, CAST(0x0000A04E00D63BC0 AS DateTime), CAST(0x0000A04E00F73140 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0021', N'E0003', N'A0008', N'V0012', 100, 99, CAST(0x0000A05700735B40 AS DateTime), CAST(0x0000A057009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0022', N'E0002', N'A0008', N'V0001', 100, 99, CAST(0x0000A057009450C0 AS DateTime), CAST(0x0000A05700B54640 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0023', N'E0003', N'A0005', N'V0012', -100, 99, CAST(0x0000A05400735B40 AS DateTime), CAST(0x0000A054008C1360 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0024', N'E0002', N'A0005', N'V0010', -100, 99, CAST(0x0000A054008C1360 AS DateTime), CAST(0x0000A05400A4CB80 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0025', N'E0003', N'A0007', N'V0001', 1, -100, CAST(0x0000A02F009450C0 AS DateTime), CAST(0x0000A02F00AD08E0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0026', N'E0003', N'A0007', N'V0007', 0, 99, CAST(0x0000A05500F73140 AS DateTime), CAST(0x0000A055011826C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0027', N'E0002', N'A0013', N'V0011', 1, -100, CAST(0x0000A014008C1360 AS DateTime), CAST(0x0000A01400A4CB80 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0028', N'E0003', N'A0013', N'V0001', 1, -100, CAST(0x0000A01400F73140 AS DateTime), CAST(0x0000A0140107AC00 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0029', N'E0003', N'A0014', N'V0001', 1, -100, CAST(0x0000A0140083D600 AS DateTime), CAST(0x0000A01400A4CB80 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0030', N'E0002', N'A0014', N'V0010', 1, -100, CAST(0x0000A01C00F73140 AS DateTime), CAST(0x0000A01C0107AC00 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0031', N'E0003', N'A0015', N'V0001', 0, 99, CAST(0x0000A05C00735B40 AS DateTime), CAST(0x0000A05C009450C0 AS DateTime))
INSERT [dbo].[Interview] ([InterviewID], [EmployeeID], [ApplicantID], [VacancyID], [Status], [AVStatus], [StartedTime], [EndedTime]) VALUES (N'I0032', N'E0002', N'A0015', N'V0004', 0, 99, CAST(0x0000A05C009450C0 AS DateTime), CAST(0x0000A05C00B54640 AS DateTime))
-- END --