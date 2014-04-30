//
// Autogenerated by Thrift Compiler (0.9.1)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


YTimestamp = function(args) {
  this.year = null;
  this.month = null;
  this.day = null;
  this.hour = null;
  this.minute = null;
  this.second = null;
  this.millisecond = null;
  if (args) {
    if (args.year !== undefined) {
      this.year = args.year;
    }
    if (args.month !== undefined) {
      this.month = args.month;
    }
    if (args.day !== undefined) {
      this.day = args.day;
    }
    if (args.hour !== undefined) {
      this.hour = args.hour;
    }
    if (args.minute !== undefined) {
      this.minute = args.minute;
    }
    if (args.second !== undefined) {
      this.second = args.second;
    }
    if (args.millisecond !== undefined) {
      this.millisecond = args.millisecond;
    }
  }
};
YTimestamp.prototype = {};
YTimestamp.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.I16) {
        this.year = input.readI16().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.month = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.BYTE) {
        this.day = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.BYTE) {
        this.hour = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.BYTE) {
        this.minute = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.BYTE) {
        this.second = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.I16) {
        this.millisecond = input.readI16().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

YTimestamp.prototype.write = function(output) {
  output.writeStructBegin('YTimestamp');
  if (this.year !== null && this.year !== undefined) {
    output.writeFieldBegin('year', Thrift.Type.I16, 1);
    output.writeI16(this.year);
    output.writeFieldEnd();
  }
  if (this.month !== null && this.month !== undefined) {
    output.writeFieldBegin('month', Thrift.Type.BYTE, 2);
    output.writeByte(this.month);
    output.writeFieldEnd();
  }
  if (this.day !== null && this.day !== undefined) {
    output.writeFieldBegin('day', Thrift.Type.BYTE, 3);
    output.writeByte(this.day);
    output.writeFieldEnd();
  }
  if (this.hour !== null && this.hour !== undefined) {
    output.writeFieldBegin('hour', Thrift.Type.BYTE, 4);
    output.writeByte(this.hour);
    output.writeFieldEnd();
  }
  if (this.minute !== null && this.minute !== undefined) {
    output.writeFieldBegin('minute', Thrift.Type.BYTE, 5);
    output.writeByte(this.minute);
    output.writeFieldEnd();
  }
  if (this.second !== null && this.second !== undefined) {
    output.writeFieldBegin('second', Thrift.Type.BYTE, 6);
    output.writeByte(this.second);
    output.writeFieldEnd();
  }
  if (this.millisecond !== null && this.millisecond !== undefined) {
    output.writeFieldBegin('millisecond', Thrift.Type.I16, 7);
    output.writeI16(this.millisecond);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

YUser = function(args) {
  this.id = null;
  this.name = null;
  this.gravatarMail = null;
  this.registerTime = null;
  this.email = null;
  if (args) {
    if (args.id !== undefined) {
      this.id = args.id;
    }
    if (args.name !== undefined) {
      this.name = args.name;
    }
    if (args.gravatarMail !== undefined) {
      this.gravatarMail = args.gravatarMail;
    }
    if (args.registerTime !== undefined) {
      this.registerTime = args.registerTime;
    }
    if (args.email !== undefined) {
      this.email = args.email;
    }
  }
};
YUser.prototype = {};
YUser.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.id = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRING) {
        this.name = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.STRING) {
        this.gravatarMail = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.STRUCT) {
        this.registerTime = new YTimestamp();
        this.registerTime.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.STRING) {
        this.email = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

YUser.prototype.write = function(output) {
  output.writeStructBegin('YUser');
  if (this.id !== null && this.id !== undefined) {
    output.writeFieldBegin('id', Thrift.Type.STRING, 1);
    output.writeString(this.id);
    output.writeFieldEnd();
  }
  if (this.name !== null && this.name !== undefined) {
    output.writeFieldBegin('name', Thrift.Type.STRING, 2);
    output.writeString(this.name);
    output.writeFieldEnd();
  }
  if (this.gravatarMail !== null && this.gravatarMail !== undefined) {
    output.writeFieldBegin('gravatarMail', Thrift.Type.STRING, 3);
    output.writeString(this.gravatarMail);
    output.writeFieldEnd();
  }
  if (this.registerTime !== null && this.registerTime !== undefined) {
    output.writeFieldBegin('registerTime', Thrift.Type.STRUCT, 4);
    this.registerTime.write(output);
    output.writeFieldEnd();
  }
  if (this.email !== null && this.email !== undefined) {
    output.writeFieldBegin('email', Thrift.Type.STRING, 5);
    output.writeString(this.email);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};
