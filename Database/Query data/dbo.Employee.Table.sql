USE [RecruitmentProcessSystem]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 05/12/2012 20:43:27 ******/
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0000', N'D0001', N'Do', N'Hoang', 1, CAST(0x0000000000000000 AS DateTime), N'', N'admin@fpt.aptech.ac.vn', N'', N'Master Chief')
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0001', N'D0002', N'Do', N'Hoang', 1, CAST(0x000081B000000000 AS DateTime), N'0974422919', N'hoangdp_c00473@fpt.aptech.ac.vn', N'Hoang Mai, Ha Noi', N'Master Chief')
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0002', N'D0002', N'Tran', N'Vinh', 1, CAST(0x00007B5500000000 AS DateTime), N'0987658901', N'tranvinh@fpt.aptech.ac.vn', N'Cau Giay, Ha Noi', N'Employee')
INSERT [dbo].[Employee] ([EmployeeID], [DepartmentID], [FirstName], [LastName], [Gender], [DOB], [PhoneNumber], [Email], [Address], [Position]) VALUES (N'E0003', N'D0002', N'Nguyen', N'Anh', 0, CAST(0x000085C300000000 AS DateTime), N'0913387456', N'nguyenanh@fpt.aptech.ac.vn', N'Hoan Kiem, Ha Noi', N'Employee')
