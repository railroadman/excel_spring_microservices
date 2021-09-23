FROM mcr.microsoft.com/mssql/server

# Switch to root user for access to apt-get install
USER root

# Install node/npm
RUN apt-get -y update  && \
        apt-get install -y curl && \
        apt-get install -y dos2unix

# Install tedious, the driver for SQL Server for Node.js

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app


# Bundle app source
COPY . /usr/src/app

RUN dos2unix *

# Grant permissions for the import-data script to be executable
RUN chmod +x /usr/src/app/import-data.sh

# Switch back to mssql user and run the entrypoint script
USER mssql
ENTRYPOINT /bin/bash ./entrypoint.sh
