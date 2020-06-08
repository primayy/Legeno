var express = require('express');
var app= express()
var router = express.Router();
var mysql=require('mysql')
var bodyParser=require('body-parser')

app.use(bodyParser.urlencoded({extended:true}))

var db_config={
  host:'3.17.141.131',
  port:3306,
  user:'root',
  password:'*tmddn1010914',
  database:'Billage'
}
var connection;

function handleDisconnect(){

    connection=mysql.createConnection(db_config)

    connection.connect(function(err){
      if(err){
        console.log('error on connecting to DB',err);
        setTimeout(handleDisconnect,2000)
      }
    })

    connection.on('error',function(err){
      console.log('DB error',err);
      if(err.code==='PROTOCOL_CONNECTION_LOST'){
        handleDisconnect()
      }else{
        throw err;
      }
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

router.post('/Intro/:idx',function(req,res){
  req.body=JSON.parse(Object.keys(JSON.parse(JSON.stringify((req.body)))))
  console.log(req.body);

  if(req.body.intro.length>100){
    res.write("introduction length can't be longer than 100 characters")
    res.end()
  }

  connection.query(`update Billage.billage set billage_intro="${req.body.intro}" where user_id=${req.params.idx}`,function(err,rows,fields){
    if(!err){
      connection.query(`select billage_intro from Billage.billage where user_id=${req.params.idx}`,function(err,rows,fields){
        res.write(JSON.stringify(rows[0].billage_intro))
        res.end()
      })
    }else{
      res.write(err)
      res.end()
      console.log(err);
    }
  })
})
module.exports = router;
