namespace java com.yao.app.springmvc.thrift

struct YTimestamp{
	1:i16 year,
	2:byte month,
	3:byte day,
	4:byte hour,
	5:byte minute
}

struct YUser{
	1:string id,
	2:string name,
	3:string gravatarMail,
	4:YTimestamp regiterTime,
	5:string email
}

service UserService{
	YUser queryUserInfo(1:string userId)
}