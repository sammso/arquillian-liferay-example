create index IX_266BA035 on Foo_Foo (companyId);
create index IX_D358AFF7 on Foo_Foo (groupId);
create index IX_7430AD47 on Foo_Foo (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_56C1D989 on Foo_Foo (uuid_[$COLUMN_LENGTH:75$], groupId);