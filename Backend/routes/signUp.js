var express = require('express');
var app= express()
var router = express.Router();
var mysql=require('mysql')
var bodyParser=require('body-parser')

app.use(bodyParser.urlencoded({extended:true}))

var connection=mysql.createConnection({
  host:'3.17.141.131',
  port:3306,
  user:'root',
  password:'*tmddn1010914',
  database:'Billage'
})

connection.connect();

router.post('/',function(req,res){
  //데이터를 json형식으로 parsing
  var str=JSON.stringify(req.body);
  var postdata=JSON.parse(Object.keys(JSON.parse(str)));
  console.log(postdata);

  if(postdata.name){//이름 검사
    if(postdata.nickname){//닉네임 검사
      connection.query("select nickname from Billage.user",function(err,rows,fields){
        if(!err) {
          var nicknameList=rows
          console.log(nicknameList);
          for (var dbNick of nicknameList){
            if(postdata.nickname==dbNick.nickname){//닉네임 중복검사
              console.log('occupied nickname!');
              return
            }
          }
          //if(req.body.password) 비밀번호 유효성검사
          connection.query(`insert into Billage.user(user_name,nickname,bank_list,auth_check) values("${postdata.name}","${postdata.nickname}",NULL,false)`)//새로운 User row생성
          connection.query(`select user_id from Billage.user where user_name="${postdata.name}" and nickname="${postdata.nickname}"`,function(err,rows,fields){
            var newUserId=JSON.parse(JSON.stringify(rows))[0].user_id
            connection.query(`insert into Billage.billage(user_id,coin,size,obj_info,billage_cost,billage_like) values("${newUserId}",0,5,'{"Obj_data":[]}',0,0)`)//새로운 billage row 생성
            res.write(String(newUserId))//response로 새로 생성된 user의 id전달
            res.end();
          })
        }
        else{
          console.log('nickname select query error');
        }
      })
    }
  }
})

module.exports = router;
