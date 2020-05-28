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
      if(err.code==='PROTOCOL_CONNECTION_LOST'){
        return handleDisconnect()
      }else{
        throw err;
      }
    })
}

handleDisconnect();

router.get('/getUserDB/:id',function(req,res){
    //req에는 userid, res로는 userid에 해당하는 billage정보
    connection.query(`select * from Billage.billage where user_id=${req.params.id}`,function(err,rows,fiels){
        if(!err){
            var billageList=rows
            var calldb=JSON.parse(JSON.stringify(billageList[0]))
            calldb.obj_info=JSON.parse(calldb.obj_info)
            res.send(calldb);
        }
    })

})

router.post('/addObjinfo/:id',function(req,res){
    //req.body.user_id,req.body.obj_info

    connection.query(`update Billage.billage set Obj_info = '${req.body.ObjInfo}',coin = '${req.body.coin}',billage_cost = '${req.body.billagecost}' where user_id = ${req.params.id}`,function(err,rows,fiels){
        if(!err){
            res.send("Obj update success");
            console.log("Obj정보 업데이트 성공");
        }
        else
        {
            res.send("Obj update fail");
            console.log("Obj정보 업데이트 실패!");
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
router.post('/UpdateBillageCost/:id',function(req,res){
    //req.body.Cost, req.params.id <- user_id
    connection.query(`update Billage.billage set billage_cost = ${req.body.billage_cost} where user_id = ${req.params.id}`,function(err,rows,fiels){
        if(!err){
            console.log("코스트 업데이트 성공");
        }
        else{
            console.log("코스트 업데이트 실패");
        }
    })
})
router.post('/UpdateCoinAndBillageCost/:id',function(req,res){
    //req.body.coin,req.body.billage_cost, req.params.id <- user_id

    connection.query(`update Billage.billage set coin = ${req.body.coin},billage_cost = ${req.body.billage_cost} where user_id = ${req.params.id}`,function(err,rows,fiels){
        if(!err){
            console.log("코인과 코스트 업데이트 성공");
        }
        else{
            console.log("코인과 코스트 업데이트 실패");
        }
    })
})
router.get('/getObjectDB',function(req,res){
    connection.query(`select * from Billage.object`,function(err,rows,fiels){
        if(!err){
            var billageList=rows;
            res.send(billageList);
        }
    })

})
router.get('/getRankingDB',function(req,res){
    connection.query(`select user.user_id,nickname,billage_cost,billage_like from Billage.billage left join Billage.user On user.user_id = billage.user_id order by billage_cost DESC;`,function(err,rows,fiels){
        if(!err){
            var RankingDBList=rows;
            res.send(RankingDBList);
        }
    })

})

router.get('/getLikeRankingDB',function(req,res){
    connection.query(`select user.user_id,nickname,billage_cost,billage_like from Billage.billage left join Billage.user On user.user_id = billage.user_id order by billage_like DESC;`,function(err,rows,fiels){
        if(!err){
            var RankingDBList=rows;
            res.send(RankingDBList);
        }
    })

})
module.exports = router;
