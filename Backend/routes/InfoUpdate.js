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

function handleDisconnect(){
    connection.connect(function(err){
      if(err){
        console.log('error on connecting to DB',err);
        setTimeout(handleDisconnect,2000)
      }
    })

    connection.on('error',function(err){
      console.log('DB error',err);
      return handleDisconnect()
    })
}

handleDisconnect();

router.post('/Coin/:idx',function(req,res){
  req.body=JSON.parse(Object.keys(JSON.parse(JSON.stringify(req.body))))
  console.log(req.body);
  connection.query(`update Billage.billage set coin=${req.body.coin} where user_id=${req.params.idx}`,function(err,rows,fields){
    if(!err){
      connection.query(`select coin from Billage.billage where user_id=${req.params.idx}`,function(err,rows,fields){
        res.write(JSON.stringify(rows[0].coin));
        res.end()
      })
    }else{
      res.write("get new coin error")
      res.end()
      console.log("get new coin error");
    }
  })
})

router.post('/Nickname/:idx',function(req,res){
  req.body=JSON.parse(Object.keys(JSON.parse(JSON.stringify(req.body))))
  console.log(req.body);
  //닉네임 중복검사
  connection.query(`select nickname from Billage.user`,function(err,rows,fields){
    if(!err){
      var nicknameList=rows
      for(var dbNick of nicknameList){
        if(req.body.nickname==dbNick.nickname){
          console.log('occupied nickname!');
          res.write('occupied nickname!')
          res.end()
          return
        }
      }
      connection.query(`update Billage.user set nickname="${req.body.nickname}" where user_id=${req.params.idx}`,function(err,rows,fields){
        if(!err){
          connection.query(`select nickname from Billage.user where user_id=${req.params.idx}`,function(err,rows,fields){
            res.write(JSON.stringify(rows[0].nickname))
            res.end()
          })
        }else{
          res.write(err)
          res.end()
          console.log(err);
        }
      })
    }else{
      res.write("nickname select error")
      res.end()
      console.log("nickname select error");
    }
  })

})
module.exports = router;
