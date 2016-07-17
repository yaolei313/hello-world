namespace java com.yao.app.protocol.thrift.service

struct TUser{
	1:optional string id,
	2:required string name,
	3:optional string gravatarMail,
	4:optional string email
}

service TUserService{
	TUser queryUserById(1:required string id),
	
	string addUser(1:required TUser user)
}