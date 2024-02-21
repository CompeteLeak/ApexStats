# Using Docker's UBI8 Container as BASE
FROM redhat/ubi8:8.9

# Connect to repository
LABEL org.opencontainers.image.source=https://github.com/CompeteLeak/ApexStats

# Set us up
RUN yum install -y wget vim unzip

# Install JDK17
RUN wget https://download.oracle.com/java/17/latest/jdk-17_linux-aarch64_bin.rpm
RUN yum install -y ./jdk-17_linux-aarch64_bin.rpm

# Install JavaFX used 22-early access version 
# because 17 doesn't have a aarch64 version
RUN wget https://download2.gluonhq.com/openjfx/22/openjfx-22-ea+28_linux-aarch64_bin-sdk.zip
RUN unzip openjfx-22-ea+28_linux-aarch64_bin-sdk.zip
RUN rm -rf openjfx-22-ea+28_linux-aarch64_bin-sdk.zip

# Install apache-maven 3.9.6
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
RUN tar xvf apache-maven-3.9.6-bin.tar.gz
RUN mv apache-maven-3.9.6 /usr/local/
RUN rm -rf apache-maven-3.9.6-bin.tar.gz

# Environmental Variables for Maven
ENV M2_HOME = /usr/local/apache-maven/apache-maven-3.9.6
ENV M2 = ${M2_HOME}/bin 
ENV PATH = ${M2}:$PATH