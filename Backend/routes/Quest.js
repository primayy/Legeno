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

router.get('/getQuest',function(req,res){
  var questList;

  connection.query("select * from Billage.quest",function(err,rows,fields){
    if(!err){
      questList=rows

      res.write(JSON.stringify(questList))
      res.end();
    }else{
      console.log("questList select error");
    }
  })
})


router.post('/checkCoin',function(req,res){
  var str=JSON.stringify(req.body)
  var postdata=JSON.parse(Object.keys(JSON.parse(str)))
  connection.query(`select coin,billage_cost from Billage.billage where user_id=${postdata.user_id}`,function(err,rows,fields){
    if(!err){
      res.write(JSON.stringify(rows))
      res.end();
    }else{
      console.log("checkCoin error");
    }
  })
})

module.exports = router;
