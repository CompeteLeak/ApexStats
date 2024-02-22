# Using Docker's UBI9 Container as BASE (needed amd64 arch for these runners)
FROM --platform=linux/amd64 redhat/ubi9:9.3

# Labels
LABEL org.opencontainers.image.source=https://github.com/CompeteLeak/ApexStats
LABEL org.opencontainers.image.description ubi9:9.3 image w/ mvn,jdk17,javafx22

# Set us up
RUN yum install -y wget vim unzip

# Install JDK17
RUN wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm
RUN yum install -y ./jdk-17_linux-x64_bin.rpm

# Install JavaFX 21.0.2
RUN wget https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip
RUN unzip openjfx-21.0.2_linux-x64_bin-sdk.zip
RUN rm -rf openjfx-21.0.2_linux-x64_bin-sdk.zip

# Install apache-maven 3.9.6
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
RUN tar xvf apache-maven-3.9.6-bin.tar.gz
RUN mv apache-maven-3.9.6 /usr/local/
RUN rm -rf apache-maven-3.9.6-bin.tar.gz

# Environmental Variables for Maven
ENV M2_HOME=/usr/local/apache-maven-3.9.6
ENV M2=${M2_HOME}/bin 
ENV PATH=${M2}:$PATH