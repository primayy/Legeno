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

function calDate(today,daybefore){
  //today는 String,daybefore는 int
  if(daybefore==0) return today;
  else{
    var tmp=String(parseInt(today.replace(/-/gi,""))-daybefore)
    var targetDay=tmp.substr(0,4)+"-"+tmp.substr(4,2)+"-"+tmp.substr(6,2)
    return targetDay
  }
}

router.post('/checkQuest',function(req,res){
  var str=JSON.stringify(req.body)
  var postdata=JSON.parse(Object.keys(JSON.parse(str)));
  postdata.today=calDate(postdata.today,2)
  postdata.day=7//for test
  //String data float로 형변환
  postdata.expectation=parseFloat(postdata.expectation)
  console.log(postdata);
  var yesterday=calDate(postdata.today,1)
  var questList;

  connection.query("select * from Billage.quest",function(err,rows,fields){
    if(!err){
      questList=rows
      //일간 소비절약 완료 확인
      if(checkDailySave(yesterday,postdata.daily,postdata.expectation)==1){
        questList[0].complete=1;
      }else if(checkDailySave(yesterday,postdata.daily,postdata.expectation)==2){
        questList[0].complete=1
        questList[1].complete=1;
      }else if(checkDailySave(yesterday,postdata.daily,postdata.expectation)==3){
        questList[0].complete=1
        questList[1].complete=1;
        questList[2].complete=1
      }
      //일간 계획소비 완료 확인
      if(checkDailyPlan(yesterday,postdata.daily,postdata.expectation)){
        questList[3].complete=1;
      }
      // //주간 소비 절약 확인
      // if(checkWeeklySave(postdata.today,postdata.day,postdata.daily,postdata.expectation)==1){
      //   questList[4].complete=1
      // }else if(checkWeeklySave(postdata.today,postdata.day,postdata.daily,postdata.expectation)==2){
      //   questList[4].complete=1
      //   questList[5].complete=1
      // }else if(checkWeeklySave(postdata.today,postdata.day,postdata.daily,postdata.expectation)==3){
      //   questList[4].complete=1
      //   questList[5].complete=1
      //   questList[6].complete=1
      // }
      //주간 계획 소비 확인
      if(checkWeeklyPlan(postdata.today,postdata.day,postdata.daily,postdata.expectation)==1){
        questList[7].complete=1
      }else if(checkWeeklyPlan(postdata.today,postdata.day,postdata.daily,postdata.expectation)==2){
        questList[7].complete=1
        questList[8].complete=1
      }else if(checkWeeklyPlan(postdata.today,postdata.day,postdata.daily,postdata.expectation)==1){
        questList[7].complete=1
        questList[8].complete=1
        questList[9].complete=1
      }

      console.log(questList);
      res.write(JSON.stringify(questList))
      res.end();
    }else{
      console.log("questList select error");
    }
  })
})

//일간 퀘스트 달성여부 확인하고 주간,월간에서는 반복문으로 돌려서 확인

function checkDailySave(targetDay,Data,expectation){
  //특정 날짜의 일간 절약 퀘스트 달성여부 확인
  for(i=0;i<Data.length;i++){
    if(Data[i].date==targetDay){
      if(IsDailySaveLv1(parseFloat(Data[i].cost),expectation)){
        return 1
      }else if(IsDailySaveLv2(parseFloat(Data[i].cost),expectation)){
        return 2
      }else if(IsDailySaveLv3(parseFloat(Data[i].cost),expectation)){
        return 3
      }else return 0
    }
  }
}

function checkWeeklySave(today,day,Data,expectation){
  var countLv1=0;
  var countLv2=0;

  for(i=1;i<4;i++){
    console.log("res: "+checkDailySave(calDate()));
    if(checkDailySave(calDate(today,i),Data,expectation)==1) countLv1++
    else if(checkDailySave(calDate(today,i),Data,expectation)==2||checkDailySave(calDate(today,i),Data,expectation)==3) countLv2++;
  }
  console.log(countLv1,countLv2);
  // for(i=0;i<result.length;i++){
  //   if(result[i]==1) countLv1++;
  //   else if (result[i]==2||result[i]==3) countLv2++;
  // }
  if(countLv2>=7) return 3
  else if(countLv2<7&&countLv2>=5) return 2
  else if(countLv1>=3) return 1
  else return 0
}

function IsDailySaveLv1(cost,expectation){
  if(cost>expectation*0.9&&cost<=expectation*0.95) return true;
  else false;
}

function IsDailySaveLv2(cost,expectation){
  if(cost>expectation*0.8&&cost<=expectation*0.9) return true;
  else false;
}

function IsDailySaveLv3(cost,expectation){
  if(cost<=expectation*0.8) return true;
  else false;
}

function checkDailyPlan(targetDay,Data,expectation){
  //특정 날짜의 일간 계획소비 퀘스트 달성여부 확인
  for(i=0;i<Data.length;i++){
    if(Data[i].date==targetDay){
      if(parseFloat(Data[i].cost)<=expectation*1.05&&parseFloat(Data[i].cost)>=expectation*0.95) return 1
      else return 0;
    }
  }
}

function checkWeeklyPlan(today,day,Data,expectation){
  var count=0;
  console.log(day,typeof(day));
  for(i=1;i<day;i++){
    var targetDay=calDate(today,i)
    console.log(targetDay);
    // if(checkDailyPlan(targetDay,Data,expectation)==1)count++
    // else continue;
  }
  console.log(count);
  if(count>=7) return 3
  else if(count>=5&&count<7) return 2
  else if(count<5&&count>=3) return 1
  else return 0
}

module.exports = router;
