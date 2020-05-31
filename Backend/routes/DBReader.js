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

router.get('/Coin/:idx',function(req,res){
  connection.query(`select coin from Billage.billage where user_id=${req.params.idx}`,function(err,rows,fields){
    if(!err){
      console.log(JSON.stringify(rows[0].coin));
      res.write(JSON.stringify(rows[0].coin))
      res.end();
    }else{
      res.write("getcoin error")
      res.end()
      console.log("getcoin error");
    }
  })
})

router.get('/Like/:idx',function(req,res){
  connection.query(`select billage_like from Billage.billage where user_id=${req.params.idx}`,function(err,rows,fields){
    if(!err){
      console.log(JSON.stringify(rows[0].billage_like));
      res.write(JSON.stringify(rows[0].billage_like))
      res.end()
    }else{
      res.write("getLike error")
      res.end()
      console.log("getLike error");
    }
  })
})

module.exports = router;
