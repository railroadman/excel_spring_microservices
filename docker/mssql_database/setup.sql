DROP DATABASE importDocs3;

GO

CREATE DATABASE importDocs3
GO
USE [importDocs3]
GO
/****** Object:  Table [dbo].[ContentType]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContentType](
	[id] [bigint] NOT NULL,
	[type] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_ContentType] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[code] [nvarchar](500) NULL,
	[isDeleted] [bigint] NULL,
	[firstName] [nvarchar](50) NULL,
	[lastName] [nvarchar](50) NULL,
 CONSTRAINT [PK_Customer3] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer_tmp]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer_tmp](
	[id] [bigint] NOT NULL,
	[code] [nvarchar](500) NULL,
	[isDeleted] [bigint] NULL,
	[firstName] [nvarchar](50) NULL,
	[lastName] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DataType]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DataType](
	[id] [bigint] NOT NULL,
	[type] [varchar](50) NOT NULL,
 CONSTRAINT [PK_DataType] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Document]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Document](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[idContentType] [bigint] NOT NULL,
	[docValue] [varbinary](max) NOT NULL,
	[idDataType] [bigint] NOT NULL,
 CONSTRAINT [PK_Document] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DocumentStatusDto]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DocumentStatusDto](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[errorMessage] [nvarchar](max) NULL,
	[documentId] [bigint] NOT NULL,
	[eventId] [bigint] NOT NULL,
 CONSTRAINT [PK_DDocumentStatusDto2] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Event]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Event](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[documentId] [bigint] NOT NULL,
	[stateId] [bigint] NOT NULL,
	[customerId] [bigint] NOT NULL,
	[isDeleted] [bit] NOT NULL,
	[updateData] [datetimeoffset](7) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [PK_Event] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Item]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Item](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[code] [nvarchar](500) NOT NULL,
	[description] [nvarchar](50) NOT NULL,
	[isDeleted] [bit] NULL,
	[customerId] [bigint] NULL,
 CONSTRAINT [PK_Item_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[State]    Script Date: 9/23/2021 00:26:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[State](
	[id] [bigint] NOT NULL,
	[state] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_State] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Event] ADD  CONSTRAINT [DF_Event_isDeleted]  DEFAULT ((0)) FOR [isDeleted]
GO
ALTER TABLE [dbo].[Event] ADD  CONSTRAINT [DF_Event_version]  DEFAULT ((1)) FOR [version]
GO
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_ContentType] FOREIGN KEY([idContentType])
REFERENCES [dbo].[ContentType] ([id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_ContentType]
GO
ALTER TABLE [dbo].[Document]  WITH CHECK ADD  CONSTRAINT [FK_Document_DataType] FOREIGN KEY([idDataType])
REFERENCES [dbo].[DataType] ([id])
GO
ALTER TABLE [dbo].[Document] CHECK CONSTRAINT [FK_Document_DataType]
GO
ALTER TABLE [dbo].[DocumentStatusDto]  WITH CHECK ADD  CONSTRAINT [FK_DocumentStatusDto2_Event] FOREIGN KEY([eventId])
REFERENCES [dbo].[Event] ([id])
GO
ALTER TABLE [dbo].[DocumentStatusDto] CHECK CONSTRAINT [FK_DocumentStatusDto2_Event]
GO
ALTER TABLE [dbo].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_Document] FOREIGN KEY([documentId])
REFERENCES [dbo].[Document] ([id])
GO
ALTER TABLE [dbo].[Event] CHECK CONSTRAINT [FK_Event_Document]
GO
ALTER TABLE [dbo].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_State] FOREIGN KEY([stateId])
REFERENCES [dbo].[State] ([id])
GO
ALTER TABLE [dbo].[Event] CHECK CONSTRAINT [FK_Event_State]
GO


GO
INSERT [dbo].[ContentType] ([id], [type]) VALUES (1, N'XLS')
GO
INSERT [dbo].[ContentType] ([id], [type]) VALUES (2, N'XLSX')
GO
INSERT [dbo].[ContentType] ([id], [type]) VALUES (3, N'CSV')
GO
INSERT [dbo].[DataType] ([id], [type]) VALUES (1, N'CUSTOMER')
GO
INSERT [dbo].[DataType] ([id], [type]) VALUES (2, N'ITEM')
GO
INSERT [dbo].[State] ([id], [state]) VALUES (0, N'ERROR')
GO
INSERT [dbo].[State] ([id], [state]) VALUES (1, N'NEW')
GO
INSERT [dbo].[State] ([id], [state]) VALUES (2, N'PARSED')
GO
INSERT [dbo].[State] ([id], [state]) VALUES (3, N'SAVED')
GO

