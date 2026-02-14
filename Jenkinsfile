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
                echo "Stopping app..."
                sudo systemctl stop travo

                echo "Copying jar..."
                cp target/*.jar /home/ubuntu/travo.jar

                echo "Starting app..."
                sudo systemctl start travo

                echo "Status..."
                sudo systemctl status travo
                '''
            }
        }
    }
}
