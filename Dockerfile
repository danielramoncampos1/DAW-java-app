# Usa la imagen base de Tomcat 11
FROM tomcat:11.0

# Instala dependencias necesarias y JDK 23 desde un repositorio confiable
RUN apt-get update && \
    apt-get install -y wget gnupg2 && \
    # Descarga JDK 23 desde Oracle
    wget https://download.oracle.com/java/23/latest/jdk-23_linux-x64_bin.tar.gz -O /tmp/jdk-23.tar.gz && \
    # Descomprime el archivo y verifica el contenido
    tar -xzf /tmp/jdk-23.tar.gz -C /opt && \
    # Limpia el archivo descargado
    rm /tmp/jdk-23.tar.gz && \
    # Verifica el contenido de /opt para asegurar la ruta correcta
    ls /opt && \
    # Establece correctamente la ruta a Java y Javac
    update-alternatives --install /usr/bin/java java /opt/jdk-23*/bin/java 1 && \
    update-alternatives --install /usr/bin/javac javac /opt/jdk-23*/bin/javac 1

# Copia el archivo WAR de la aplicación a la carpeta webapps de Tomcat
COPY target/llibreriaweb.war /usr/local/tomcat/webapps/

# Expone el puerto 8085
EXPOSE 8085

# Ejecuta Tomcat
CMD ["catalina.sh", "run"]
