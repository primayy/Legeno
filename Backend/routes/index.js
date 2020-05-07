var express = require('express');
var app= express()
var router = express.Router();
var bodyParser=require('body-parser')

app.use(bodyParser.urlencoded({extended:true}))

var mysql=require('mysql')

var connection=mysql.createConnection({
  host:'3.17.141.131',
  port:3306,
  user:'root',
  password:'*tmddn1010914',
  database:'Billage'
})

connection.connect()

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/SignUp',function(req,res){
  //데이터를 json형식으로 parsing
  var str=JSON.stringify(req.body);
  var postdata=JSON.parse(JSON.parse(str.substring(1,str.length-4)));
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
          connection.query(`insert into Billage.user(user_name,nickname,bank_list,auth_check) values("${postdata.name}","${postdata.nickname}",NULL,false)`)
        }
        else{
          console.log('nickname select query error');
        }
      })
    }
  }
})

module.exports = router;
