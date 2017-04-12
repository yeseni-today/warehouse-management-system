CREATE TABLE SDictionary
(
  field        CHAR(30) PRIMARY KEY, --字段或者项目编号
  _table       CHAR(50) NOT NULL, --对应表
  _index       CHAR(10) NOT NULL, --当前可用的指针
  _indexlength INT      NOT NULL     --指针允许长度

)
INSERT INTO SDictionary VALUES ('020', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('021', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('022', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('023', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('024', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('025', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('026', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('027', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('028', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('029', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('040', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('041', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('042', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('043', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('044', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('045', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('046', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('047', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('048', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('049', 'Item', 1, 5)
INSERT INTO SDictionary VALUES ('company_ID', 'Item_company', 1, 4)
INSERT INTO SDictionary VALUES ('storage_ID', 'Item_ in_storage', 100, 3)
INSERT INTO SDictionary VALUES ('borrow_ID', 'Item_borrow', 100, 3)
INSERT INTO SDictionary VALUES ('application_ID', 'Item_application', 100, 3)
INSERT INTO SDictionary VALUES ('out_ID', 'Item_out_storage', 100, 3)
