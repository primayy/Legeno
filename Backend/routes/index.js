var express = require('express');
var app= express()
var router = express.Router();
var bodyParser=require('body-Parser')

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

router.get('/SignUp',function(req,res){
  if(req.body.name){//이름 검사
    if(req.body.nickname){//닉네임 검사
      connection.query("select nickname from Billage.user",function(err,rows,fields){
        if(!err) {
          var nicknameList=rows
          console.log(nicknameList);
          for (var dbNick of nicknameList){
            if(req.body.nickname==dbNick.nickname){//닉네임 중복검사
              console.log('occupied nickname!');
              return
            }
          }
          //if(req.body.password) 비밀번호 유효성검사
          connection.query(`insert into Billage.user(user_name,nickname,bank_list,auth_check) values(${req.body.name},${req.body.nickname},NULL,false)`)
        }
        else{
          console.log('nickname select query error');
        }
      })
    }
  }
})

module.exports = router;
