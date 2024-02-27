# Using Docker's UBI9 Container as BASE (needed amd64 arch for these runners)
FROM --platform=linux/amd64 redhat/ubi9:9.3

# Version variables
ARG JDK_VERSION=jdk-17_linux-x64_bin
ARG JFX_VERSION=openjfx-17.0.1_linux-x64_bin-sdk
ARG MVN_VERSION=apache-maven-3.9.6-bin

# Labels
LABEL org.opencontainers.image.source=https://github.com/CompeteLeak/ApexStats
LABEL org.opencontainers.image.description ubi9:9.3 image w/ mvn,jdk17,javafx22

# Send up config.yaml
RUN mkdir /resources
COPY resources/ /resources

# Set us up
RUN yum install -y wget vim unzip libX11
RUN mv /resources/centos8.repo /etc/yum.repos.d/
RUN rpm --import https://www.centos.org/keys/RPM-GPG-KEY-CentOS-Official
RUN dnf install -y xorg-x11-server-Xvfb

# Install JDK17
RUN wget https://download.oracle.com/java/17/latest/${JDK_VERSION}.rpm
RUN yum install -y ./${JDK_VERSION}.rpm

# Install JavaFX 17.0.1
RUN wget https://download2.gluonhq.com/openjfx/17.0.1/${JFX_VERSION}.zip
RUN unzip ${JFX_VERSION}.zip
RUN rm -rf ${JFX_VERSION}.zip
RUN cp /javafx-sdk-17.0.1/lib/libglass.so /usr/lib64/

# Install apache-maven 3.9.6
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/${MVN_VERSION}.tar.gz
RUN tar xvf ${MVN_VERSION}.tar.gz
RUN mv apache-maven-3.9.6 /usr/local/
RUN rm -rf ${MVN_VERSION}.tar.gz

# Environmental Variables for Maven
ENV M2_HOME=/usr/local/apache-maven-3.9.6
ENV M2=${M2_HOME}/bin 
ENV PATH=${M2}:$PATH