# Etapa 1: construir Angular
FROM node:18.20-alpine AS build

WORKDIR /app

# Copiamos dependencias
COPY package*.json ./

RUN npm install

# Copiamos el proyecto
COPY . .

# Construimos Angular
RUN npm run build


# Etapa 2: servidor web
FROM nginx:alpine

# Copiar el build generado por Angular
COPY --from=build /app/dist/marketplace /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]