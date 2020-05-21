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

      console.log(questList);
      res.write(JSON.stringify(questList))
      res.end();
    }else{
      console.log("questList select error");
    }
  })
})


module.exports = router;
