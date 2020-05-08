FROM node:10
MAINTAINER doseum <wjdtmddn1996@gmail.com>

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install

COPY . .

EXPOSE 3000
CMD npm start
