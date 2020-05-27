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

router.get('/Coin/:idx',function(req,res){
  connection.query(`select coin from Billage.billage where user_id=${req.params.idx}`,function(err,rows,fields){
    if(!err){
      console.log(JSON.stringify(rows[0].coin));
      res.write(JSON.stringify(rows[0].coin))
      res.end();
    }else{
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
      console.log("getLike error");
    }
  })
})

module.exports = router;
