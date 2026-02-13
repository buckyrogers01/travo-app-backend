pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Clone Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/buckyrogers01/travo-app-backend.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                pkill -f travo.jar || true

                cp target/*.jar /home/ubuntu/travo.jar

                nohup java -jar /home/ubuntu/travo.jar \
                --spring.config.location=/home/ubuntu/application.properties \
                > /home/ubuntu/log.txt 2>&1 &
                '''
            }
        }
    }
}
