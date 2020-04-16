namespace java com.yao.app.protocol.thrift.service

struct TUser{
	1:optional string id,
	2:required string name,
	3:optional string gravatarMail,
	4:optional string email
}

struct TUserDetail{
  1:i64 accountNo;
  2:string name;
  3:string department;
  4:string email;
  5:binary snsAccountNo;
  6:list<string> permissions;
  7:map<string,string> otherProperties;
  8:i64 loginTimestamp;
}

service TUserService{
	TUser queryUserById(1:required string id);

	list<TUser> queryUserByIds(1:required list<string> ids),
	
	string addUser(1:required TUser user)
}