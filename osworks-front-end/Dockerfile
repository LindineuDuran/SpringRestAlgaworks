# We use a node.js container to build our application
# We call this contanier 'angular'
FROM node:latest as angular
WORKDIR /app
COPY package.json /app
COPY package-lock.json /app
RUN npm install --silent
COPY . .
RUN npm run build

# We move our builded application into a nginx container
FROM nginx:alpine
VOLUME /var/cache/nginx

# we can take the value of our "outputPath" into angular.json file
COPY --from=angular app/dist/osworks-front-end /usr/share/nginx/html
COPY ./config/nginx.conf /etc/nginx/conf.d/default.conf

# docker build -t lduran355/osworks-front-end .
# docker run -d --restart=always -p 8081:80 --network=mysqlNET lduran355/osworks-front-end
