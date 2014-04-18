namespace java com.yao.app.protocol.thrift.service

struct YTimestamp{
	1:i16 year,
	2:byte month,
	3:byte day,
	4:byte hour,
	5:byte minute,
	6:byte second,
	7:i16 millisecond
}

struct YUser{
	1:string id,
	2:string name,
	3:string gravatarMail,
	4:YTimestamp registerTime,
	5:string email
}

service UserService{
	YUser queryUserInfo(1:string userId)
}