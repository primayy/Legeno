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
      res.write("getcoin error")
      res.end()
      console.log("getcoin error");
    }
  })
})
module.exports = router;
