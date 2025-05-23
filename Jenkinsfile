pipeline {
    agent any

    environment {
        AZURE_RESOURCE_GROUP = 'SWII-CICD'
        AZURE_APP_NAME = 'productosjson'
        AZURE_REGION = 'Canada Central'
        MAVEN_HOME = tool 'Maven'
        JAVA_HOME = tool 'JDK-17'
    }

    tools {
        maven 'Maven'
        jdk 'JDK-17'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Json-Esutpinan/Examen2SWII.git'
            }
        }

        stage('Copy Azure Auth File') {
            steps {
                // Si usas el plugin Config File Provider
                configFileProvider([configFile(fileId: 'azure-auth-json', targetLocation: 'azureAuth.json')]) {
                    echo 'Auth file copied'
                }
            }
        }

        stage('Build Application') {
            steps {
                sh "mvn clean install -DskipTests"
            }
        }

        stage('Deploy to Azure App Service') {
            steps {
                sh "mvn azure-webapp:deploy"
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."
        }
        failure {
            echo "Pipeline failed. Check logs for details."
        }
        success {
            echo "Pipeline succeeded. Application deployed to Azure App Service."
        }
    }
}
