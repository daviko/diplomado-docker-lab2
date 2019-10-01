FROM node:10.16.3-alpine as frontbuild
WORKDIR /usr/src/front
RUN npm install -g @angular/cli@8.2.2 \
    && npm cache clean --force \
    && ng new api-products --routing=true --style=css --skipGit=true \
    && rm -rf api-products/src/app/
COPY ./src/app /usr/src/front/api-products/src/app
WORKDIR /usr/src/front/api-products
RUN ng build --prod

FROM node:10.16.3-alpine
LABEL maintainer="daviko.50@gmail.com"
RUN apk add --update tini \
    && npm install http-server -g
WORKDIR /app
COPY --from=frontbuild /usr/src/front/api-products/ .
EXPOSE 80
ENTRYPOINT ["/sbin/tini", "--", "http-server", "-p", "80"]
