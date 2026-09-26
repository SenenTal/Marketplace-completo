# Etapa 1: construir Angular
FROM node:22-alpine AS build
# Crea un contenedor basado en node.js
WORKDIR /app
# Copiar package.json del proyecto
COPY package*.json ./
# instala las dependencias
RUN npm install
# Asi esta bien, no es necesario nada más
COPY . .
# Compilar Angular
RUN npm run build

# Etapa 2: servir Angular con Nginx
FROM nginx:alpine
# Copia desde la etapa build los archivos generados por Angular
# hacia la carpeta que Nginx utiliza para servir páginas web.
COPY --from=build /app/dist/marketplace/browser /usr/share/nginx/html
#Puerto
EXPOSE 80
