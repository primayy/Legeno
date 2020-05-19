var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var signUpRouter = require('./routes/signUp.js');
var gameRouter = require('./routes/game.js');
var questRouter= require('./routes/Quest.js')


var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


app.use('/SignUp', signUpRouter);
app.use('/Game', gameRouter);
app.use('/Quest',questRouter);

module.exports = app;
