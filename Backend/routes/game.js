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

router.get('/getUserDB/:id',function(req,res){
    //req에는 userid, res로는 userid에 해당하는 billage정보
    connection.query(`select * from Billage.billage where user_id=${req.params.id}`,function(err,rows,fiels){
        if(!err){
            var billageList=rows
            var calldb=JSON.parse(JSON.stringify(billageList[0]))
            calldb.obj_info=JSON.parse(calldb.obj_info)
            console.log(calldb);
            res.send(calldb);
        }
    })

})

router.post('/addObjinfo/:id',function(req,res){
    //req.body.user_id,req.body.obj_info
    var Obj_info = req.body.ObjInfo;
    console.log("Obj_info = " +Obj_info);
    connection.query(`update Billage.billage set Obj_info = '${req.body.ObjInfo}' where user_id = ${req.params.id}`,function(err,rows,fiels){
        if(!err){
            console.log("Success in node");
        }
        else
        {
            console.log("Fail in node");
        }
    })
    //connection.query()
})
router.post('/UpdateCoin/:id',function(req,res){
    //req.body.coin, req.params.id <- user_id
    connection.query(`update Billage.billage set coin = ${req.body.coin} where user_id = ${req.params.id}`,function(err,rows,fiels){
        if(!err){
            console.log("Success in node");
        }
        else{
            console.log("Fail in node");
        }
    })
})
router.get('/getObjectDB',function(req,res){
    connection.query(`select * from Billage.object`,function(err,rows,fiels){
        if(!err){
            var billageList=rows

            console.log(billageList);
            res.send(billageList);
        }
    })

})

module.exports = router;
